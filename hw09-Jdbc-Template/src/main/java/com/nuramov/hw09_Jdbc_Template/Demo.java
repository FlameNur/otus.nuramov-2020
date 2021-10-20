package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Example_Classes.User;

public class Demo {
    private static final String URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

    public static void main(String[] args) {
        User user = new User();
        user.setName("Bill");
        user.setAge(10);

        User user2 = new User();
        user2.setName("Sally");
        user2.setAge(50);

        JdbcTemplateImpl jdbcTemplate = new JdbcTemplateImpl(URL);
        jdbcTemplate.createTable(user);

        System.out.println("Добавляем строку: ");
        jdbcTemplate.create(user);
        jdbcTemplate.create(user2);

        user.setName("Yo");
        System.out.println(user);

        jdbcTemplate.update(user);
        User loadedUser = jdbcTemplate.load(1, User.class);
        System.out.println(loadedUser);

        User loadedUser2 = jdbcTemplate.load(2, User.class);
        System.out.println(loadedUser2);

        User user3 = new User();
        jdbcTemplate.createTable(user3);
    }
}
