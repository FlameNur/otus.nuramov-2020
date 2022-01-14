package com.nuramov.hw10_Hibernate_ORM.service;

import com.nuramov.hw10_Hibernate_ORM.dao.UserDAO;
import com.nuramov.hw10_Hibernate_ORM.model.User;

/**
 * class UserService реализует interface UserService
 */
public class UserServiceImp implements UserService {
    private UserDAO userDAO;

    public UserServiceImp(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User findUser(long id) {
        // Из Optional<User> optionalUser получаем User'a или null
        return userDAO.findById(id).orElse(null);
    }

    @Override
    public long saveUser(User user) {
        return userDAO.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userDAO.delete(user);
    }

    @Override
    public void updateUser(User user) {
        userDAO.update(user);
    }
}
