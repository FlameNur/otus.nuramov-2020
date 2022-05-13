package com.nuramov.hw12_Web_Server.servlets;

import com.nuramov.hw10_Hibernate_ORM.dao.UserDAOImp_Web;
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
    private HttpSession session;
    private String message;

    @Override
    public void init() {
        // Создаем набор данных, используемый шаблоном
        templateData = new HashMap<>();

        message = "";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Устанавливаем код успешного ответа (стандартно - ок = 200)
        response.setStatus(HttpServletResponse.SC_OK);

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

        // Создаем объект конфигурации Freemarker
        Configuration configuration = new Configuration(new Version("2.3.31"));

        // Задаем путь, по которому находится файл шаблона. Не совсем понятно что задавать, пока и так работает
        configuration.setClassForTemplateLoading(UsersInfo.class, "/");
        configuration.setDefaultEncoding("UTF-8");

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
        // Проверяем какая кнопка нажата - Update или Delete
        String buttonValue = request.getParameter("buttonValue");

        if(buttonValue.equals("update")) {
            try {
                updateUser(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(buttonValue.equals("delete")) {
            try {
                deleteUser(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            doGet(request, response);
        }
    }

    /**
     * Метод updateUser позволяет обновить информацию о пользователе в БД
     */
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String idStr = request.getParameter("idToUpdate");
        // Проверяем корректность введенного id
        if(idCheck(response, idStr)) return;
        int idToUpdate = Integer.parseInt(idStr);

        User userToUpdate = userDao.findById(idToUpdate).orElse(null);
        // Проверяем наличие пользователя по введенному id (на null)
        if(userCheck(response, userToUpdate)) return;

        // Создаем сессию для работы с idToUpdate разных сервлетах,
        // т.е. idToUpdate будет передаваться в рамках текущей сессии
        session = request.getSession();
        // Записываем в сессию объект idToUpdate
        session.setAttribute("idToUpdate", idToUpdate);

        // Поменяли на другой адрес и сервлет
        response.sendRedirect("http://localhost:8080/userUpdate");
    }

    /**
     * Метод deleteUser позволяет удалить пользователя по заданному id
     */
    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String idStr = request.getParameter("idToDelete");
        // Проверяем корректность введенного id
        if(idCheck(response, idStr)) return;
        int id = Integer.parseInt(idStr);

        User userToDelete = userDao.findById(id).orElse(null);
        // Проверяем наличие пользователя по введенному id (на null)
        if(userCheck(response, userToDelete)) return;

        userDao.delete(userToDelete);
    }

    /**
     * Метод idCheck позволяет проверить корректность введенного id
     * @return - возвращает true, если введено некорректное значение
     */
    private boolean idCheck(HttpServletResponse response, String idStr) throws IOException {
        boolean check = false;
        int id;

        if(idStr.equals("")) {
            id = 0;
        } else {
            id = Integer.parseInt(idStr);
        }

        if(id <= 0) {
            // Добавили сообщение в сессию, которое будет выведено при выводе страницы с ошибкой
            message = "Enter a valid id value";
            session.setAttribute("message", message);

            response.sendRedirect("http://localhost:8080/exceptionServlet");
            check = true;
        }
        return check;
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
}
