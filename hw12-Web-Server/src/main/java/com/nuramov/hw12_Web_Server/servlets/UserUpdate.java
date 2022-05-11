package com.nuramov.hw12_Web_Server.servlets;

import com.nuramov.hw10_Hibernate_ORM.dao.UserDAOImp_Web;
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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/userUpdate")
public class UserUpdate extends HttpServlet {
    private UserDAOImp_Web userDao;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Устанавливаем код успешного ответа (стандартно - ок = 200)
        response.setStatus(HttpServletResponse.SC_OK);

        // Создаем сессию для работы с UserDAOImp_Web разных сервлетах,
        // т.е. UserDAOImp_Web будет передаваться в рамках текущей сессии
        HttpSession session = request.getSession();

        // Получаем UserDAOImp_Web из сессии, если null, создаем новый объект
        userDao = (UserDAOImp_Web) session.getAttribute("userDao");

        if(userDao == null) {
            userDao = new UserDAOImp_Web();
        }

        // Записываем в сессию объект UserDAOImp_Web
        session.setAttribute("userDao", userDao);

        // Конфиги для Freemarker
        Configuration configuration = new Configuration(new Version("2.3.31"));

        configuration.setClassForTemplateLoading(UserSave.class, "/");
        configuration.setDefaultEncoding("UTF-8");

        Map<String, Object> templateData = new HashMap<>();

        try (Writer writer = new StringWriter()) {
            Template template = configuration.getTemplate("infoForUpdating.html");
            template.process(templateData, writer);

            response.getWriter().println(writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // При дабавлении нового пользователя мы возвращаемся на ту же страницу, чтобы добавить нового
        String buttonValue = request.getParameter("buttonValue");
        if(buttonValue.equals("addUser")) {
            doGet(request, response);
        }

        /*try {
            insertUser(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }
}
