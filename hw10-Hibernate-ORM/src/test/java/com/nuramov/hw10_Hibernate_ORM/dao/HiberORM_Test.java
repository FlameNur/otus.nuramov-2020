package com.nuramov.hw10_Hibernate_ORM.dao;

import com.nuramov.hw10_Hibernate_ORM.service.UserService;
import com.nuramov.hw10_Hibernate_ORM.service.UserServiceImp;
import com.nuramov.hw10_Hibernate_ORM.model.AddressDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.PhoneDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HiberORM_Test {
    private static UserService userService;

    private static User user1;
    private static User user2;

    private static PhoneDataSet phoneDataSet;

    private static AddressDataSet addressDataSet1;
    private static AddressDataSet addressDataSet2;

    @BeforeAll
    static void createUserService() {
        UserDAO userDAO = new UserDAOImp();
        userService = new UserServiceImp(userDAO);
    }

    @BeforeAll
    static void createUser() {
        user1 = new User("Bill", 10);
        user2 = new User("Sally", 20);
    }

    @BeforeAll
    static void createPhone() {
        phoneDataSet = new PhoneDataSet();
        phoneDataSet.setNumber("111");
    }

    @BeforeAll
    static void createAddress() {
        addressDataSet1 = new AddressDataSet();
        addressDataSet1.setStreet("адрес1");

        addressDataSet2 = new AddressDataSet();
        addressDataSet2.setStreet("адрес2");
    }

    @BeforeEach
    void start() {
        System.out.println("Начало теста");
    }

    @BeforeEach
    void addPhone() {
        user1.setPhone(phoneDataSet);
        user2.setPhone(phoneDataSet);
    }

    @BeforeEach
    void addAddress() {
        user1.setAddress(addressDataSet1);
        user2.setAddress(addressDataSet2);
    }

    @Test
    void userServiceMethodsTest() {
        // Сохраняем User'a 1 в БД
        long id1 = userService.saveUser(user1);

        // Обновили имя User'a 1 и обновили информацию в БД
        user1.setName("New name");
        userService.updateUser(user1);

        // Сохранили User'ов 2 и 3
        long id2 = userService.saveUser(user2);

        // Нашли User'а 1 по id в БД
        User newUser1 = userService.findUser(id1);

        assertEquals(id1, newUser1.getId());
        assertNotEquals("Bill", newUser1.getName());
        assertEquals("New name", newUser1.getName());
        assertEquals(10, newUser1.getAge());
        assertEquals("адрес1", newUser1.getAddress().getStreet());
        assertEquals("111", newUser1.getPhone().getNumber());

        // Удаляем  User'a 1 из БД
        userService.deleteUser(user1);
        User deletedUser1 = userService.findUser(id1);
        System.out.println(deletedUser1);

        // Удаляем  User'a 2 из БД
        userService.deleteUser(user2);
        User deletedUser2 = userService.findUser(id2);
        System.out.println(deletedUser2);
    }

    @AfterEach
    void end() {
        System.out.println("\n" + "Конец теста");
    }
}