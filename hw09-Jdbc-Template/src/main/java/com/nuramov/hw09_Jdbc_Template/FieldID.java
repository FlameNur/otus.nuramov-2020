package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Annotations.id;

import java.lang.reflect.Field;

/**
 * class FieldID позволяет работать полем с аннотацией @id
 */
public class FieldID {
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

    <T> void setIdValue(T objectData, long id) {
        // Определяем поля класса
        Class<?> clazz = objectData.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // Проверяем наличие аннотации "id" над одним из полей класса
            if(field.isAnnotationPresent(id.class)) {
                field.setAccessible(true);

                try {
                    // Устанавливаем новое значение id
                    field.setLong(objectData, id);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                field.setAccessible(false);
            }
        }
    }
}
