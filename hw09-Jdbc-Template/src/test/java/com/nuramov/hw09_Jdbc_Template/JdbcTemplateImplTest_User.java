package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Example_Classes.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class JdbcTemplateImplTest_User {
    private static final String URL = "jdbc:h2:mem:";
    private static Connection connection;
    private static JdbcTemplateImpl jdbcTemplate;
    private static JdbcConnection jdbcConnection;
    private static User user1;
    private static User user2;

    @BeforeAll
    static void setUpUser() {
        user1 = new User();
        user1.setName("Bill");
        user1.setAge(10);

        user2 = new User();
        user2.setName("Tom");
        user2.setAge(20);
    }

    @BeforeEach
    void getConnection() {
        jdbcConnection = new JdbcConnection();
        connection = jdbcConnection.getConnection(URL);
        jdbcTemplate = new JdbcTemplateImpl(connection);
    }

    @BeforeEach
    void TestStart() {
        System.out.println("\n" + "Начало Теста:");
    }

    @Test
    void createAndLoadTest() {
        System.out.println("\nTest: createAndLoadTest:");
        jdbcTemplate.create(user1);
        User loadedUser = jdbcTemplate.load(1, User.class);

        assertEquals(1, loadedUser.getID());
        assertEquals("Bill", loadedUser.getName());
        assertEquals(10, loadedUser.getAge());

        System.out.println("user1: " + user1);
        System.out.println("loadedUser: " + loadedUser);
    }

    @Test
    void updateAndLoadTest() {
        System.out.println("\nTest: updateAndLoadTest:");
        jdbcTemplate.create(user1);
        user1.setName("Yo");
        jdbcTemplate.update(user1);
        User loadedUser = jdbcTemplate.load(1, User.class);

        assertEquals(1, loadedUser.getID());
        assertNotEquals("Bill", loadedUser.getName());
        assertEquals("Yo", loadedUser.getName());
        assertEquals(10, loadedUser.getAge());

        System.out.println("user1: " + user1);
        System.out.println("loadedUser: " + loadedUser);
    }

    @Test
    void insertRecordTest() {
        System.out.println("\nTest: insertRecordTest:");
        jdbcTemplate.create(user1);
        jdbcTemplate.insertRecord(user2);

        User loadedUser1 = jdbcTemplate.load(1, User.class);
        User loadedUser2 = jdbcTemplate.load(2, User.class);

        assertEquals(1, loadedUser1.getID());
        assertEquals("Bill", loadedUser1.getName());
        assertEquals(10, loadedUser1.getAge());

        assertEquals(2, loadedUser2.getID());
        assertEquals("Tom", loadedUser2.getName());
        assertEquals(20, loadedUser2.getAge());

        System.out.println("user1: " + user1);
        System.out.println("loadedUser1: " + loadedUser1);
        System.out.println("\nuser2: " + user2);
        System.out.println("loadedUser2: " + loadedUser2);
    }

    @AfterEach
    void TestEnd() {
        System.out.println("Тест успешно завершен");
    }

    @AfterEach
    void closeConnection() {
        jdbcConnection.close(connection);
    }
}