package com.nuramov.hw09_Jdbc_Template.Fields;

import com.nuramov.hw09_Jdbc_Template.Annotations.id;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * class FieldsTypeAndValue позволяет работать со всеми полями полученного экземпляра класса
 * кроме поля с аннотацией @id
 */
public class FieldsTypeAndValue {
    /**
     * Метод getFieldsSQLTypeAndValue позволяет получить Map с именами полей (key) и SQL типами этих полей (value)
     * @param objectData - экземпляр класса
     * @return - возвращает Map fieldsNameAndSQLType с именами полей (key) и SQL типами этих полей (value)
     */
    public <T> Map<String, String> getFieldsNameAndSQLType (T objectData) {
        // Map с именами полей (key) и SQL типами этих полей (value)
        Map<String, String> fieldsNameAndSQLType = new LinkedHashMap<>();

        String fieldSimpleName;
        String fieldName;
        Object fieldValue;

        Class<?> cls = objectData.getClass();
        Field[] fields = cls.getDeclaredFields();
        for(Field field : fields) {
            try {
                field.setAccessible(true);
                // Пока использую SimpleName, потом возможно придется переделать
                fieldSimpleName = field.getType().getSimpleName();
                // Название поля
                fieldName = field.getName();
                // Значение поля
                fieldValue = field.get(objectData);

                // Включаем в map fieldsNameAndSQLType поля без аннотации @id
                if (!field.isAnnotationPresent(id.class)) {
                    if(fieldSimpleName.startsWith("String")) {
                        fieldsNameAndSQLType.put(fieldName, "VARCHAR");
                    }

                    if (fieldValue instanceof Character) {
                        fieldsNameAndSQLType.put(fieldName, "CHAR");
                    }

                    if (fieldValue instanceof Long) {
                        fieldsNameAndSQLType.put(fieldName, "BIGINT");
                    }

                    if(fieldValue instanceof Integer) {
                        fieldsNameAndSQLType.put(fieldName, "INT");
                    }

                    if(fieldValue instanceof Boolean) {
                        fieldsNameAndSQLType.put(fieldName, "BOOLEAN");
                    }

                    if(fieldValue instanceof Short) {
                        fieldsNameAndSQLType.put(fieldName, "SMALLINT");
                    }

                    if(fieldValue instanceof Byte) {
                        fieldsNameAndSQLType.put(fieldName, "TINYINT");
                    }

                    if(fieldValue instanceof Float) {
                        fieldsNameAndSQLType.put(fieldName, "REAL");
                    }

                    if(fieldValue instanceof Double) {
                        fieldsNameAndSQLType.put(fieldName, "DOUBLE");
                    }
                }
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fieldsNameAndSQLType;
    }

    /**
     * Метод getFieldsNameAndValue позволяет получить Map c именами полей (key) и его значаниями (value)
     * @param objectData - экземпляр класса
     * @return - возвращает Map fieldsNameAndValue c именами полей (key) и его значаниями (value)
     */
    public <T> Map<String, Object> getFieldsNameAndValue(T objectData) {
        // Map c именами полей (key) и его значаниями (value)
        Map<String, Object> fieldsNameAndValue = new LinkedHashMap<>();
        // Название поля
        String fieldName;
        // Значение поля
        Object fieldValue;

        Class<?> cls = objectData.getClass();
        Field[] fields = cls.getDeclaredFields();
        for(Field field : fields) {
            try {
                field.setAccessible(true);
                fieldName = field.getName();
                fieldValue = field.get(objectData);

                // Включаем в map fieldsNameAndValue поля без аннотации @id
                if (!field.isAnnotationPresent(id.class)) {
                    fieldsNameAndValue.put(fieldName, fieldValue);
                }
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return  fieldsNameAndValue;
    }
}
