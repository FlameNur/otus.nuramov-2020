package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Annotations.id;

import java.lang.reflect.Field;
import java.sql.Connection;

public class FieldsSQLTypeAndValue {


    public FieldsSQLTypeAndValue() {
    }

    /**
     *
     * @param connection
     * @param objectData
     * @param <T>
     */
    <T> void mymethod (Connection connection, T objectData) {
        Class<?> cls = objectData.getClass();

        Field[] fields = cls.getDeclaredFields();
    }

    /**
     *
     * @param objectData
     * @param <T>
     */
    <T> void getAnnotatedID (T objectData) {
        // Определяем поля класса
        Class<?> clazz = objectData.getClass();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // Проверяем наличие аннотации "id" над одним из полей класса
            if(field.isAnnotationPresent(id.class)) {
                //Что-то делаем
            }
        }
    }
}
