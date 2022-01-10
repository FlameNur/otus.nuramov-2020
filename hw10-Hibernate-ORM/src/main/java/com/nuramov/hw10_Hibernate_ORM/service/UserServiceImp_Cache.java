package com.nuramov.hw10_Hibernate_ORM.service;

import com.nuramov.hw10_Hibernate_ORM.dao.UserDAO;
import com.nuramov.hw10_Hibernate_ORM.model.User;
import com.nuramov.hw11_CacheEngine.cache_engine.CacheEngine;

import java.util.Optional;

/**
 * class UserService реализует interface UserService и работает с кэшом из модуля hw11_CacheEngine на этом уровне
 */
public class UserServiceImp_Cache implements UserService{
    private UserDAO userDAO;
    private CacheEngine<Long, User> cacheEngine;

    public UserServiceImp_Cache(UserDAO userDAO, CacheEngine<Long, User> cacheEngine) {
        this.userDAO = userDAO;
        this.cacheEngine = cacheEngine;
    }

    @Override
    public User findUser(long id) {
        /*// Проверяем в кэше наличие нужного User'a (кэш 2-го уровня)
        User user = cacheEngine.get(id);
        if(user != null) {
            return user;
        } else {
            // Проверяем наличие внутри Optional наличие User'a
            if(userDAO.findById(id).isPresent()) {
                // Если в кэше нужного User'a нет, обращаем к Hibernate (кэш 1-го уровня и БД)
                user = userDAO.findById(id).get();
                // Сохраняем User'a в кэше 2-го уровня, чтобы в следующий раз достать его уже из кэша
                cacheEngine.put(id, user);
            }
        }
        return user;*/

        Optional<User> optionalUser = cacheEngine.get(id);
        User user = null;
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            // Проверяем наличие внутри Optional наличие User'a
            if(userDAO.findById(id).isPresent()) {
                // Если в кэше нужного User'a нет, обращаем к Hibernate (кэш 1-го уровня и БД)
                user = userDAO.findById(id).get();
                // Сохраняем User'a в кэше 2-го уровня, чтобы в следующий раз достать его уже из кэша
                cacheEngine.put(id, user);
            }
        }
        return user;
    }

    @Override
    public long saveUser(User user) {
        // Сохраняем User'a в БД
        long id = userDAO.save(user);
        // Сохраняем User'a в кэше 2-го уровня
        cacheEngine.put(id, user);
        return id;
    }

    @Override
    public void deleteUser(User user) {
        // Не реализовано
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateUser(User user) {
        // Не реализовано
        throw new UnsupportedOperationException();
    }

    public void clearCache() {
        cacheEngine.dispose();
    }
}
