package com.nuramov.hw08_JSON_Object_Writer;

import javax.json.*;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Пример десериализатора
 */

public class TestObjectWriter {

    public static <T> T fromJson(String json, Class<T> beanClass) {
        JsonValue value = Json.createReader(new StringReader(json)).read();
        return (T) decode(value, beanClass);
    }

    private static Object decode(JsonValue jsonValue, Type targetType) {
        if (jsonValue.getValueType() == JsonValue.ValueType.NULL) {
            return null;
        }
        else if (jsonValue.getValueType() == JsonValue.ValueType.TRUE || jsonValue.getValueType() == JsonValue.ValueType.FALSE) {
            return decodeBoolean(jsonValue, targetType);
        }
        else if (jsonValue instanceof JsonNumber) {
            return decodeNumber((JsonNumber) jsonValue, targetType);
        }
        else if (jsonValue instanceof JsonString) {
            return decodeString((JsonString) jsonValue, targetType);
        }
        else if (jsonValue instanceof JsonArray) {
            return decodeArray((JsonArray) jsonValue, targetType);
        }
        else if (jsonValue instanceof JsonObject) {
            return decodeObject((JsonObject) jsonValue, targetType);
        }
        else {
            throw new UnsupportedOperationException("Unsupported json value: " + jsonValue);
        }
    }

    private static Object decodeBoolean(JsonValue jsonValue, Type targetType) {
        if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.valueOf(jsonValue.toString());
        }
        else {
            throw new UnsupportedOperationException("Unsupported boolean type: " + targetType);
        }
    }

    private static Object decodeNumber(JsonNumber jsonNumber, Type targetType) {
        if (targetType == int.class || targetType == Integer.class) {
            return jsonNumber.intValue();
        }
        else if (targetType == long.class || targetType == Long.class) {
            return jsonNumber.longValue();
        }
        else {
            throw new UnsupportedOperationException("Unsupported number type: " + targetType);
        }
    }

    private static Object decodeString(JsonString jsonString, Type targetType) {
        if (targetType == String.class) {
            return jsonString.getString();
        }
        else if (targetType == Date.class) {
            try {
                return new SimpleDateFormat("MMM dd, yyyy H:mm:ss a", Locale.ENGLISH).parse(jsonString.getString()); // This is default Gson format. Alter if necessary.
            }
            catch (ParseException e) {
                throw new UnsupportedOperationException("Unsupported date format: " + jsonString.getString());
            }
        }
        else {
            throw new UnsupportedOperationException("Unsupported string type: " + targetType);
        }
    }

    private static Object decodeArray(JsonArray jsonArray, Type targetType) {
        Class<?> targetClass = (Class<?>) ((targetType instanceof ParameterizedType) ? ((ParameterizedType) targetType).getRawType() : targetType);

        if (List.class.isAssignableFrom(targetClass)) {
            Class<?> elementClass = (Class<?>) ((ParameterizedType) targetType).getActualTypeArguments()[0];
            List<Object> list = new ArrayList<>();

            for (JsonValue item : jsonArray) {
                list.add(decode(item, elementClass));
            }

            return list;
        }
        else if (targetClass.isArray()) {
            Class<?> elementClass = targetClass.getComponentType();
            Object array = Array.newInstance(elementClass, jsonArray.size());

            for (int i = 0; i < jsonArray.size(); i++) {
                Array.set(array, i, decode(jsonArray.get(i), elementClass));
            }

            return array;
        }
        else {
            throw new UnsupportedOperationException("Unsupported array type: " + targetClass);
        }
    }

    private static Object decodeObject(JsonObject object, Type targetType) {
        Class<?> targetClass = (Class<?>) ((targetType instanceof ParameterizedType) ? ((ParameterizedType) targetType).getRawType() : targetType);

        if (Map.class.isAssignableFrom(targetClass)) {
            Class<?> valueClass = (Class<?>) ((ParameterizedType) targetType).getActualTypeArguments()[1];
            Map<String, Object> map = new LinkedHashMap<>();

            for (Map.Entry<String, JsonValue> entry : object.entrySet()) {
                map.put(entry.getKey(), decode(entry.getValue(), valueClass));
            }

            return map;
        }
        else try {
            Object bean = targetClass.newInstance();

            for (PropertyDescriptor property : Introspector.getBeanInfo(targetClass).getPropertyDescriptors()) {
                if (property.getWriteMethod() != null && object.containsKey(property.getName())) {
                    property.getWriteMethod().invoke(bean, decode(object.get(property.getName()), property.getWriteMethod().getGenericParameterTypes()[0]));
                }
            }

            return bean;
        }
        catch (Exception e) {
            throw new UnsupportedOperationException("Unsupported object type: " + targetClass, e);
        }
    }
}
