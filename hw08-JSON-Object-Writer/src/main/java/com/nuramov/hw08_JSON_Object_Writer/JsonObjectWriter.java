package com.nuramov.hw08_JSON_Object_Writer;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * class JsonObjectWriter представляет собой json object writer (object to JSON string) аналогичный gson
 */

public class JsonObjectWriter {

    public String toJson(Object obj) throws IllegalAccessException {
        // Начинаем собирать json объект
        StringBuilder stringBuilder = new StringBuilder("{");
        // Определяем класс пришедшего obj
        Class<?> cls = obj.getClass();
        // Определяем поля класса
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            // Исключаем static поля класса, т.к. их не должно быть в json объекте
            int modifier = field.getModifiers();
            if(!Modifier.isStatic(modifier)) {
                // Определяем название поля
                String fieldName = field.getName();
                stringBuilder.append("\"").append(fieldName).append("\":");

                // Определяем значение поля
                field.setAccessible(true);
                Object valueOfField = field.get(obj);

                // Тип поля - Array
                String arrayToJson = arrayToJson(field, valueOfField);
                stringBuilder.append(arrayToJson);

                // Тип поля - String, Character
                String stringCharacterTypesToJson = stringCharacterTypesToJson(valueOfField);
                stringBuilder.append(stringCharacterTypesToJson);

                // Тип поля - Byte, Integer, Double, Float, Short, Long, Boolean
                String simpleTypesToJson = simpleTypesToJson(valueOfField);
                stringBuilder.append(simpleTypesToJson);

                // Тип поля - map
                String mapTypeToJson = mapTypeToJson(valueOfField);
                stringBuilder.append(mapTypeToJson);

                // Тип поля - Collection
                String collectionTypeToJson = collectionTypeToJson(valueOfField);
                stringBuilder.append(collectionTypeToJson);

                stringBuilder.append(",");
            }
        }
        stringBuilder.append("}");
        // Удаляем последнюю запятую
        stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        return stringBuilder.toString();
    }

    /**
     *
     * @param o
     * @return
     */
    private String simpleTypesToJson(Object o) {
        Object valueOfField = o;
        String result = "";

        if (valueOfField instanceof Byte || valueOfField instanceof Integer || valueOfField instanceof Double ||
                valueOfField instanceof Float || valueOfField instanceof Short ||
                valueOfField instanceof Long || valueOfField instanceof Boolean) {
            result = valueOfField.toString();
        }
        return result;
    }

    /**
     *
     * @param o
     * @return
     */
    private String mapTypeToJson(Object o) {
        Object valueOfField = o;
        StringBuilder stringBuilder = new StringBuilder("");

        if (valueOfField instanceof Map) {
            Iterator<Map.Entry<Object, Object>> items = ((Map) valueOfField).entrySet().iterator();
            stringBuilder.append("{");
            while (items.hasNext()) {
                Map.Entry<Object, Object> entry = items.next();
                stringBuilder.append("\"").append(entry.getKey().toString()).append("\"").append(":");
                stringBuilder.append("\"").append(entry.getValue().toString()).append("\"").append(",");
            }
            stringBuilder.append("}");
            // Удаляем последнюю запятую
            stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        }

        String result = stringBuilder.toString();
        return result;
    }

    /**
     *
     * @param o
     * @return
     */
    private String collectionTypeToJson(Object o) {
        Object valueOfField = o;
        StringBuilder stringBuilder = new StringBuilder("");

        if (valueOfField instanceof Collection) {
            Iterator items = ((Collection) valueOfField).iterator();
            stringBuilder.append("[");
            while (items != null && items.hasNext()) {
                Object item = items.next();
                stringBuilder.append("\"").append(item.toString()).append("\"");
                stringBuilder.append(",");
            }
            stringBuilder.append("]");
            // Удаляем последнюю запятую
            stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        }

        String result = stringBuilder.toString();
        return result;
    }

    /**
     *
     * @param o
     * @return
     */
    private String stringCharacterTypesToJson(Object o) {
        Object valueOfField = o;
        StringBuilder stringBuilder = new StringBuilder("");

        if(valueOfField instanceof String || valueOfField instanceof Character) {
            stringBuilder.append("\"").append(valueOfField.toString()).append("\"");
        }

        String result = stringBuilder.toString();
        return result;
    }

    /**
     *
     * @param f
     * @param o
     * @return
     */
    private String arrayToJson(Field f, Object o) {
        Field field = f;
        Object valueOfField = o;
        StringBuilder stringBuilder = new StringBuilder("");

        if (field.getType().isArray()) {
            int length = Array.getLength(valueOfField);
            stringBuilder.append("[");
            for (int i = 0; i < length; i++) {
                if (i != 0) stringBuilder.append(",");
                stringBuilder.append(Array.get(valueOfField, i));
            }
            stringBuilder.append("]");
        }

        String result = stringBuilder.toString();
        return result;
    }
}
