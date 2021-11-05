package com.nuramov.hw10_Hibernate_ORM.Servise;

import com.nuramov.hw10_Hibernate_ORM.DAO.UserDAOImp;
import com.nuramov.hw10_Hibernate_ORM.model.User;

import java.util.List;

/**
 * class UserService представляет собой слой данных в приложении, отвечающий за выполнение бизнес-логики.
 * Сервис содержит внутри себя UserDao, и в своих методах вызывает методы DAO.
 */
public class UserService {
    private UserDAOImp userDAOImp = new UserDAOImp();

    public UserService() {
    }

    public User findUser(int id) {
        return userDAOImp.findById(id);
    }

    public void saveUser(User user) {
        userDAOImp.save(user);
    }

    public void deleteUser(User user) {
        userDAOImp.delete(user);
    }

    public void updateUser(User user) {
        userDAOImp.update(user);
    }

    /*public Auto findAutoById(int id) {
        return usersDao.findAutoById(id);
    }*/
}
