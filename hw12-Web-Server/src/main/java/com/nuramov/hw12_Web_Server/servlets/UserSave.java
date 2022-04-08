package com.nuramov.hw12_Web_Server.servlets;

import com.nuramov.hw10_Hibernate_ORM.dao.UserDAOImp_Web;
import com.nuramov.hw10_Hibernate_ORM.model.AddressDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.PhoneDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/userSave")
public class UserSave extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Устанавливаем код успешного ответа (стандартно - ок = 200)
        response.setStatus(HttpServletResponse.SC_OK);

        // Конфиги для Freemarker
        Configuration configuration = new Configuration(new Version("2.3.31"));

        configuration.setClassForTemplateLoading(UserSave.class, "/");
        configuration.setDefaultEncoding("UTF-8");

        Map<String, Object> templateData = new HashMap<>();
        templateData.put("msg", "New User");

        try (Writer writer = new StringWriter()) {
            Template template = configuration.getTemplate("addUser.html");
            template.process(templateData, writer);

            response.getWriter().println(writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        // Использовал redirect, чтобы перейти на страничку google - работает
        //response.sendRedirect("https://www.google.com");

        // на отдельные файлы .ftl или .html не переходит, только по адресу
        //response.sendRedirect("http://localhost:8080/publicInfo");

        // Пробуем через forward (переходы только внутри сервера) - так тоже не работает
        //RequestDispatcher requestDispatcher = request.getRequestDispatcher("http://localhost:8080/publicInfo");
        //requestDispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
