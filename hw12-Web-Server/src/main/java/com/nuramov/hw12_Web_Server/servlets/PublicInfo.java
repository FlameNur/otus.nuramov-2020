package com.nuramov.hw12_Web_Server.servlets;

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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/publicInfo")
public class PublicInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Устанавливаем код успешного ответа (стандартно - ок = 200)
        response.setStatus(HttpServletResponse.SC_OK);

        // Сообщает клиенту (веб-браузеру), какой это тип контента, чтобы он знал, что с ним делать
        response.setContentType("text/html");

        // Конфиги для Freemarker
        Configuration configuration = new Configuration(new Version("2.3.31"));

        configuration.setClassForTemplateLoading(UserSave.class, "/");
        configuration.setDefaultEncoding("UTF-8");

        Map<String, Object> templateData = new HashMap<>();

        try (Writer writer = new StringWriter()) {
            Template template = configuration.getTemplate("publicInfo.html");
            template.process(templateData, writer);

            response.getWriter().println(writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод doPost в данном случае нужен для того, чтобы при переходе по ссылке на него, обработать запрос и
     * выполнить необходимые действия/заполнить формы. Сейчас просто вызываем метод doGet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
