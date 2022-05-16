package com.nuramov.hw12_Web_Server.servlets;

import com.nuramov.hw10_Hibernate_ORM.dao.UserDAOImpWeb;
import com.nuramov.hw10_Hibernate_ORM.model.AddressDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.PhoneDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
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

@WebServlet("/userSave")
public class UserSave extends HttpServlet {
    private UserDAOImpWeb userDao;
    private HttpSession session;
    private String message;
    private Configuration configuration;

    public UserSave(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void init() {
        message = "";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Устанавливаем код успешного ответа (стандартно - ок = 200)
        response.setStatus(HttpServletResponse.SC_OK);

        // Создаем сессию для работы с UserDAOImpWeb разных сервлетах,
        // т.е. UserDAOImpWeb будет передаваться в рамках текущей сессии
        session = request.getSession();

        // Получаем UserDAOImpWeb из сессии, если null, создаем новый объект
        userDao = (UserDAOImpWeb) session.getAttribute("userDao");

        if(userDao == null) {
            userDao = new UserDAOImpWeb();
        }

        // Записываем в сессию объект UserDAOImpWeb
        session.setAttribute("userDao", userDao);

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
        // При дабавлении нового пользователя мы возвращаемся на ту же страницу, чтобы добавить нового
        String buttonValue = request.getParameter("buttonValue");
        if(buttonValue.equals("addUser")) {
            doGet(request, response);
        }

        insertUser(request,response);
    }

    /**
     * Метод insertUser позволяет добавить нового пользователя в БД
     */
    private void insertUser(HttpServletRequest request, HttpServletResponse response) {
        // Получаем и проверяем введенное имя пользователя
        String name = request.getParameter("name");
        if(nameCheck(response, name)) return;

        // Получаем и проверяем введенный возраст пользователя
        String ageStr = request.getParameter("age");
        if(ageCheck(response, ageStr)) return;
        int age = Integer.parseInt(ageStr);

        // Получаем и проверяем введенный номер телефона пользователя
        String phoneNumber = request.getParameter("phone");
        if(phoneNumberCheck(response,phoneNumber)) return;

        // Получаем и проверяем введенный адрес пользователя
        String address = request.getParameter("address");
        if(addressCheck(response, address)) return;

        User newUser = new User(name, age);

        PhoneDataSet phoneDataSet = new PhoneDataSet();
        phoneDataSet.setNumber(phoneNumber);

        AddressDataSet addressDataSet = new AddressDataSet();
        addressDataSet.setStreet(address);

        newUser.setPhone(phoneDataSet);
        newUser.setAddress(addressDataSet);

        userDao.save(newUser);
    }

    /**
     * Метод nameCheck проверяет корректность введенного имени
     * @return - возвращает true, если введено некорректное значение
     */
    private boolean nameCheck(HttpServletResponse response, String name) {
        boolean check = false;

        if(name.equals("")) {
            // Добавили сообщение в сессию, которое будет выведено при выводе страницы с ошибкой
            message = "Enter your name";
            session.setAttribute("message", message);

            try {
                response.sendRedirect("http://localhost:8080/exceptionServlet");
            } catch (IOException e) {
                e.printStackTrace();
            }
            check = true;
        }
        return check;
    }

    /**
     * Метод ageCheck проверяет корректность введенного возраста
     * @return - возвращает true, если введено некорректное значение
     */
    private boolean ageCheck(HttpServletResponse response, String ageStr) {
        boolean check = false;
        int age;

        if(ageStr.equals("")) {
            age = 0;
        } else {
            age = Integer.parseInt(ageStr);
        }

        if(age <= 0) {
            // Добавили сообщение в сессию, которое будет выведено при выводе страницы с ошибкой
            message = "Enter your age";
            session.setAttribute("message", message);

            try {
                response.sendRedirect("http://localhost:8080/exceptionServlet");
            } catch (IOException e) {
                e.printStackTrace();
            }
            check = true;
        }
        return check;
    }

    /**
     * Метод phoneNumberCheck проверяет корректность введенного номера телефона
     * @return - возвращает true, если введено некорректное значение
     */
    private boolean phoneNumberCheck(HttpServletResponse response, String phoneNumber) {
        boolean check = false;

        if(phoneNumber.equals("")) {
            // Добавили сообщение в сессию, которое будет выведено при выводе страницы с ошибкой
            message = "Enter your phone number";
            session.setAttribute("message", message);

            try {
                response.sendRedirect("http://localhost:8080/exceptionServlet");
            } catch (IOException e) {
                e.printStackTrace();
            }
            check = true;
        }
        return check;
    }

    /**
     * Метод addressCheck проверяет корректность введенного адреса
     * @return - возвращает true, если введено некорректное значение
     */
    private boolean addressCheck(HttpServletResponse response, String address) {
        boolean check = false;

        if(address.equals("")) {
            // Добавили сообщение в сессию, которое будет выведено при выводе страницы с ошибкой
            message = "Enter your address";
            session.setAttribute("message", message);

            try {
                response.sendRedirect("http://localhost:8080/exceptionServlet");
            } catch (IOException e) {
                e.printStackTrace();
            }
            check = true;
        }
        return check;
    }
}
