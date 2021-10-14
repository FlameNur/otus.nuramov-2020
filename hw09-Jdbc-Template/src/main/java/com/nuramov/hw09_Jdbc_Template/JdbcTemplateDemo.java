package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Example_Classes.Account;
import com.nuramov.hw09_Jdbc_Template.Example_Classes.User;

import java.sql.*;

public class JdbcTemplateDemo {
    private static final String URL = "jdbc:h2:mem:";

    public static void main(String[] args) {
        JdbcConnection jdbcConnection = new JdbcConnection();
        Connection connection = jdbcConnection.getConnection(URL);

        JdbcTemplate jdbcTemplate = new JdbcTemplateImpl(connection);

        User user1 = new User();
        user1.setAge(10);
        user1.setName("Bill");

        User user2 = new User();
        user2.setAge(20);
        user2.setName("Tom");

        User user3 = new User();


        // Работаем с классом User
        System.out.println("Работаем с классом User:");
        jdbcTemplate.create(user1);

        user1.setName("Yo");

        jdbcTemplate.update(user1);

        User loadedUser1 = jdbcTemplate.load(1, User.class);
        System.out.println(loadedUser1);

        System.out.println(user2);

        System.out.println("\nВставляем строку:__________________________________");
        jdbcTemplate.insertRecord(user2);
        User loadedUser2 = jdbcTemplate.load(2, User.class);
        System.out.println(loadedUser2);

        jdbcTemplate.insertRecord(user1);

        user3.setName("Wow");
        user3.setAge(1);
        jdbcTemplate.insertRecord(user3);
        User loadedUser3 = jdbcTemplate.load(3, User.class);
        System.out.println(loadedUser3);

        // Работаем с классом Account
        System.out.println("\n-----------------------------------------");
        System.out.println("\nРаботаем с классом Account:");

        Account account1 = new Account();
        account1.setRest(100);
        account1.setType("AAA");
        account1.setTest(2.38);

        Account account2 = new Account();
        account2.setRest(222);
        account2.setType("BBB");
        account2.setTest(5.74);

        Account account3 = new Account();


        jdbcTemplate.create(account1);

        account1.setType("WWW");

        jdbcTemplate.update(account1);

        Account loadedAccount1 = jdbcTemplate.load(4, Account.class);
        System.out.println(loadedAccount1);

        System.out.println(account2);

        System.out.println("\nВставляем строку:__________________________________");
        jdbcTemplate.insertRecord(account2);
        Account loadedAccount2 = jdbcTemplate.load(5, Account.class);
        System.out.println(loadedAccount2);

        jdbcTemplate.insertRecord(account1);

        account3.setType("CCC");
        account3.setRest(444);
        jdbcTemplate.insertRecord(account3);
        Account loadedAccount3 = jdbcTemplate.load(6, Account.class);
        System.out.println(loadedAccount3);


        System.out.println("\n--------------------------------------------");
        System.out.println("Названия всех таблиц в базе даных:");
        jdbcTemplate.getDatabaseMetaData();

        jdbcConnection.close(connection);
    }
}
