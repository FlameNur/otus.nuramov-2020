package com.nuramov.hw08_JSON_Object_Writer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * class JsonObjectWriter представляет собой json object writer (object to JSON string) аналогичный gson
 */

public class JsonObjectWriter {

    /**
     *
     * @param obj
     * @return
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
     *
     * @param obj
     * @return
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
     *
     * @param obj
     * @return
     */
    private String mapTypeToJson(Map obj) {
        JSONObject jsonObject = new JSONObject();

        for (Object key : obj.keySet()) {
            Object value = obj.get(key);
            jsonObject.put(toJsonObject(key), toJsonObject(value));
        }
        return jsonObject.toString();
    }

    /**
     *
     * @param obj
     * @return
     */
    private String collectionTypeToJson(Collection obj) {
        JSONArray jsonArray = new JSONArray();

        for (Object o : obj) {
            jsonArray.add(toJsonObject(o));
        }
        return jsonArray.toString();
    }

    /**
     *
     * @param obj
     * @return
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
     *
     * @param obj
     * @return
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
            Object valueOfField = initValueOfField(field, obj);

            if (valueOfField != null) {
                jsonObject.put(toJsonObject(fieldName), toJsonObject(valueOfField));
            } else continue;
        }

        return jsonObject;
    }

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
