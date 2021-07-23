package com.nuramov.hw09_Jdbc_Template;

public interface JdbcTemplate {

    // Этот метод должен быть
    <T> void create(T objectData);

    // Этот метод должен быть
    <T> void update(T objectData);

    // Этот метод должен быть
    <T> T load(long id, Class<T> clazz);
}
