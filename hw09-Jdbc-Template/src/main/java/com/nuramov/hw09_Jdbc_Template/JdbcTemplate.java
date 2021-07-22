package com.nuramov.hw09_Jdbc_Template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcTemplate <T> {
    private static final String URL = "jdbc:h2:mem:";
    private Connection connection;

    // Этот метод должен быть
    void create(T objectData) {
        // Что-то
    }

    // Этот метод должен быть
    void update(T objectData) {
        // Что-то
    }

    // Этот метод должен быть
    <T> T load(long id, Class<T> clazz) {
        // Что-то
        return null;
    }

    /**
     * Метод getConnection() подключает к базе данных H2
     * @throws SQLException
     */
    public void getConnection() throws SQLException {
        connection = DriverManager.getConnection(URL);
        connection.setAutoCommit(false);
    }

    // Можно сделать так и вернуть connection
    /*private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        connection.setAutoCommit(false);
        return connection;
    }*/


    /**
     * Метод createTable() создает таблицу "User" в базе данных H2 c полями:
     * id bigint(20) NOT NULL auto_increment
     * name varchar(255)
     * age int(3)
     *
     * @throws SQLException
     */
    public void createTable() throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement("create table User" +
                "(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
    }

    /**
     * Метод close() закрывает соединение
     * @throws SQLException
     */
    public void close() throws SQLException {
        this.connection.close();
    }
}
