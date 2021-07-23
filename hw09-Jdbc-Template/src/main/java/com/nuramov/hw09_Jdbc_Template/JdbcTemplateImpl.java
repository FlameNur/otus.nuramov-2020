package com.nuramov.hw09_Jdbc_Template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcTemplateImpl<T> implements JdbcTemplate {
    private final Connection connection;

    public JdbcTemplateImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T> void create(T objectData) {
        // Что-то
    }

    @Override
    public <T> void update(T objectData) {
        // Что-то
    }

    @Override
    public <T> T load(long id, Class<T> clazz) {
        // Что-то
        return null;
    }
}
