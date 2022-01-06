package com.nuramov.hw10_Hibernate_ORM.DAO;

import com.nuramov.hw10_Hibernate_ORM.model.User;

/**
 * interface UserDAO позволяет создать в приложении слой, который отвечает только за доступ к данным
 * DAO (data access object) — один из наиболее распространенных паттернов проектирования, "Доступ к данным".
 */
public interface UserDAO {
    /**
     * Метод findById позволяет найти пользователя (User) по его id в БД
     * @param id - id пользователя (User)
     * @return - пользователя (User)
     */
    User findById(long id);

    /**
     * Метод save позволяет сохранить пользователя (User) в БД
     * @param user - пользователь (User), которого сохраняем в БД
     */
    long save(User user);

    /**
     * Метод update позволяет обновить данные пользователя (User) в БД
     * @param user - пользователь (User), чьи данные обновляются в БД
     */
    void update(User user);

    /**
     * Метод delete позволяет удалить пользователя (User) из БД
     * @param user - пользователь (User), которого удаляем из БД
     */
    void delete(User user);
}
