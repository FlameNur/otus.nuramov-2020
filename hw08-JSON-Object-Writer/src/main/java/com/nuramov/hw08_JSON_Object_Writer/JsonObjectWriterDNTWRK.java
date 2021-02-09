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

public class JsonObjectWriterDNTWRK {

    /**
     * Метод toJson из заданного объекта/экземпляра класса формирует json объект для сериализации
     * @param obj - экземпляр класса для сериализации
     * @return    - возвращает json объект формата String
     */
    public String toJson(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Cannot serialize null value");
        }

        // Начинаем собирать json объект
        StringBuilder stringBuilder = new StringBuilder("{");
        // Определяем класс пришедшего obj
        Class<?> cls = obj.getClass();
        // Определяем поля класса
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            // Исключаем static поля класса
            int modifier = field.getModifiers();
            if(Modifier.isStatic(modifier)) continue;

            // Определяем название поля
            String fieldName = field.getName();
            stringBuilder.append("\"").append(fieldName).append("\":");

            // Определяем значение поля
            Object valueOfField = initValueOfField(field, obj);

            // Тип поля - Array
            stringBuilder.append(arrayToJson(field, valueOfField));

            // Тип поля - String, Character
            stringBuilder.append(stringCharacterTypesToJson(valueOfField));

            // Тип поля - Byte, Integer, Double, Float, Short, Long, Boolean
            stringBuilder.append(simpleTypesToJson(valueOfField));

            // Тип поля - Map
            stringBuilder.append(mapTypeToJson(valueOfField));

            // Тип поля - Collection
            stringBuilder.append(collectionTypeToJson(valueOfField));

            stringBuilder.append(",");
        }
        stringBuilder.append("}");
        // Удаляем последнюю запятую
        stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        return stringBuilder.toString();
    }

    /**
     * Метод simpleTypesToJson используется для работы с полями следубщих типов:
     * Byte, Integer, Double, Float, Short, Long, Boolean.
     * @param o - значение поля экземпляра класса для сериализации. Если поле проходит условия проверок,
     *            значение поля прописывается в строку, т.е. становится частью объекта json.
     * @return  - возвращает строку со значением поля, т.е. часть объекта json,
     *            или пустую строку, если условие проверки не выполняется.
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
     * Метод mapTypeToJson используется для работы с полями типа Map.
     * @param o - значение поля объекта для сериализации. Если поле проходит условие проверки,
     *            значение поля прописывается в строку, т.е. становится частью объекта json.
     * @return  - возвращает строку со значением поля, т.е. часть объекта json,
     *            или пустую строку, если условие проверки не выполняется.
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
     * Метод collectionTypeToJson используется для работы с полями типа Collection.
     * @param o - значение поля объекта для сериализации. Если поле проходит условие проверки,
     *            значение поля прописывается в строку, т.е. становится частью объекта json.
     * @return  - возвращает строку со значением поля, т.е. часть объекта json,
     *            или пустую строку, если условие проверки не выполняется.
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
     * Метод stringCharacterTypesToJson используется для работы с полями типов String и Character.
     * @param o - значение поля объекта для сериализации. Если поле проходит условия проверок,
     *            значение поля прописывается в строку, т.е. становится частью объекта json.
     * @return  - возвращает строку со значением поля, т.е. часть объекта json,
     *            или пустую строку, если условие проверки не выполняется.
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
     * Метод arrayToJson используется для работы с полями типа Array.
     * @param f - поле объекта
     * @param o - значение поля объекта для сериализации. Если поле проходит условие проверки,
     *            значение поля прописывается в строку, т.е. становится частью объекта json.
     * @return  - возвращает строку со значением поля, т.е. часть объекта json,
     *            или пустую строку, если условие проверки не выполняется.
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

    /**
     * Метод initValueOfField используется для определния значения заданного поля
     * @param field - поле объекта
     * @param obj   - объект для сериализации/экземпляр класса
     * @return      - возвращает значение заданного поля
     */
    private Object initValueOfField (Field field, Object obj) {
        Object valueOfField = null;
        field.setAccessible(true);
        try {
            valueOfField = field.get(obj);
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException");
        }
        field.setAccessible(false);
        return valueOfField;
    }
}
