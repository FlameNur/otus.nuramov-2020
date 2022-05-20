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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Сервлет MyExceptionServlet выдает соответсвующее сообщение при обработке некорректных значений,
 * которые были введены при заполнении форм (add, update, delete)
 */
@WebServlet("/exceptionServlet")
public class MyExceptionServlet extends HttpServlet {
    private Configuration configuration;

    public MyExceptionServlet(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Устанавливаем код успешного ответа (стандартно - ок = 200)
        response.setStatus(HttpServletResponse.SC_OK);

        // Создаем сессию для работы с message в разных сервлетах,
        // т.е. message будет передаваться в рамках текущей сессии
        HttpSession session = request.getSession();

        String message = (String) session.getAttribute("message");

        if(message.equals("")) {
            message = "No information";
        }

        Map<String, Object> templateData = new HashMap<>();
        templateData.put("msg", message);

        try (Writer writer = new StringWriter()) {
            Template template = configuration.getTemplate("ExceptionInfo.html");
            template.process(templateData, writer);

            response.getWriter().println(writer);
        } catch (TemplateException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // Записываем в сессию пустое сообщение
        session.setAttribute("message", "");
    }
}
