package com.nuramov.hw12_Web_Server.servlets;

import com.nuramov.hw10_Hibernate_ORM.dao.UserDAOImpWeb;
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

/**
 * Сервлет UserUpdate обновляет информацию пользователя в БД по введенному id
 */
@WebServlet("/userUpdate")
public class UserUpdate extends HttpServlet {
    private UserDAOImpWeb userDao;
    private HttpSession session;
    private int idToUpdate;
    private String message;
    private Configuration configuration;

    public UserUpdate(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void init() {
        idToUpdate = 0;
        message = "";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        //Map<String, Object> templateData = new HashMap<>();

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

        try {
            updateUser(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Обнуляем idToUpdate перед тем, как записать его снова в текущую сессию
        idToUpdate = 0;
        // Записываем idToUpdate в текущую сессию
        session.setAttribute("idToUpdate", idToUpdate);
        // Записываем в сессию объект UserDAOImpWeb
        session.setAttribute("userDao", userDao);
    }

    /**
     * Метод updateUser позволяет обновить информацию о пользователе в БД
     */
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        // Получаем id пользователя, которого надо обновить
        idToUpdate = (int) session.getAttribute("idToUpdate");
        User userToUpdate = userDao.findById(idToUpdate).orElse(null);
        // Надо ли еще раз проверять на null?
        // Проверяем наличие пользователя по введенному id (на null)
        if(userCheck(response, userToUpdate)) return;

        // Обновляем имя пользователя, если введено новое имя
        String name = request.getParameter("name");
        if(name.equals("")) {
            name = userToUpdate.getName();
        }
        userToUpdate.setName(name);

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
        userDao.update(userToUpdate);
    }

    /**
     * Метод userCheck проверяет наличие пользователя по введенному id (на null)
     * и выводит при этом соответсвующее сообщение
     * @return - возвращает true, если пользователь не найден (null)
     */
    private boolean userCheck(HttpServletResponse response, User user) throws IOException {
        boolean check = false;

        if(user == null) {
            // Добавили сообщение в сессию, которое будет выведено при выводе страницы с ошибкой
            message = "User is not found";
            session.setAttribute("message", message);

            response.sendRedirect("http://localhost:8080/exceptionServlet");
            check = true;
        }
        return check;
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
            message = "Enter your age";
            session.setAttribute("message", message);

            response.sendRedirect("http://localhost:8080/exceptionServlet");
            check = true;
        }
        return check;
    }
}
