package com.nuramov.hw09_Jdbc_Template;

public interface JdbcTemplate {

    // Этот метод должен быть
    <T> void create(User objectData); // (T objectData)

    // Этот метод должен быть
    <T> void update(User objectData); // (T objectData)

    // Этот метод должен быть
    User load(long id); // <T> T load(long id, Class<T> clazz);
}
