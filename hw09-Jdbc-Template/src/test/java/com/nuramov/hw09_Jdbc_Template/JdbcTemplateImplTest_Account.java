package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Example_Classes.Account;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcTemplateImplTest_Account {
    private static final String URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static JdbcTemplateImpl jdbcTemplate;
    private static Account account1;
    private static Account account2;

    @BeforeAll
    static void setUpUser() {
        account1 = new Account();
        account1.setType("AAA");
        account1.setRest(11);
        account1.setTest(4.73);

        account2 = new Account();
        account2.setType("FFF");
        account2.setRest(222);
        account2.setTest(7.25);
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
        jdbcTemplate.createTable(account1);
        jdbcTemplate.create(account1);
        jdbcTemplate.create(account2);

        account1.setType("BBB");
        jdbcTemplate.update(account1);
        Account loadedAccount1 = jdbcTemplate.load(1, Account.class);
        Account loadedAccount2 = jdbcTemplate.load(2, Account.class);

        assertEquals(1, loadedAccount1.getNo());
        assertNotEquals("AAA", loadedAccount1.getType());
        assertEquals("BBB", loadedAccount1.getType());
        assertEquals(11, loadedAccount1.getRest());
        assertEquals(4.73, loadedAccount1.getTest());

        System.out.println("account1: " + account1);
        System.out.println("loadedAccount1: " + loadedAccount1);
        System.out.println("account2: " + account2);
        System.out.println("loadedAccount2: " + loadedAccount2);
    }

    @AfterEach
    void TestEnd() {
        System.out.println("Тест успешно завершен");
    }
}
