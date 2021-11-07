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
    public User findById(int id) {
        User user;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            user = session.get(User.class, id);
        }
        return user;
    }

    public void save(User user) {
        User savedUser = user;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            //Transaction transaction = session.beginTransaction();
            session.beginTransaction();
            session.save(savedUser);
            //transaction.commit();
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    /*public Auto findAutoById(int id) {
        return HibernateUtil.getSessionFactory().openSession().get(Auto.class, id);
    }*/
}
