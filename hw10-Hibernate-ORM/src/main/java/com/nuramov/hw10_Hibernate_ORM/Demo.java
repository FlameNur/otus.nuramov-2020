package com.nuramov.hw10_Hibernate_ORM;

import com.nuramov.hw10_Hibernate_ORM.Service.UserService;
import com.nuramov.hw10_Hibernate_ORM.model.AddressDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.PhoneDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.User;

public class Demo {
    public static void main(String[] args) {
        UserService userService = new UserService();

        // Создали user'ов 1, 2 и 3
        User user1 = new User();
        user1.setAge(10);
        user1.setName("Bill");

        User user2 = new User();
        user2.setAge(20);
        user2.setName("Sally");

        User user3 = new User();
        user3.setAge(50);
        user3.setName("Huanita");

        // Создали адресы для каждого User'a
        AddressDataSet addressDataSet1 = new AddressDataSet();
        addressDataSet1.setStreet("адрес1");

        AddressDataSet addressDataSet2 = new AddressDataSet();
        addressDataSet2.setStreet("адрес2");

        AddressDataSet addressDataSet3 = new AddressDataSet();
        addressDataSet3.setStreet("адрес3");

        // Добавили адресы
        user1.setAddress(addressDataSet1);
        user2.setAddress(addressDataSet2);
        user3.setAddress(addressDataSet3);

        // Создали телефон для User'ов 1, 2 и 3
        PhoneDataSet phoneDataSet = new PhoneDataSet();
        phoneDataSet.setNumber("111");
        phoneDataSet.addUser(user1);
        phoneDataSet.addUser(user2);
        phoneDataSet.addUser(user3);

        // Добавили телефон для для каждого User'a
        user1.setPhone(phoneDataSet);
        user2.setPhone(phoneDataSet);
        user3.setPhone(phoneDataSet);

        // Сохраняем User'a 1 в БД
        userService.saveUser(user1);

        // Обновили имя User'a 1 и обновили информацию в БД
        user1.setName("New name");
        userService.updateUser(user1);

        // Сохранили User'ов 2 и 3
        userService.saveUser(user2);
        userService.saveUser(user3);

        // Нашли User'ов 1, 2 и 3 по их id в БД
        User newUser1 = userService.findUser(1);
        System.out.println(newUser1);

        User newUser2 = userService.findUser(2);
        System.out.println(newUser2);

        User newUser3 = userService.findUser(3);
        System.out.println(newUser3);

        // Получили адрес и телефон у User'a 3
        AddressDataSet addressUser3 = newUser3.getAddress();
        System.out.println(addressUser3.getStreet());  // "адрес3" все правильно
        PhoneDataSet phoneUser3 = newUser3.getPhone();
        System.out.println(phoneUser3.getNumber());    // "111" все правильно


        // Удаляем  User'a 1 из БД
        userService.deleteUser(user1);
        User deletedUser1 = userService.findUser(1);
        System.out.println(deletedUser1);

        userService.deleteUser(user2);
        User deletedUser2 = userService.findUser(2);
        System.out.println(deletedUser2);

        System.out.println(user2);
        System.out.println(user3);

    }
}
