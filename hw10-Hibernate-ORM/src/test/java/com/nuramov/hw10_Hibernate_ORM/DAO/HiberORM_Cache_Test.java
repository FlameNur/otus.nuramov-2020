package com.nuramov.hw10_Hibernate_ORM.DAO;

import com.nuramov.hw10_Hibernate_ORM.Service.UserService;
import com.nuramov.hw10_Hibernate_ORM.Service.UserServiceImp_Cache;
import com.nuramov.hw10_Hibernate_ORM.model.AddressDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.PhoneDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.User;
import com.nuramov.hw11_CacheEngine.CacheEngine.CacheEngine;
import com.nuramov.hw11_CacheEngine.CacheEngine.CacheEngineImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HiberORM_Cache_Test {
    private static UserService userService;
    private static CacheEngine<Long, User> cacheEngine;

    private static User user1;
    private static User user2;
    private static User user3;

    private static PhoneDataSet phoneDataSet;

    private static AddressDataSet addressDataSet1;
    private static AddressDataSet addressDataSet2;
    private static AddressDataSet addressDataSet3;

    @BeforeAll
    static void createUserService() {
        cacheEngine = new CacheEngineImpl<>(5, 5000, 0);
        userService = new UserServiceImp_Cache(cacheEngine);
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
    void Test() throws InterruptedException {
        // Сохраняем User'ов 1, 2 и 3 в БД
        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);

        // Оцениваем время доступа через кэш и БД
        long startTime_Cache = System.currentTimeMillis();

        // Нашли User'а 1 по id в БД
        User newUser1 = userService.findUser(1);

        System.out.println("Работаем через кэш");
        long accessTime_Cache = System.currentTimeMillis() - startTime_Cache;
        System.out.println("Время доступа: " + accessTime_Cache);

        // Проводим проверки
        assertEquals(1, newUser1.getId());
        assertEquals("Bill", newUser1.getName());
        assertEquals(10, newUser1.getAge());
        assertEquals("адрес1", newUser1.getAddress().getStreet());
        assertEquals("111", newUser1.getPhone().getNumber());

        Thread.sleep(6000);

        // Оцениваем время доступа через БД
        long startTime_DB = System.currentTimeMillis();

        User userFromDB = userService.findUser(1);

        System.out.println("Работаем через БД");
        long accessTime_DB = System.currentTimeMillis() - startTime_DB;
        System.out.println("Время доступа: " + accessTime_DB);

        // Проводим проверки
        assertEquals(1, userFromDB.getId());
        assertEquals("Bill", userFromDB.getName());
        assertEquals(10, userFromDB.getAge());
        assertEquals("адрес1", userFromDB.getAddress().getStreet());
        assertEquals("111", userFromDB.getPhone().getNumber());
    }

    @AfterEach
    void end() {
        System.out.println("\n" + "Конец теста");
    }
}
