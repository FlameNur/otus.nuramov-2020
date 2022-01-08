package com.nuramov.hw10_Hibernate_ORM.dao;

import com.nuramov.hw10_Hibernate_ORM.HibernateUtil;
import com.nuramov.hw10_Hibernate_ORM.model.User;
import org.hibernate.Session;

import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * class UserDAOImp реализует интерфейс UserDAO
 */
public class UserDAOImp implements UserDAO {

    @Override
    public User findById(long id) {
        User user;
        Optional<User> optionalUser;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                // Находим пользователя (User) по id
                user = session.get(User.class, id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            /*// Работаем с Optional, чтобы исключить возможность получения null
            optionalUser = Optional.ofNullable(session.get(User.class, id));
            user = optionalUser.orElseThrow(() -> new RuntimeException("User is not found"));*/
        }
        return user;
    }

    @Override
    public long save(User user) {
        // id User'a в БД
        long id;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                // Сохраняем пользователя (User) в БД и возвращаем его id
                id = (long) session.save(user);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
        return id;
    }

    @Override
    public void update(User user) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                // Обновляем данные пользователя (User) в БД
                session.update(user);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void delete(User user) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                // Сначала присваиваем полю phone значение null, чтобы в случае удаления пользователя (User) из БД
                // не был удален Phone, на котрого ссылаются другие пользователи
                user.setPhone(null);
                session.update(user);
                // Удаляем пользователя (User) из БД
                session.delete(user);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }
}