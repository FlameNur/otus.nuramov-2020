package com.nuramov.hw09_Jdbc_Template;

import java.sql.*;

public class JdbcTemplateDemo {
    private static final String URL = "jdbc:h2:mem:";

    public static void main(String[] args) throws SQLException {
        JdbcTemplateDemo demo = new JdbcTemplateDemo();
        Connection connection = demo.getConnection();
        //demo.createTable(connection); Перенес в JdbcTemplateImpl

        JdbcTemplate jdbcTemplate = new JdbcTemplateImpl<>(connection);

        User user1 = new User();
        user1.setAge(10);
        user1.setName("Bill");

        User user2 = new User();
        user2.setAge(20);
        user2.setName("Tom");


        // Работаем с классом User
        jdbcTemplate.create(user1);

        user1.setName("Yo");

        jdbcTemplate.update(user1);

        User loadedUser = jdbcTemplate.load(1); // User loadedUser = jdbcTemplate.load(1, User.class);
        System.out.println(loadedUser);


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
