package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Annotations.id;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class FieldsTypeAndValue {
    /**
     * Метод getFieldsSQLTypeAndValue позволяет получить Map
     * @param objectData
     * @return
     */
    <T> Map<String, String> getFieldsNameAndSQLType (T objectData) {
        //
        Map<String, String> fieldsNameAndSQLType = new HashMap<>();

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
     * Метод getFieldsNameAndValue позволяет получить навание поля (key) и его значание (value)
     * @param objectData
     * @param <T>
     * @return
     */
    <T> Map<String, Object> getFieldsNameAndValue(T objectData) {
        //
        Map<String, Object> fieldsNameAndValue = new HashMap<>();

        String fieldName;
        Object fieldValue;

        Class<?> cls = objectData.getClass();
        Field[] fields = cls.getDeclaredFields();
        for(Field field : fields) {
            try {
                field.setAccessible(true);
                // Название поля
                fieldName = field.getName();
                // Значение поля
                fieldValue = field.get(objectData);

                fieldsNameAndValue.put(fieldName, fieldValue);
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return  fieldsNameAndValue;
    }
}
