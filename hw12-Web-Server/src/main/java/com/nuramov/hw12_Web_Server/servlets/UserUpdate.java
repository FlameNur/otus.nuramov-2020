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
    private HttpSession session;
    private int idToUpdate;

    @Override
    public void init() {
        idToUpdate = 0;
        System.out.println("Проверка на уровне инициализации");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Устанавливаем код успешного ответа (стандартно - ок = 200)
        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println("1Проверка перед запуском страницы!!!");

        // Создаем сессию для работы с UserDAOImp_Web разных сервлетах,
        // т.е. UserDAOImp_Web будет передаваться в рамках текущей сессии
        session = request.getSession();

        // Получаем UserDAOImp_Web из сессии, если null, создаем новый объект
        userDao = (UserDAOImp_Web) session.getAttribute("userDao");

        if(userDao == null) {
            userDao = new UserDAOImp_Web();
        }

        // Записываем в сессию объект UserDAOImp_Web
        session.setAttribute("userDao", userDao);

        // Конфиги для Freemarker
        Configuration configuration = new Configuration(new Version("2.3.31"));

        configuration.setClassForTemplateLoading(UserUpdate.class, "/");
        configuration.setDefaultEncoding("UTF-8");

        Map<String, Object> templateData = new HashMap<>();

        try (Writer writer = new StringWriter()) {
            System.out.println("2Проверка перед запуском страницы!!!");
            Template template = configuration.getTemplate("infoForUpdating.html");
            template.process(templateData, writer);

            response.getWriter().println(writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Обновляем информацию пользователя
        String buttonValue = request.getParameter("buttonValue");
        if(buttonValue.equals("userUpdate")) {
            doGet(request, response);
        }

        // Плучаем id пользователя, которого надо обновить
        idToUpdate = (int) session.getAttribute("idToUpdate");

        try {
            updateUser(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Обнуляем idToUpdate перед тем, как записать его снова в текущую сессию
        idToUpdate = 0;

        // Записываем idToUpdate в текущую сессию
        session.setAttribute("idToUpdate", idToUpdate);

        // Записываем в сессию объект UserDAOImp_Web
        // Надо проверить где и как работает в get или post!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        session.setAttribute("userDao", userDao);
    }

    /**
     * Метод updateUser позволяет обновить информацию о пользователе в БД
     */
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        //int id = Integer.parseInt(request.getParameter("idToUpdate"));
        User userToUpdate = userDao.findById(idToUpdate).orElse(null);

        // Надо доработать!!!!!!!!!!!!!!!! Это возможно уже не надо, потому что до этого проверяем уже
        if(userToUpdate == null) {
            throw new IOException();
        }

        String name = request.getParameter("name");
        if(!name.equals("")) {
            userToUpdate.setName(name);
        }

        int age = Integer.parseInt(request.getParameter("age"));
        if(age > 0) {
            userToUpdate.setAge(age);
        }

        String phoneNumber = request.getParameter("phone");
        if(!phoneNumber.equals("")) {
            PhoneDataSet phoneDataSet = new PhoneDataSet();
            phoneDataSet.setNumber(phoneNumber);
            userToUpdate.setPhone(phoneDataSet);
        }

        String address = request.getParameter("address");
        if(!address.equals("")) {
            AddressDataSet addressDataSet = new AddressDataSet();
            addressDataSet.setStreet(address);
            userToUpdate.setAddress(addressDataSet);
        }

        userDao.update(userToUpdate);
    }
}
