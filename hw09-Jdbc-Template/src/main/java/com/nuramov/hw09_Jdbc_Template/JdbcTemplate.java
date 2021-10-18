package com.nuramov.hw09_Jdbc_Template;

/**
 * Интерфейс JdbcTemplate задает основные методы работы с таблицей БД
 */
public interface JdbcTemplate {
    /**
     * Метод createTable позволяет содать таблицу с названием, соответсвующим названию класса
     * заданного экземпляра класса
     * @param objectData - экземпляр класса
     */
    <T> void createTable(T objectData);

    /**
     * Метод create позволяет добавить в таблицу строку с данными,
     * соответсвующими значениям полей заданного экземпляра класса
     * @param objectData - экземпляр класса
     */
    <T> void create(T objectData);

    /**
     * Метод update позволяет обновить информацию в таблице (строке) по заданному экземпляру класса
     * @param objectData - экземпляр класса
     */
    <T> void update(T objectData);

    /**
     * Метод load позволяет получить нужную строку из таблицы в БД по заданным id и классу
     * и сформировать экземпляр класса со значениями полей, заданных в таблице
     * @param id - требуемый id
     * @param clazz - заданный класс
     * @return - возвращает экземпляр класса
     */
    <T> T load(long id, Class<T> clazz);

    /**
     * Метод insertRecord позволяет добавить новую запись в таблицу
     * @param objectData - экземпляр класса
     */
    <T> void insertRecord(T objectData);
}
