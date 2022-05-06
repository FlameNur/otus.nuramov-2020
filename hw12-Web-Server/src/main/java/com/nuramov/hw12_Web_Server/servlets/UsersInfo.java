package com.nuramov.hw12_Web_Server.servlets;

import com.nuramov.hw10_Hibernate_ORM.dao.UserDAOImp_Web;
import com.nuramov.hw10_Hibernate_ORM.model.AddressDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.PhoneDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.User;
import freemarker.template.*;
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
import java.util.List;
import java.util.Map;


@WebServlet("/usersInfo")
public class UsersInfo extends HttpServlet {
    private UserDAOImp_Web userDao;
    private Map<String, Object> templateData;
    private long id;

    public void init() {
        // Создаем набор данных, используемый шаблоном
        templateData = new HashMap<>();
        id = 0;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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

        // Создаем объект конфигурации Freemarker
        Configuration configuration = new Configuration(new Version("2.3.31"));

        // Задаем путь, по которому находится файл шаблона. Не совсем понятно что задавать, пока и так работает
        configuration.setClassForTemplateLoading(UsersInfo.class, "/");
        configuration.setDefaultEncoding("UTF-8");


        // Вариант работы с update и delete
        // Надо проработать
        String buttonValue = request.getParameter("buttonValue");

        // Пока так защищаемся от null
        if(buttonValue == null) {
            buttonValue = "ss";
        }



        // Добавляем список всех пользователей
        List<User> listUser = userDao.getAllUser();
        templateData.put("list", listUser);

        try (Writer writer = new StringWriter()) {
            // Загружаем шаблон и создаем объект шаблона
            Template template = configuration.getTemplate("UsersInfo.html");
            // Вызов метода процесса объекта шаблона для вывода файла
            template.process(templateData, writer);

            response.getWriter().println(writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }


    /**
     *
     */
    /*private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int age = Integer.parseInt(request.getParameter("age"));
        String name = request.getParameter("name");

        User user = new User(name, age);

        userDao.update(user);

    }*/

    /**
     *
     */
    /*private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User userToDelete = userDao.findById(id).orElse(null);
        userDao.delete(userToDelete);
    }*/
}
