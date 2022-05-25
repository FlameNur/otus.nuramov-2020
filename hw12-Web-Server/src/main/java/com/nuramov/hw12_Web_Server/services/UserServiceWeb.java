package com.nuramov.hw12_Web_Server.services;

import com.nuramov.hw10_Hibernate_ORM.model.User;
import com.nuramov.hw12_Web_Server.exceptions.MyException;

import java.util.List;

/**
 * Интерфейс UserServiceWeb представляет собой слой данных в приложении, отвечающий за выполнение бизнес-логики.
 * Сервис содержит внутри себя UserDao, и в своих методах вызывает методы DAO.
 */
public interface UserServiceWeb {

    /**
     * Находим пользователя по введенному id (id получаем с html-страницы в формате String)
     * @param idStr - id пользователя
     * @return - возвращаем соответствующего id пользователя
     * @throws MyException - выдает ошибку с соответствующим сообщением, если введенный id не корректный
     * или невозможно найти пользователя
     */
    User findUser(String idStr) throws MyException;

    /**
     * Метод saveUser позволяет сохранить нового пользователя в БД
     * @param name - имя пользователя
     * @param age - возраст пользователя
     * @param phoneNumber - телефонный номер пользователя
     * @param address - адрес пользователя
     * @return - возвращает id сохраненного пользователя
     */
    long saveUser(String name, String age, String phoneNumber, String address);

    /**
     * Метод deleteUser позволяет удалить пользователя из БД
     * @param id - id пользователя, которого необходимо удалить
     */
    void deleteUser(String id);

    /**
     * Метод updateUser позволяет обновить информацию пользователя в БД
     * @param id - id пользователя
     * @param name - имя пользователя
     * @param age - возраст пользователя
     * @param phoneNumber - телефонный номер пользователя
     * @param address - адрес пользователя
     */
    void updateUser(String id, String name, String age, String phoneNumber, String address);

    /**
     * Метод getAllUser позволяет вывести список всех пользователей
     * @return - список всех пользователей
     */
    List<User> getAllUser();
}
