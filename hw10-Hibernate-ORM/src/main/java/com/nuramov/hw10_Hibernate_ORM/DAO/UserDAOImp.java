package com.nuramov.hw10_Hibernate_ORM.DAO;

import com.nuramov.hw10_Hibernate_ORM.HibernateUtil;
import com.nuramov.hw10_Hibernate_ORM.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * class UserDAOImp реализует интерфейс UserDAO
 */
public class UserDAOImp implements UserDAO {

    @Override
    public User findById(long id) {
        User user;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            // Находим пользователя (User) по id
            user = session.get(User.class, id);
            session.getTransaction().commit();
        }
        return user;
    }

    @Override
    public void save(User user) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            // Сохраняем пользователя (User) в БД
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(User user) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            // Обновляем данные пользователя (User) в БД
            session.update(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(User user) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            // Сначала присваиваем полю phone значение null, чтобы в случае удаления пользователя (User) из БД
            // не был удален Phone, на котрого ссылаются другие пользователи
            user.setPhone(null);
            session.update(user);
            // Удаляем пользователя (User) из БД
            session.delete(user);
            session.getTransaction().commit();
        }
    }
}
