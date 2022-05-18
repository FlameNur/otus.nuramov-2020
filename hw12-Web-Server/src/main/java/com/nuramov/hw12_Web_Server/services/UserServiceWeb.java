package com.nuramov.hw12_Web_Server.services;

import com.nuramov.hw10_Hibernate_ORM.dao.UserDAOImpWeb;
import com.nuramov.hw10_Hibernate_ORM.model.User;
import com.nuramov.hw12_Web_Server.exceptions.MyException;

import java.util.List;
import java.util.regex.Pattern;

/**
 * класс UserService представляет собой слой данных в приложении, отвечающий за выполнение бизнес-логики.
 * Сервис содержит внутри себя UserDao, и в своих методах вызывает методы DAO.
 */
public class UserServiceWeb {
    private UserDAOImpWeb userDao;

    public UserServiceWeb(UserDAOImpWeb userDao) {
        this.userDao = userDao;
    }

    /**
     * Находим пользователя по введенному id (id получаем с html-страницы в формате String)
     * @param idStr - id пользователя
     * @return - возвращаем соответствующего id пользователя
     * @throws MyException - выдает ошибку с соответствующим сообщением, если введенный id не корректный
     * или невозможно найти пользователя
     */
    public User findUser(String idStr) throws MyException {
        // Проверяем корректность введенного id
        idCheck(idStr);

        int id = Integer.parseInt(idStr);
        // Из Optional<User> optionalUser получаем User'a или null
        User user = this.userDao.findById(id).orElse(null);

        // Если при положительном значении id не смогли найти пользователя в БД, то выдаем ошибку
        if(user == null) {
            throw new MyException("User is not found");
        }
        return user;
    }

    public long saveUser(User user) {
        return this.userDao.save(user);
    }

    public void deleteUser(User user) {
        this.userDao.delete(user);
    }

    public void updateUser(User user) {
        this.userDao.update(user);
    }

    public List<User> getAllUser() {
        return this.userDao.getAll();
    }

    /**
     * Метод nameCheck позволяет проверить имя на корректность
     * @param name - новое имя пользователя
     * @param user - пользователь
     * @return - возвращает пользователя с обновленным именем
     * @throws MyException - выдает ошибку с соответствующим сообщением при нижеследующих проверках
     */
    public User nameCheck(String name, User user) throws MyException {
        if(user == null) {
            throw new MyException("User is not found");
        }

        // Имя должно содержать только латинские буквы
        if(!Pattern.matches("\\b[a-zA-Z]+\\b", name)) {
            throw new MyException("Enter your name");
        }

        // Если новое имя не введено, сохраняем старое имя
        if(name.equals("")) {
            name = user.getName();
        }

        user.setName(name);
        return user;
    }

    /**
     * Метод idCheck позволяет проверить корректность введенного id
     */
    private void idCheck(String idStr) throws MyException {
        // Если пустое поле, "id = 0" или "нечисловое значение", то выдаем ошибку с соответствующим сообщением
        if(idStr.equals("") || idStr.equals("0") || !Pattern.matches("\\b[\\d]+\\b", idStr)) {
            throw new MyException("Enter a valid id value");
        }
    }
}
