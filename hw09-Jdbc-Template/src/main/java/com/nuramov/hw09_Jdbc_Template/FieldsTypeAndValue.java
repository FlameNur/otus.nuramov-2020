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
     *
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

    /**
     * Метод getAnnotatedID определяет наличие поля с аннотацией @id у полученного экземпляра класса
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
            if (field.isAnnotationPresent(id.class)) {
                idState = true;
                break;
            }
        }
        return idState;
    }

    /**
     * Метод getIdValue определяет значение поля с аннотацией @id у полученного экземпляра класса
     * @param objectData - экземпляр класса
     * @return - возвращает значение поля с аннотацией @id
     */
    <T> Object getIdValue(T objectData) {
        Object id = null;

        // Определяем поля класса
        Class<?> clazz = objectData.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // Проверяем наличие аннотации "id" над одним из полей класса
            if(field.isAnnotationPresent(id.class)) {
                try {
                    field.setAccessible(true);
                    // Получаем значение id
                    id = field.get(objectData);
                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return id;
    }

    /**
     * Метод getIdName определяет наименование/название поля с аннотацией @id у полученного экземпляра класса
     * @param objectData - экземпляр класса
     * @return - возвращает наименование/название поля с аннотацией @id
     */
    <T> String getIdName(T objectData) {
        String  name = null;

        // Определяем поля класса
        Class<?> clazz = objectData.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // Проверяем наличие аннотации "id" над одним из полей класса
            if(field.isAnnotationPresent(id.class)) {
                field.setAccessible(true);
                // Получаем имя поля с аннотацией id
                name = field.getName();
                field.setAccessible(false);
            }
        }
        return name;
    }
}
