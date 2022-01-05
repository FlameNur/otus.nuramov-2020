package com.nuramov.hw10_Hibernate_ORM.Service;

import com.nuramov.hw10_Hibernate_ORM.DAO.UserDAO;
import com.nuramov.hw10_Hibernate_ORM.DAO.UserDAOImp_Cache;
import com.nuramov.hw10_Hibernate_ORM.model.User;
import com.nuramov.hw11_CacheEngine.CacheEngine.CacheEngine;

/**
 * class UserService реализует interface UserService и аботает с кэшом из модуля hw11_CacheEngine
 */
public class UserServiceImp_Cache implements UserService{
    private UserDAO userDAO;

    public UserServiceImp_Cache(CacheEngine<Long, User> cacheEngine) {
        userDAO = new UserDAOImp_Cache(cacheEngine);
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
