package com.nuramov.hw12_Web_Server.servlets;

import com.nuramov.hw10_Hibernate_ORM.model.User;
import com.nuramov.hw12_Web_Server.exceptions.MyException;
import com.nuramov.hw12_Web_Server.services.UserServiceWeb;
import com.nuramov.hw12_Web_Server.services.UserServiceWebImp;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.SQLException;

/**
 * Сервлет UserUpdate обновляет информацию пользователя в БД по введенному id
 */
@WebServlet("/userUpdate")
public class UserUpdate extends HttpServlet {
    private Configuration configuration;
    private UserServiceWeb userServiceWeb;

    public UserUpdate(Configuration configuration, UserServiceWeb userServiceWeb) {
        this.configuration = configuration;
        this.userServiceWeb = userServiceWeb;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Устанавливаем код успешного ответа (стандартно - ок = 200)
        response.setStatus(HttpServletResponse.SC_OK);

        try (Writer writer = new StringWriter()) {
            Template template = configuration.getTemplate("infoForUpdating.html");
            template.process(null, writer);

            response.getWriter().println(writer);
        } catch (TemplateException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Обновляем информацию пользователя при нажатии кнопки "Update" и передаче параметра "buttonValue"
        String buttonValue = request.getParameter("buttonValue");
        if(buttonValue.equals("userUpdate")) {
            try {
                updateUser(request,response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            doGet(request, response);
            // Можно сделать сразу переход на страницу usersInfo после обновления информации
            //response.sendRedirect("http://localhost:8080/usersInfo");
        }
    }

    /**
     * Метод updateUser позволяет обновить информацию о пользователе в БД
     */
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        // Вызываем сессию для передачи сообщения об ошибке
        HttpSession session = request.getSession();

        // Получаем id пользователя, которого надо обновить
        String idStr = request.getParameter("id");
        // Обновляем имя пользователя, если введено новое имя
        String name = request.getParameter("name");
        // Обновляем возраст пользователя, если введено новое значение
        String ageStr = request.getParameter("age");
        // Обновляем телефонный номер пользователя, если введено новое значение
        String phoneNumber = request.getParameter("phone");
        // Обновляем адрес пользователя, если введен новый адрес
        String address = request.getParameter("address");

        try {
            // Обновляем пользователя в БД
            userServiceWeb.updateUser(idStr, name, ageStr, phoneNumber, address);
        } catch (MyException e) {
            // Сообщения об ошибке формируются на стороне UserServiceWebImp
            session.setAttribute("message", e.getMessage());
            response.sendRedirect("http://localhost:8080/exceptionServlet");
        }
    }
}
