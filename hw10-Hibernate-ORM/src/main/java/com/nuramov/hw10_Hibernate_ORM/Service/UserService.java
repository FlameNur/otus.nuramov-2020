package com.nuramov.hw10_Hibernate_ORM.Service;

import com.nuramov.hw10_Hibernate_ORM.DAO.UserDAO;
import com.nuramov.hw10_Hibernate_ORM.DAO.UserDAOImp;
import com.nuramov.hw10_Hibernate_ORM.model.User;

/**
 * class UserService представляет собой слой данных в приложении, отвечающий за выполнение бизнес-логики.
 * Сервис содержит внутри себя UserDao, и в своих методах вызывает методы DAO.
 */
public class UserService {
    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAOImp();
    }

    public User findUser(long id) {
        return userDAO.findById(id);
    }

    public void saveUser(User user) {
        userDAO.save(user);
    }

    public void deleteUser(User user) {
        userDAO.delete(user);
    }

    public void updateUser(User user) {
        userDAO.update(user);
    }
}
