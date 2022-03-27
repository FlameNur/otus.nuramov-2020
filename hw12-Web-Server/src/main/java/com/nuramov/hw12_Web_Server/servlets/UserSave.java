package com.nuramov.hw12_Web_Server.servlets;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/userSave")
public class UserSave extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Конфиги для Freemarker
        Configuration configuration = new Configuration(new Version("2.3.31"));

        configuration.setClassForTemplateLoading(UserSave.class, "/");
        configuration.setDefaultEncoding("UTF-8");

        Map<String, Object> templateData = new HashMap<>();
        templateData.put("msg", "Today is a beautiful day");

        try (Writer writer = new StringWriter()) {
            Template template = configuration.getTemplate("test.html");
            template.process(templateData, writer);

            response.getWriter().println(writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        response.setStatus(HttpServletResponse.SC_OK);

        // Использовал redirect, чтобы перейти на страничку google - работает
        //response.sendRedirect("https://www.google.com");

        // на отдельные файлы .ftl или .html не переходит, только по адресу
        //response.sendRedirect("http://localhost:8080/publicInfo");

        // Пробуем через forward (переходы только внутри сервера) - так тоже не работает
        //RequestDispatcher requestDispatcher = request.getRequestDispatcher("http://localhost:8080/publicInfo");
        //requestDispatcher.forward(request, response);

    }
}
