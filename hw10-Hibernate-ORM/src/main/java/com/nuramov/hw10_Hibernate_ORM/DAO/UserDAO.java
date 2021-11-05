package com.nuramov.hw10_Hibernate_ORM.DAO;

import com.nuramov.hw10_Hibernate_ORM.model.User;

/**
 * interface UserDAO позволяет создать в приложении слой, который отвечает только за доступ к данным
 * DAO (data access object) — один из наиболее распространенных паттернов проектирования, "Доступ к данным".
 */
public interface UserDAO {
    public User findById(int id);

    public void save(User user);

    public void update(User user);

    public void delete(User user);

    //public Auto findAutoById(int id);
}
