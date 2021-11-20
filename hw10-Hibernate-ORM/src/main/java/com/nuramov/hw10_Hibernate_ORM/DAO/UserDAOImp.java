package com.nuramov.hw10_Hibernate_ORM.DAO;

import com.nuramov.hw10_Hibernate_ORM.HibernateUtil;
import com.nuramov.hw10_Hibernate_ORM.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * class UserDAOImp реализует интерфейс UserDAO
 * class UserDAOImp позволяет создать в приложении слой, который отвечает только за доступ к данным.
 * DAO (data access object) — один из наиболее распространенных паттернов проектирования, "Доступ к данным".
 */
public class UserDAOImp implements UserDAO {

    @Override
    public User findById(long id) {
        User user;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            user = session.get(User.class, id);
            session.getTransaction().commit();
        }
        return user;
    }

    public void save(User user) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(User user) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(User user) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            user.setPhone(null);                // добавил
            session.update(user);               // добавил
            //session.getTransaction().commit();  // добавил
            //session.beginTransaction();

            session.delete(user);
            session.getTransaction().commit();
        }
    }
}
