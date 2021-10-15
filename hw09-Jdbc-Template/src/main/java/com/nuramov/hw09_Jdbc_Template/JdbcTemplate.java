package com.nuramov.hw09_Jdbc_Template;

/**
 * Интерфейс JdbcTemplate задает основные методы работы с таблицей БД
 */
public interface JdbcTemplate {

    /**
     * Метод create позволяет создать таблицу с данными,
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
     * @param clazz - требуемый класс
     * @return - возвращает экземпляр класса
     */
    <T> T load(long id, Class<T> clazz);

    /**
     * Метод insertRecord позволяет добавить новую запись в таблицу
     * @param objectData - экземпляр класса
     */
    <T> void insertRecord(T objectData);

    /**
     * Метод getDatabaseMetaData позволяет получить названия всех таблиц в базе даных
     */
    void getDatabaseMetaData();
}
