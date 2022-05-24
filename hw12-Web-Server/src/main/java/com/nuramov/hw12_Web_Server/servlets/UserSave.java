package com.nuramov.hw12_Web_Server.servlets;

import com.nuramov.hw10_Hibernate_ORM.model.User;
import com.nuramov.hw12_Web_Server.exceptions.MyException;
import com.nuramov.hw12_Web_Server.services.UserServiceWeb;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/userSave")
public class UserSave extends HttpServlet {
    private Configuration configuration;
    private UserServiceWeb userServiceWeb;

    public UserSave(Configuration configuration, UserServiceWeb userServiceWeb) {
        this.configuration = configuration;
        this.userServiceWeb = userServiceWeb;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Устанавливаем код успешного ответа (стандартно - ок = 200)
        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> templateData = new HashMap<>();
        templateData.put("msg", " add New User");

        try (Writer writer = new StringWriter()) {
            Template template = configuration.getTemplate("addUser.html");
            template.process(templateData, writer);

            response.getWriter().println(writer);
        } catch (TemplateException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // При добавлении нового пользователя возвращаемся на ту же страницу, чтобы добавить нового
        String buttonValue = request.getParameter("buttonValue");
        if(buttonValue.equals("addUser")) {
            insertUser(request,response);
            doGet(request, response);
        }
    }

    /**
     * Метод insertUser позволяет добавить нового пользователя в БД
     */
    private void insertUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Вызываем сессию для передачи сообщения об ошибке
        HttpSession session = request.getSession();

        // Получаем и проверяем введенное имя пользователя
        String name = request.getParameter("name");
        // Получаем и проверяем введенный возраст пользователя
        String ageStr = request.getParameter("age");
        // Получаем и проверяем введенный номер телефона пользователя
        String phoneNumber = request.getParameter("phone");
        // Получаем и проверяем введенный адрес пользователя
        String address = request.getParameter("address");

        User newUser = null;
        try {
            newUser = userServiceWeb.insertParametersCheck(name, ageStr, phoneNumber, address);
        } catch (MyException e) {
            // Сообщения об ошибке формируются на стороне UserServiceWebImp
            session.setAttribute("message", e.getMessage());
            response.sendRedirect("http://localhost:8080/exceptionServlet");
        }
        userServiceWeb.saveUser(newUser);
    }
}
