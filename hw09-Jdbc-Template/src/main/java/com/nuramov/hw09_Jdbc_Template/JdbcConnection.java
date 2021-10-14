package com.nuramov.hw09_Jdbc_Template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс JdbcConnection позволяет получить текущее соединнения к БД и закрывать указанное соединение
 */
public class JdbcConnection {
    /**
     * Метод getConnection() подключает к базе данных H2
     * @param URL - унифицированный указатель ресурса (адрес)
     * @return - возвращает текущее соединение
     */
    Connection getConnection(String URL) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * Метод close() закрывает соединение
     * @param connection - текущее соединение
     */
    void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
