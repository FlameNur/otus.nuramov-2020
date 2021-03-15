package com.nuramov.hw08_JSON_Object_Writer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * class JsonObjectWriter представляет собой json object writer (object to JSON string) аналогичный Gson
 */

public class JsonObjectWriter {

    /**
     * Метод toJson из заданного объекта/экземпляра класса формирует json объект (сериализует)
     * @param obj - объект для сериализации
     * @return    - возвращает json объект формата String
     */
    public String toJson(Object obj) {
        if(obj == null){
            return null;
        }

        if(obj instanceof String || obj instanceof Character){
            return '\"' + obj.toString() + '\"';
        }
        return toJsonObject(obj).toString();
    }

    /**
     * Метод toJsonObject определяет тип объекта
     * @param obj - объект для сериализации
     * @return    - возвращает готовый для сериализации объект
     */
    private Object toJsonObject (Object obj) {
        if(obj == null){
            return null;
        }

        if(obj instanceof String || obj instanceof Character ||
                obj instanceof Number || obj instanceof Boolean){
            return obj;
        }

        if (obj.getClass().isArray()) {
            return arrayToJson(obj);
        }

        if (obj instanceof Collection) {
            return collectionTypeToJson((Collection) obj);
        }

        if(obj instanceof Map) {
            return mapTypeToJson((Map) obj);
        }
        return otherClassToJson(obj);
    }


    /**
     * Метод mapTypeToJson используется для работы объектами типа Map.
     * @param obj - объект для сериализации
     * @return    - возвращает готовый для сериализации объект
     */
    private Object mapTypeToJson(Map obj) {
        JSONObject jsonObject = new JSONObject();

        for (Object key : obj.keySet()) {
            Object value = obj.get(key);
            if (value == null) continue;
            jsonObject.put(toJsonObject(key), toJsonObject(value));
        }
        return jsonObject;
    }

    /**
     * Метод collectionTypeToJson используется для работы с объектами типа Collection.
     * @param obj - объект для сериализации
     * @return    - возвращает готовый для сериализации объект
     */
    private Object collectionTypeToJson(Collection obj) {
        JSONArray jsonArray = new JSONArray();

        for (Object o : obj) {
            jsonArray.add(toJsonObject(o));
        }
        return jsonArray;
    }

    /**
     * Метод arrayToJson используется для работы с объектами типа Array
     * @param obj - объект для сериализации
     * @return    - возвращает готовый для сериализации объект
     */
    private Object arrayToJson(Object obj) {
        JSONArray jsonArray = new JSONArray();
        int length = Array.getLength(obj);

        for (int i = 0; i < length; i++) {
            Object o = Array.get(obj, i);
            jsonArray.add(toJsonObject(o));
        }
        return jsonArray;
    }

    /**
     * Метод otherClassToJson используется для работы с объектами класса, имеющих в качестве поля другие объекты
     * @param obj - объект для сериализации
     * @return    - возвращает готовый для сериализации объект
     */
    private Object otherClassToJson(Object obj) {
        JSONObject jsonObject = new JSONObject();

        // Определяем класс пришедшего obj
        Class<?> cls = obj.getClass();
        // Определяем поля класса
        //Field[] fields = cls.getDeclaredFields();
        Set<Field> fields = new LinkedHashSet<>(Arrays.asList(cls.getDeclaredFields()));

        for (Field field : fields) {
            // Исключаем static поля класса
            int modifier = field.getModifiers();
            if(Modifier.isStatic(modifier)) continue;

            // Определяем название поля
            String fieldName = field.getName();

            // Определяем значение поля
            Object valueOfField = getValueOfField(field, obj);

            if (valueOfField != null) {
                jsonObject.put(toJsonObject(fieldName), toJsonObject(valueOfField));
            } else continue;
        }

        return jsonObject;
    }

    /**
     * Метод initValueOfField используется для получения значения заданного поля
     * @param field - поле объекта;
     * @param obj   - объект для сериализации.
     * @return      - возвращает значение поля объекта
     */
    private Object getValueOfField(Field field, Object obj) {
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
