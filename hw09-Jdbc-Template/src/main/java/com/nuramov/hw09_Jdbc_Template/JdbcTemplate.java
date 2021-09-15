package com.nuramov.hw09_Jdbc_Template;

import java.sql.SQLException;

public interface JdbcTemplate {

    /**
     *
     * @param objectData
     * @param <T>
     */
    <T> void create(T objectData);

    /**
     *
     * @param objectData
     * @param <T>
     */
    <T> void update(T objectData);

    /**
     *
     * @param id
     * @return
     */
    <T> T load(long id, Class<T> clazz);

    /**
     *
     * @param objectData
     * @param <T>
     */
    <T> void insertRecord(T objectData);

    /**
     * Получаем названия всех таблиц в базе даных
     */
    void getDatabaseMetaData();
}
