package com.nuramov.hw12_Web_Server.servlets;

import com.nuramov.hw10_Hibernate_ORM.model.AddressDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.PhoneDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.User;
import com.nuramov.hw12_Web_Server.exceptions.MyException;
import com.nuramov.hw12_Web_Server.services.UserServiceWeb;
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
    private String idStr;

    public UserUpdate(Configuration configuration, UserServiceWeb userServiceWeb) {
        this.configuration = configuration;
        this.userServiceWeb = userServiceWeb;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Устанавливаем код успешного ответа (стандартно - ок = 200)
        response.setStatus(HttpServletResponse.SC_OK);

        // Получаем id пользователя, которого надо обновить
        idStr = request.getParameter("idToUpdate");
        System.out.println("Наш idStr1" + idStr);

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Обновляем информацию пользователя
        String buttonValue = request.getParameter("buttonValue");
        if(buttonValue.equals("userUpdate")) {
            doGet(request, response);
        }

        // Получаем id пользователя, которого надо обновить
        idStr = request.getParameter("idToUpdate");
        System.out.println("Наш idStr2" + idStr);
        try {
            updateUser(request,response);



            // Возможно это просто добавить сюда и убрать верхнюю часть ???????????????????????
            //doGet(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод updateUser позволяет обновить информацию о пользователе в БД
     */
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        // Вызываем сессию
        // Возможно пригодится здесь
        HttpSession session = request.getSession();

        System.out.println("Наш idStr3" + idStr);



        // ТОЛЬКО ДЛЯ ПРОВЕРКИ ПРОПИСАЛ 1  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        idStr = "1";
        User userToUpdate = null;
        try {
            userToUpdate = userServiceWeb.findUser(idStr);
        } catch (MyException e) {
            // Сообщения об ошибке формируются на стороне UserServiceWeb
            session.setAttribute("message", e.getMessage());
            response.sendRedirect("http://localhost:8080/exceptionServlet");
        }

        // Обновляем имя пользователя, если введено новое имя
        String name = request.getParameter("name");
        try {
            userToUpdate = userServiceWeb.nameCheck(name, userToUpdate);
        } catch (MyException e) {
            // Сообщения об ошибке формируются на стороне UserServiceWeb
            session.setAttribute("message", e.getMessage());
            response.sendRedirect("http://localhost:8080/exceptionServlet");
        }




        // Обновляем возраст пользователя, если введено новое значение
        String ageStr = request.getParameter("age");
        int age;
        if(ageStr.equals("")) {
            age = userToUpdate.getAge();
        } else {
            if(ageCheck(response, ageStr)) return;
            age = Integer.parseInt(ageStr);
        }
        userToUpdate.setAge(age);

        // Обновляем телефонный номер пользователя, если введено новое значение
        String phoneNumber = request.getParameter("phone");
        if(phoneNumber.equals("")) {
            phoneNumber = userToUpdate.getPhone().getNumber();
        }
        PhoneDataSet phoneDataSet = new PhoneDataSet();
        phoneDataSet.setNumber(phoneNumber);
        userToUpdate.setPhone(phoneDataSet);

        // Обновляем адрес пользователя, если введен новый адрес
        String address = request.getParameter("address");
        if(address.equals("")) {
            address = userToUpdate.getAddress().getStreet();
        }
        AddressDataSet addressDataSet = new AddressDataSet();
        addressDataSet.setStreet(address);
        userToUpdate.setAddress(addressDataSet);




        // Обновляем пользователя в БД
        userServiceWeb.updateUser(userToUpdate);
    }

    /**
     * Метод ageCheck проверяет корректность введенного возраста
     * @return - возвращает true, если введено некорректное значение
     */
    private boolean ageCheck(HttpServletResponse response, String ageStr) throws IOException {
        boolean check = false;
        int age;

        if(ageStr.equals("")) {
            age = 0;
        } else {
            age = Integer.parseInt(ageStr);
        }

        if(age <= 0) {
            // Добавили сообщение в сессию, которое будет выведено при выводе страницы с ошибкой
            //session.setAttribute("message", "Enter your age");

            response.sendRedirect("http://localhost:8080/exceptionServlet");
            check = true;
        }
        return check;
    }
}
