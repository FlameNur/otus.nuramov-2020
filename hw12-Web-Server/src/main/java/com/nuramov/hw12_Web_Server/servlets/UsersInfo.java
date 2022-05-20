package com.nuramov.hw12_Web_Server.servlets;

import com.nuramov.hw10_Hibernate_ORM.model.User;
import com.nuramov.hw12_Web_Server.exceptions.MyException;
import com.nuramov.hw12_Web_Server.services.UserServiceWeb;
import freemarker.template.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/usersInfo")
public class UsersInfo extends HttpServlet {
    private Configuration configuration;
    private UserServiceWeb userServiceWeb;

    public UsersInfo(Configuration configuration, UserServiceWeb userServiceWeb) {
        this.configuration = configuration;
        this.userServiceWeb = userServiceWeb;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Устанавливаем код успешного ответа (стандартно - ок = 200)
        response.setStatus(HttpServletResponse.SC_OK);

        // Добавляем список всех пользователей
        List<User> listUser = userServiceWeb.getAllUser();

        // Создаем набор данных, используемый шаблоном
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("list", listUser);

        try (Writer writer = new StringWriter()) {
            // Загружаем шаблон и создаем объект шаблона
            Template template = configuration.getTemplate("UsersInfo.html");
            // Вызов метода процесса объекта шаблона для вывода файла
            template.process(templateData, writer);
            response.getWriter().println(writer);
        } catch (TemplateException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            deleteUser(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        doGet(request, response);
    }

    /**
     * Метод deleteUser позволяет удалить пользователя по заданному id
     */
    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        // Вызываем сессию, чтобы передать сообщение об ошибке
        HttpSession session = request.getSession();
        // Получаем введенный на странице id
        String idStr = request.getParameter("idToDelete");

        User userToDelete;
        try {
            userToDelete = userServiceWeb.findUser(idStr);
        } catch (MyException e) {
            // Сообщения об ошибке формируются на стороне UserServiceWebImp
            session.setAttribute("message", e.getMessage());
            response.sendRedirect("http://localhost:8080/exceptionServlet");
        }
        userServiceWeb.deleteUser(userToDelete);
    }
}
