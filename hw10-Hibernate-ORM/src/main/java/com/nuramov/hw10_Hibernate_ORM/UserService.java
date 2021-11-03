package com.nuramov.hw10_Hibernate_ORM;

import com.nuramov.hw10_Hibernate_ORM.DAO.UserDAO;
import com.nuramov.hw10_Hibernate_ORM.model.User;

import java.util.List;

public class UserService {

    // Просто примеры методов


    private UserDAO usersDao = new UserDAO();

    public UserService() {
    }

    public User findUser(int id) {
        return usersDao.findById(id);
    }

    public void saveUser(User user) {
        usersDao.save(user);
    }

    public void deleteUser(User user) {
        usersDao.delete(user);
    }

    public void updateUser(User user) {
        usersDao.update(user);
    }

    public List<User> findAllUsers() {
        return usersDao.findAll();
    }

    /*public Auto findAutoById(int id) {
        return usersDao.findAutoById(id);
    }*/

}
