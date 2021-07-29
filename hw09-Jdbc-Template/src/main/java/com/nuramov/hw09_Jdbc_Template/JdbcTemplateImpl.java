package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Annotations.id;

import java.lang.reflect.Field;
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

        // Определяем поля класса
        Class<?> cls = objectData.getClass();
        String className = cls.getName();

        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            if(field.isAnnotationPresent(id.class)) {
                System.out.println("Круто!");
                makeTable(fields, className);
            }

            Class<?> fld = field.getType();
            System.out.println("Class name : " + field.getName());
            System.out.println("Class type : " + fld.getName());
        }


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

    private void makeTable(Field[] fields, String className) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + className + " VALUES(?, ?, ?, ?)");
            for (Field field : fields) {
                //preparedStatement.setLong(1, );
            }


        } catch (SQLException e) {
           e.printStackTrace();
        }

    }
}
