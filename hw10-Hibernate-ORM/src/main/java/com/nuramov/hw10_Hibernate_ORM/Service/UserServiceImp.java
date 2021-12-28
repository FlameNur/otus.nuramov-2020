package com.nuramov.hw10_Hibernate_ORM.Service;

import com.nuramov.hw10_Hibernate_ORM.DAO.UserDAO;
import com.nuramov.hw10_Hibernate_ORM.DAO.UserDAOImp;
import com.nuramov.hw10_Hibernate_ORM.model.User;

/**
 * class UserService реализует interface UserService
 */
public class UserServiceImp implements UserService {
    private UserDAO userDAO;

    public UserServiceImp() {
        userDAO = new UserDAOImp();
    }

    @Override
    public User findUser(long id) {
        return userDAO.findById(id);
    }

    @Override
    public void saveUser(User user) {
        userDAO.save(user);
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
