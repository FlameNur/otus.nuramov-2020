package com.nuramov.hw09_Jdbc_Template;

import java.sql.SQLException;

public interface JdbcTemplate {

    /**
     *
     * @param objectData
     * @param <T>
     */
    <T> void create(User objectData); // (T objectData)

    /**
     *
     * @param objectData
     * @param <T>
     */
    <T> void update(User objectData) throws SQLException; // (T objectData)

    /**
     *
     * @param id
     * @return
     */
    User load(long id); // <T> T load(long id, Class<T> clazz);
}
