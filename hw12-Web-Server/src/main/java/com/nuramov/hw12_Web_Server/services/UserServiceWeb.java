package com.nuramov.hw12_Web_Server.services;

import com.nuramov.hw10_Hibernate_ORM.dao.UserDAOImpWeb;
import com.nuramov.hw10_Hibernate_ORM.model.AddressDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.PhoneDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.User;
import com.nuramov.hw12_Web_Server.exceptions.MyException;

import java.util.List;
import java.util.regex.Pattern;

/**
 * класс UserService представляет собой слой данных в приложении, отвечающий за выполнение бизнес-логики.
 * Сервис содержит внутри себя UserDao, и в своих методах вызывает методы DAO.
 */
public class UserServiceWeb {
    private UserDAOImpWeb userDao;

    public UserServiceWeb(UserDAOImpWeb userDao) {
        this.userDao = userDao;
    }

    /**
     * Находим пользователя по введенному id (id получаем с html-страницы в формате String)
     * @param idStr - id пользователя
     * @return - возвращаем соответствующего id пользователя
     * @throws MyException - выдает ошибку с соответствующим сообщением, если введенный id не корректный
     * или невозможно найти пользователя
     */
    public User findUser(String idStr) throws MyException {
        // Проверяем корректность введенного id
        idCheck(idStr);

        int id = Integer.parseInt(idStr);
        // Из Optional<User> optionalUser получаем User'a или null
        User user = this.userDao.findById(id).orElse(null);

        // Если при положительном значении id не смогли найти пользователя в БД, то выдаем ошибку
        if(user == null) {
            throw new MyException("User is not found");
        }
        return user;
    }

    /**
     * Метод saveUser позволяет сохранить нового пользователя в БД
     * @param user - новый пользователь
     * @return - возвращает id сохраненного пользователя
     */
    public long saveUser(User user) {
        return this.userDao.save(user);
    }

    /**
     * Метод deleteUser позволяет удалить пользователя из БД
     * @param user - пользователь, которого необходимо удалить
     */
    public void deleteUser(User user) {
        this.userDao.delete(user);
    }

    /**
     * Метод updateUser позволяет обновить информацию пользователя в БД
     * @param user - пользователь, чью информацию необходимо обновить
     */
    public void updateUser(User user) {
        this.userDao.update(user);
    }

    /**
     * Метод getAllUser позволяет вывести список всех пользователей
     * @return - список всех пользователей
     */
    public List<User> getAllUser() {
        return this.userDao.getAll();
    }

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
    public User insertParametersCheck(String name, String ageStr, String phoneNumber, String address)
            throws MyException {
        User newUser = new User();

        // Проверяем все введенные параметры нового пользователя прежде чем добавить его в БД
        nameCheck(name, newUser);
        ageCheck(ageStr, newUser);
        phoneNumberCheck(phoneNumber, newUser);
        addressCheck(address, newUser);

        return newUser;
    }

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
    public User updateParametersCheck(String id, String name, String ageStr, String phoneNumber, String address)
            throws MyException {
        // Проверяем id
        User user = findUser(id);

        // Если новое имя не введено, сохраняем старое имя
        if(name.equals("")) name = user.getName();
        // Проверяем имя на корректность
        nameCheck(name, user);

        // Если новый возраст не введен, сохраняем старый возраст
        if(ageStr.equals("")) ageStr = String.valueOf(user.getAge());
        // Проверяем возраст на корректность
        ageCheck(ageStr, user);

        // Если новый телефонный номер не введен, сохраняем старый телефонный номер
        if(phoneNumber.equals("")) phoneNumber = user.getPhone().getNumber();
        // Проверяем телефонный номер на корректность
        phoneNumberCheck(phoneNumber, user);

        // Если новый адрес не введен, сохраняем старый адрес
        if(address.equals("")) address = user.getAddress().getStreet();
        // Проверяем адрес на корректность
        addressCheck(address, user);

        return user;
    }

    /**
     * Метод idCheck позволяет проверить корректность введенного id
     * @param idStr - id пользователя
     * @throws MyException - выдает ошибку с соответствующим сообщением при нижеследующих проверках
     */
    private void idCheck(String idStr) throws MyException {
        // Если пустое поле, "id = 0" или "нечисловое значение", то выдаем ошибку с соответствующим сообщением
        if(idStr.equals("") || idStr.equals("0") || !Pattern.matches("\\b[\\d]+\\b", idStr)) {
            throw new MyException("Enter a valid id value");
        }
    }

    /**
     * Метод nameCheck позволяет проверить имя на корректность
     * @param name - новое имя пользователя
     * @param user - пользователь
     * @throws MyException - выдает ошибку с соответствующим сообщением при нижеследующих проверках
     */
    private void nameCheck(String name, User user) throws MyException {
        // Имя должно содержать только латинские буквы
        if(!Pattern.matches("\\b[a-zA-Z]+\\b", name)) {
            throw new MyException("Enter your name in Latin letters");
        }
        user.setName(name);
    }

    /**
     * Метод ageCheck позволяет проверить возраст на корректность
     * @param ageStr - новый возраст пользователя
     * @param user - пользователь
     * @throws MyException - выдает ошибку с соответствующим сообщением при нижеследующих проверках
     */
    private void ageCheck(String ageStr, User user) throws MyException {
        // Если пустое поле, "id = 0" или "нечисловое значение", то выдаем ошибку с соответствующим сообщением
        if(ageStr.equals("0") || !Pattern.matches("\\b[\\d]+\\b", ageStr)) {
            throw new MyException("Enter a valid age value");
        }
        user.setAge(Integer.parseInt(ageStr));
    }

    /**
     * Метод phoneNumberCheck позволяет проверить телефонный номер на корректность
     * @param phoneNumber - новый телефонный номер пользователя
     * @param user - пользователь
     * @throws MyException - выдает ошибку с соответствующим сообщением при нижеследующих проверках
     */
    private void phoneNumberCheck(String phoneNumber, User user) throws MyException {
        // Упрощенная проверка номера телефона - любые положительные числовые значения
        if(phoneNumber.equals("0") || !Pattern.matches("\\b[\\d]+\\b", phoneNumber)) {
            throw new MyException("Enter a valid phone number");
        }
        PhoneDataSet phoneDataSet = new PhoneDataSet();
        phoneDataSet.setNumber(phoneNumber);
        user.setPhone(phoneDataSet);
    }

    /**
     * Метод addressCheck позволяет проверить адрес на корректность
     * @param address - новый адрес пользователя
     * @param user - пользователь
     * @throws MyException - выдает ошибку с соответствующим сообщением при нижеследующих проверках
     */
    private void addressCheck(String address, User user) throws MyException {
        // Упрощенная проверка адреса - любые числовые значения и латинские буквы
        if(address.equals("0") || !Pattern.matches("\\b[[a-zA-Z0-9+-]]+\\b", address)) {
            throw new MyException("Enter a valid address");
        }
        AddressDataSet addressDataSet = new AddressDataSet();
        addressDataSet.setStreet(address);
        user.setAddress(addressDataSet);
    }
}
