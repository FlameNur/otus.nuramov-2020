package com.nuramov.hw09_Jdbc_Template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTemplate <T> {
    private static final String URL = "jdbc:h2:mem:";
    private final Connection connection;

    public JdbcTemplate() throws SQLException {
        this.connection = DriverManager.getConnection(URL);
        this.connection.setAutoCommit(false);
    }

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
     * Метод close() закрывает соединение
     * @throws SQLException
     */
    public void close() throws SQLException {
        this.connection.close();
    }




    /*// Пример проверки соединнения с БД
    private Connection getNewConnection() throws SQLException {
        String url = "jdbc:h2:mem:";
        return DriverManager.getConnection(url);
    }*/
}
