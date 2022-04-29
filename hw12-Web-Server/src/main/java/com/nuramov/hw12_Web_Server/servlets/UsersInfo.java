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
        userDao = new UserDAOImp_Web();
        templateData = new HashMap<>();
        id = 0;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Устанавливаем код успешного ответа (стандартно - ок = 200)
        response.setStatus(HttpServletResponse.SC_OK);

        // Конфиги для Freemarker
        Configuration configuration = new Configuration(new Version("2.3.31"));

        configuration.setClassForTemplateLoading(UserSave.class, "/");
        configuration.setDefaultEncoding("UTF-8");


        // Добавляем пользователя в БД
        try {
            id = insertUser(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }



        // Пробуем вывести список
        //listUser(request, response);


        // Вариант работы с update и delete
        // Надо проработать
        String buttonValue = request.getParameter("buttonValue");

        // Не работает при простом нажатии
        if(buttonValue.equals("update")) {
            System.out.println(buttonValue);
            /*try {
                updateUser(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }*/

        } else if(buttonValue.equals("delete")) {
            System.out.println(buttonValue);

            /*try {
                deleteUser(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }*/
        } else {
            System.out.println(buttonValue);

        }







        // Так можно найти добавленного пользователя и вывести на консоль и страницу
        User findedUser = userDao.findById(id).get();

        templateData.put("name", findedUser.getName());
        templateData.put("age", findedUser.getAge());

        templateData.put("phone", findedUser.getPhone().getNumber());
        templateData.put("address", findedUser.getAddress().getStreet());

        //_________________________________________





        try (Writer writer = new StringWriter()) {
            Template template = configuration.getTemplate("UsersInfo.html");
            template.process(templateData, writer);

            response.getWriter().println(writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Пока работает и без этого
        //doGet(request, response);
        //listUser(request, response);
    }

    // Вроде, работает

    /**
     *
     * @param request
     * @param response
     * @return
     * @throws SQLException
     * @throws IOException
     */
    private long insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String phoneNumber = request.getParameter("phone");
        String address = request.getParameter("address");

        User newUser = new User(name, age);

        PhoneDataSet phoneDataSet = new PhoneDataSet();
        phoneDataSet.setNumber(phoneNumber);

        AddressDataSet addressDataSet = new AddressDataSet();
        addressDataSet.setStreet(address);

        newUser.setPhone(phoneDataSet);
        newUser.setAddress(addressDataSet);

        long id = userDao.save(newUser);
        return id;
    }

    /**
     *
     * @param request
     * @param response
     */
    private void listUser(HttpServletRequest request, HttpServletResponse response) {
        List<User> listUser = userDao.getAllUser();

        User userer = listUser.get((int) id);

    }

    /**
     *
     * @param request
     * @param response
     * @throws SQLException
     * @throws IOException
     */
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int age = Integer.parseInt(request.getParameter("age"));
        String name = request.getParameter("name");

        User user = new User(name, age);

        userDao.update(user);

    }

    /**
     *
     * @param request
     * @param response
     * @throws SQLException
     * @throws IOException
     */
    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User userToDelete = userDao.findById(id).orElse(null);
        userDao.delete(userToDelete);



    }
}
