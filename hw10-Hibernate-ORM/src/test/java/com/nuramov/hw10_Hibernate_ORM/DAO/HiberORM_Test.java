package com.nuramov.hw10_Hibernate_ORM.DAO;

import com.nuramov.hw10_Hibernate_ORM.Service.UserService;
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
    private static User user3;

    private static PhoneDataSet phoneDataSet;

    private static AddressDataSet addressDataSet1;
    private static AddressDataSet addressDataSet2;
    private static AddressDataSet addressDataSet3;

    @BeforeAll
    static void createUserService() {
        userService = new UserService();
    }

    @BeforeAll
    static void createUser() {
        user1 = new User();
        user1.setAge(10);
        user1.setName("Bill");

        user2 = new User();
        user2.setAge(20);
        user2.setName("Sally");

        user3 = new User();
        user3.setAge(50);
        user3.setName("Huanita");
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

        addressDataSet3 = new AddressDataSet();
        addressDataSet3.setStreet("адрес3");
    }

    @BeforeEach
    void start() {
        System.out.println("Начало теста");
    }

    @BeforeEach
    void addUsersToPhone() {
        phoneDataSet.addUser(user1);
        phoneDataSet.addUser(user2);
        phoneDataSet.addUser(user3);
    }

    @BeforeEach
    void addPhone() {
        user1.setPhone(phoneDataSet);
        user2.setPhone(phoneDataSet);
        user3.setPhone(phoneDataSet);
    }

    @BeforeEach
    void addAddress() {
        user1.setAddress(addressDataSet1);
        user2.setAddress(addressDataSet2);
        user3.setAddress(addressDataSet3);
    }

    @Test
    void Test() {
        // Сохраняем User'a 1 в БД
        userService.saveUser(user1);

        // Обновили имя User'a 1 и обновили информацию в БД
        user1.setName("New name");
        userService.updateUser(user1);

        // Сохранили User'ов 2 и 3
        userService.saveUser(user2);
        userService.saveUser(user3);

        // Нашли User'а 1 по id в БД
        User newUser1 = userService.findUser(1);

        assertEquals(1, newUser1.getId());
        assertNotEquals("Bill", newUser1.getName());
        assertEquals("New name", newUser1.getName());
        assertEquals(10, newUser1.getAge());
        assertEquals("адрес1", newUser1.getAddress().getStreet());
        assertEquals("111", newUser1.getPhone().getNumber());

        // Удаляем  User'a 1 из БД
        userService.deleteUser(user1);
        User deletedUser1 = userService.findUser(1);
        assertNull(deletedUser1);

        // Удаляем  User'a 2 из БД
        userService.deleteUser(user2);
        User deletedUser2 = userService.findUser(2);
        assertNull(deletedUser2);
    }

    @AfterEach
    void end() {
        System.out.println("\n" + "Конец теста");
    }
}