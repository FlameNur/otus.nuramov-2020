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
     * @param user - новый пользователь
     * @return - возвращает id сохраненного пользователя
     */
    long saveUser(User user);

    /**
     * Метод deleteUser позволяет удалить пользователя из БД
     * @param user - пользователь, которого необходимо удалить
     */
    void deleteUser(User user);

    /**
     * Метод updateUser позволяет обновить информацию пользователя в БД
     * @param user - пользователь, чью информацию необходимо обновить
     */
    void updateUser(User user);

    /**
     * Метод getAllUser позволяет вывести список всех пользователей
     * @return - список всех пользователей
     */
    List<User> getAllUser();

    /**
     * Метод insertParametersCheck позволяет проверить параметры нового пользователя
     * на корректность перед тем, как сохранить его в БД
     * @param name - имя пользователя
     * @param ageStr - возраст пользователя
     * @param phoneNumber - телефонный номер пользователя
     * @param address - адрес пользователя
     * @return - возвращает пользователя, которого можно сохранить в БД
     * @throws MyException - выдает ошибку с соответствующим сообщением при нижеследующих проверках
     */
    User insertParametersCheck(String name, String ageStr, String phoneNumber, String address)
            throws MyException;

    /**
     * Метод updateParametersCheck позволяет проверить параметры пользователя, которые предстоит обновить и ввести в БД
     * @param id - id пользователя
     * @param name - имя пользователя
     * @param ageStr - возраст пользователя
     * @param phoneNumber - телефонный номер пользователя
     * @param address - адрес пользователя
     * @return - возвращает пользователя с обновленными параметрами
     * @throws MyException - выдает ошибку с соответствующим сообщением при нижеследующих проверках
     */
    User updateParametersCheck(String id, String name, String ageStr, String phoneNumber, String address)
            throws MyException;
}
