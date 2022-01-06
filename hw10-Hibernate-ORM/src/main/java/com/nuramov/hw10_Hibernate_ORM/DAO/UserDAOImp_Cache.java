package com.nuramov.hw10_Hibernate_ORM.DAO;

import com.nuramov.hw10_Hibernate_ORM.HibernateUtil;
import com.nuramov.hw10_Hibernate_ORM.model.User;
import com.nuramov.hw11_CacheEngine.CacheEngine.CacheEngine;
import com.nuramov.hw11_CacheEngine.CacheEngine.CacheEngineImpl;
import org.hibernate.Session;

/**
 * class UserDAOImp реализует интерфейс UserDAO и работает с кэшом из модуля hw11_CacheEngine
 */
public class UserDAOImp_Cache implements UserDAO {
    CacheEngine<Long, User> cacheEngine;

    public UserDAOImp_Cache(CacheEngine<Long, User> cacheEngine) {
        this.cacheEngine = cacheEngine;
    }

    @Override
    public User findById(long id) {
        // Проверяем в кэше наличие нужного User'a (кэш 2-го уровня)
        User user = cacheEngine.get(id);
        if(user != null) {
            return user;
        }

        // Если в кэше нужного User'a нет, обращаем к Hibernate (кэш 1-го уровня и БД)
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                // Находим пользователя (User) по id
                user = session.get(User.class, id);
                // Сохраняем User'a в кэше 2-го уровня, чтобы в следующий раз достать его уже из кэша
                cacheEngine.put(id, user);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }

    @Override
    public long save(User user) {
        // id User'a в БД
        long id;
        // Сначала сохраняем User'a в БД
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
        // Сохраняем User'a в кэше 2-го уровня
        cacheEngine.put(id, user);
        return id;
    }

    @Override
    public void update(User user) {
        // Не реализовано
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(User user) {
        // Не реализовано
        throw new UnsupportedOperationException();
    }
}
