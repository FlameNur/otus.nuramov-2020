package com.nuramov.hw12_Web_Server.service;

import com.nuramov.hw10_Hibernate_ORM.dao.UserDAOImpWeb;
import com.nuramov.hw10_Hibernate_ORM.model.User;

/**
 * класс UserService представляет собой слой данных в приложении, отвечающий за выполнение бизнес-логики.
 * Сервис содержит внутри себя UserDao, и в своих методах вызывает методы DAO.
 */
public class UserServiceWeb {
    private UserDAOImpWeb userDao;

    public UserServiceWeb(UserDAOImpWeb userDao) {
        this.userDao = userDao;
    }

    public User findUser(long id) {
        // Из Optional<User> optionalUser получаем User'a или null
        return this.userDao.findById(id).orElse(null);
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


    // Пример проверки номера регулярными выражениями
    private static boolean validatePhoneNumber(String phoneNo) {
        // для формата "1234567890"
        if (phoneNo.matches("\\d{10}")) return true;
        // российские мобильные + городские с кодом из 3 цифр
        else if(phoneNo.matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")) return true;
        else return false;
    }
}
