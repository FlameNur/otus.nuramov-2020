package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Example_Classes.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class JdbcTemplateImplTest_User {
    private static final String URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static JdbcTemplateImpl jdbcTemplate;
    private static User user1;
    private static User user2;


    @BeforeAll
    static void createUser() {
        user1 = new User();
        user1.setName("Bill");
        user1.setAge(10);

        user2 = new User();
        user2.setName("Sally");
        user2.setAge(55);
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
        jdbcTemplate.createTable(user1);
        jdbcTemplate.create(user1);
        jdbcTemplate.create(user2);

        user1.setName("Yo");
        jdbcTemplate.update(user1);
        User loadedUser1 = jdbcTemplate.load(1, User.class);
        User loadedUser2 = jdbcTemplate.load(2, User.class);

        assertEquals(1, loadedUser1.getID());
        assertNotEquals("Bill", loadedUser1.getName());
        assertEquals("Yo", loadedUser1.getName());
        assertEquals(10, loadedUser1.getAge());

        System.out.println("user1: " + user1);
        System.out.println("loadedUser1: " + loadedUser1);
        System.out.println("user2: " + user2);
        System.out.println("loadedUser2: " + loadedUser2);

    }

    @AfterEach
    void TestEnd() {
        System.out.println("Тест успешно завершен");
    }
}