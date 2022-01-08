package com.nuramov.hw10_Hibernate_ORM.dao;

import com.nuramov.hw10_Hibernate_ORM.service.UserService;
import com.nuramov.hw10_Hibernate_ORM.service.UserServiceImp_Cache;
import com.nuramov.hw10_Hibernate_ORM.model.AddressDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.PhoneDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.User;
import com.nuramov.hw11_CacheEngine.cache_engine.CacheEngine;
import com.nuramov.hw11_CacheEngine.cache_engine.CacheEngineImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HiberORM_Cache_Test {
    private static UserServiceImp_Cache userService;

    private static User user1;
    private static PhoneDataSet phoneDataSet;
    private static AddressDataSet addressDataSet1;

    @BeforeAll
    static void createUserService() {
        UserDAO userDAO = new UserDAOImp();
        CacheEngine<Long, User> cacheEngine = new CacheEngineImpl<>(5, 5000, 0);
        userService = new UserServiceImp_Cache(userDAO, cacheEngine);
    }

    @BeforeAll
    static void createUser() {
        user1 = new User("Bill", 10);
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
    }

    @BeforeEach
    void start() {
        System.out.println("\n" + "Начало теста");
    }

    @BeforeEach
    void addPhone() {
        user1.setPhone(phoneDataSet);
    }

    @BeforeEach
    void addAddress() {
        user1.setAddress(addressDataSet1);
    }

    @Test
    void cacheDataIntegrityTest() {
        // Сохраняем User'а 1 в БД
        long id1 = userService.saveUser(user1);

        // Работаем через кэш. Нашли User'а 1 по id в кэше
        User newUser1 = userService.findUser(id1);

        // Проводим проверки
        assertEquals(id1, newUser1.getId());
        assertEquals("Bill", newUser1.getName());
        assertEquals(10, newUser1.getAge());
        assertEquals("адрес1", newUser1.getAddress().getStreet());
        assertEquals("111", newUser1.getPhone().getNumber());

        userService.clearCache();

        // Работаем через БД. Нашли User'а 1 по id в БД
        User userFromDB = userService.findUser(id1);

        // Проводим проверки
        assertEquals(id1, userFromDB.getId());
        assertEquals("Bill", userFromDB.getName());
        assertEquals(10, userFromDB.getAge());
        assertEquals("адрес1", userFromDB.getAddress().getStreet());
        assertEquals("111", userFromDB.getPhone().getNumber());
    }

    @Test
    void cacheAccessTimeTest() {
        // Сохраняем User'а 1 в БД
        long id1 = userService.saveUser(user1);

        // Оцениваем время доступа через кэш
        long startTime_Cache = System.currentTimeMillis();

        // Нашли User'а 1 по id в кэше
        userService.findUser(id1);

        System.out.println("Работаем через кэш");
        long accessTime_Cache = System.currentTimeMillis() - startTime_Cache;
        System.out.println("Время доступа: " + accessTime_Cache);

        userService.clearCache();

        // Оцениваем время доступа через БД
        long startTime_DB = System.currentTimeMillis();

        // Нашли User'а 1 по id в БД
        userService.findUser(id1);

        System.out.println("Работаем через БД");
        long accessTime_DB = System.currentTimeMillis() - startTime_DB;
        System.out.println("Время доступа: " + accessTime_DB);
    }

    @AfterEach
    void end() {
        System.out.println("\n" + "Конец теста");
    }
}
