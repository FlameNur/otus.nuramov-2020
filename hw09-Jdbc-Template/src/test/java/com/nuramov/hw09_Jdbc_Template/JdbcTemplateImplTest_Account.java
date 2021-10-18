package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Example_Classes.Account;
import org.junit.jupiter.api.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcTemplateImplTest_Account {
    private static final String URL = "jdbc:h2:mem:";
    private static JdbcTemplateImpl jdbcTemplate;
    private static Account account;

    @BeforeAll
    static void setUpUser() {
        account = new Account();
        account.setType("AAA");
        account.setRest(11);
        account.setTest(4.73);
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
        jdbcTemplate.create(account);
        account.setType("BBB");
        jdbcTemplate.update(account);
        Account loadedAccount = jdbcTemplate.load(1, Account.class);

        assertEquals(1, loadedAccount.getNo());
        assertNotEquals("AAA", loadedAccount.getType());
        assertEquals("BBB", loadedAccount.getType());
        assertEquals(11, loadedAccount.getRest());
        assertEquals(4.73, loadedAccount.getTest());

        System.out.println("account: " + account);
        System.out.println("loadedAccount: " + loadedAccount);
    }

    @AfterEach
    void TestEnd() {
        System.out.println("Тест успешно завершен");
    }
}
