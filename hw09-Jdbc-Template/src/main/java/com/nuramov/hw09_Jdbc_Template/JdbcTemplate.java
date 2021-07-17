package com.nuramov.hw09_Jdbc_Template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTemplate <T> {
    // Просто пример проверки соединнения с БД
    private Connection getNewConnection() throws SQLException {
        String url = "jdbc:h2:mem:test";
        String user = "sa";
        String passwd = "sa";
        return DriverManager.getConnection(url, user, passwd);
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
}
