package com.nuramov.hw09_Jdbc_Template;

import java.sql.SQLException;

public class main {
    public static void main(String[] args) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        User user1 = new User("Tom", 32);
        User user2 = new User("Bill", 64);
        User user3 = new User("Rex", 18);

        Account account1 = new Account("AAA", 4);
        Account account2 = new Account("BBB", 102);
        Account account3 = new Account("CCC", 73);

        System.out.println("Проверка определния id/no:");
        System.out.println(user2.toString());
        System.out.println(account3.toString());

        // Работаем с классом User
        //jdbcTemplate.create();
        //jdbcTemplate.update();
        jdbcTemplate.load(2, User.class);


        // Работаем с классом Account для дополнительной проверки
        //jdbcTemplate.create();
        //jdbcTemplate.update();
        jdbcTemplate.load(2, Account.class);


        jdbcTemplate.close();
    }
}
