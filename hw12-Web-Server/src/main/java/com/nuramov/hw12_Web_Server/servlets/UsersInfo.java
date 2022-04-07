package com.nuramov.hw12_Web_Server.servlets;

import com.nuramov.hw10_Hibernate_ORM.dao.UserDAOImp_Web;
import com.nuramov.hw10_Hibernate_ORM.model.AddressDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.PhoneDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/usersInfo")
public class UsersInfo extends HttpServlet {
    private UserDAOImp_Web userDao;
    Map<String, Object> templateData;

    public void init() {
        userDao = new UserDAOImp_Web();
        templateData = new HashMap<>();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Устанавливаем код успешного ответа (стандартно - ок = 200)
        response.setStatus(HttpServletResponse.SC_OK);

        // Конфиги для Freemarker
        Configuration configuration = new Configuration(new Version("2.3.31"));

        configuration.setClassForTemplateLoading(UserSave.class, "/");
        configuration.setDefaultEncoding("UTF-8");

        try (Writer writer = new StringWriter()) {
            Template template = configuration.getTemplate("listOfUsers.html");
            template.process(templateData, writer);

            response.getWriter().println(writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            insertUser(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listUser(request, response);

        doGet(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");

        User newUser = new User(name, age);

        PhoneDataSet phoneDataSet = new PhoneDataSet();
        phoneDataSet.setNumber(phoneNumber);

        AddressDataSet addressDataSet = new AddressDataSet();
        addressDataSet.setStreet(address);

        newUser.setPhone(phoneDataSet);
        newUser.setAddress(addressDataSet);

        userDao.save(newUser);

        // Это уже не надо, вроде. Надо попробовать с этим сначала, чтобы сформировать ответ
        //response.sendRedirect("http://localhost:8080/usersInfo");
    }

    private void listUser(HttpServletRequest request, HttpServletResponse response) {
        List<User> listUser = userDao.getAllUser();

        //Пока не работает
        User user = listUser.get(1);
        templateData.put("test", user.getName());

        // ??? Для чего не понятно пока
        //request.setAttribute("listUser", listUser);


    }


    /*private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int age = Integer.parseInt(request.getParameter("age"));
        String name = request.getParameter("name");

        User user = new User(name, age);

        userDao.update(user);
        response.sendRedirect("list");
    }*/

    /*private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User userToDelete = userDao.findById(id).orElse(null);
        userDao.delete(userToDelete);
        response.sendRedirect("list");
    }*/
}
