package com.nuramov.hw10_Hibernate_ORM.Service;

import com.nuramov.hw10_Hibernate_ORM.DAO.UserDAOImp;
import com.nuramov.hw10_Hibernate_ORM.model.User;

/**
 * interface UserService представляет собой слой данных в приложении, отвечающий за выполнение бизнес-логики.
 * Сервис содержит внутри себя UserDao, и в своих методах вызывает методы DAO.
 */
public interface UserService {

    User findUser(long id);

    void saveUser(User user);

    void deleteUser(User user);

    void updateUser(User user);
}
