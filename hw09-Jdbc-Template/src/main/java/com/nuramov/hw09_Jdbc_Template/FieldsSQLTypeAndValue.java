package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Annotations.id;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class FieldsSQLTypeAndValue {


    public FieldsSQLTypeAndValue() {
    }

    /**
     *
     * @param connection
     * @param objectData
     * @param <T>
     */
    <T> Map<String, Object> myMethod (Connection connection, T objectData) {
        Map<String, Object> fieldsSQLNameAndValue = new HashMap<>();

        String fieldId = "";
        long id = 0;

        String fieldName;
        Object fieldValue;

        Class<?> cls = objectData.getClass();
        Field[] fields = cls.getDeclaredFields();
        for(Field field : fields) {
            try {
                field.setAccessible(true);
                // Пока использую SimpleName, потом возможно придется переделать
                fieldName = field.getType().getSimpleName();
                // Значение поля
                fieldValue = field.get(objectData);
                // Добавил в Map тип поля и его значение
                // Пока добавил не SQL типы
                fieldsSQLNameAndValue.put(fieldName, fieldValue);


                // Просто как пример висит
                if (fieldValue instanceof Long) {
                    fieldId = field.getName();
                    id = (Long) fieldValue;
                }


                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fieldsSQLNameAndValue;
    }

    /**
     * Метод getAnnotatedID определяет наличие у полученного экземпляра класса поля с аннотацией @id
     * @param objectData - экземпляр класса
     * @return - возвращает true, если у поля имеется аннотация @id, и false, если нет
     */
    <T> boolean getAnnotatedID (T objectData) {
        boolean idState = false;
        // Определяем поля класса
        Class<?> clazz = objectData.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // Проверяем наличие аннотации "id" над одним из полей класса
            if(field.isAnnotationPresent(id.class)) {
                idState = true;
            }
        }
        return idState;
    }

    /**
     *
     * @param objectData
     * @param <T>
     */
    <T> void getIdValue(T objectData) {

    }
}
