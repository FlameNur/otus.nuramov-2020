package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Example_Classes.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class JdbcTemplateImplTest_User {
    private static final String URL = "jdbc:h2:mem:";
    private static JdbcTemplateImpl jdbcTemplate;
    private static User user;

    @BeforeAll
    static void setUpUser() {
        user = new User();
        user.setName("Bill");
        user.setAge(10);
    }

    @BeforeEach
    void getConnection() {
        jdbcTemplate = new JdbcTemplateImpl(URL);
    }

    @BeforeEach
    void TestStart() {
        System.out.println("\n" + "Начало Теста:");
    }

    @Test
    void Test() {
        //jdbcTemplate.createTable(user);
        jdbcTemplate.create(user);
        user.setName("Yo");
        jdbcTemplate.update(user);
        User loadedUser = jdbcTemplate.load(1, User.class);

        assertEquals(1, loadedUser.getID());
        assertNotEquals("Bill", loadedUser.getName());
        assertEquals("Yo", loadedUser.getName());
        assertEquals(10, loadedUser.getAge());

        System.out.println("user: " + user);
        System.out.println("loadedUser: " + loadedUser);
    }

    @AfterEach
    void TestEnd() {
        System.out.println("Тест успешно завершен");
    }
}