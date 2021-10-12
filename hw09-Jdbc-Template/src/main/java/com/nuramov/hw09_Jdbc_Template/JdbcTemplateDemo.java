package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Example_Classes.User;

import java.sql.*;

public class JdbcTemplateDemo {
    private static final String URL = "jdbc:h2:mem:";

    public static void main(String[] args) throws SQLException {
        JdbcTemplateDemo demo = new JdbcTemplateDemo();
        Connection connection = demo.getConnection();

        JdbcTemplate jdbcTemplate = new JdbcTemplateImpl<>(connection);

        User user1 = new User();
        user1.setAge(10);
        user1.setName("Bill");

        User user2 = new User();
        user2.setAge(20);
        user2.setName("Tom");

        User user3 = new User();


        // Работаем с классом User
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
        jdbcTemplate.insertRecord(user3);
        User loadedUser3 = jdbcTemplate.load(3, User.class);
        System.out.println(loadedUser3);

        System.out.println("\n--------------------------------------------");
        jdbcTemplate.getDatabaseMetaData();

        demo.close(connection);
    }

    /**
     * Метод getConnection() подключает к базе данных H2
     * @return Возвращает текущее соединение
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        connection.setAutoCommit(false);
        return connection;
    }

    /**
     * Метод close() закрывает соединение
     * @throws SQLException
     */
    public void close(Connection connection) throws SQLException {
        connection.close();
    }
}
