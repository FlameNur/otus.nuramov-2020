package com.nuramov.hw08_JSON_Object_Writer;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class JsonObjectWriter {

    public String toJson(Object obj) throws NoSuchFieldException, IllegalAccessException {
        // Начинаем собирать json объект
        StringBuilder stringBuilder = new StringBuilder("{");

        // Определяем класс пришедшего obj
        Class<?> cls = obj.getClass();

        // Определяем поля класса
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            // Определяем static поля класса и исключаем их, т.к. их не должно быть в json объекте
            int modifier = field.getModifiers();
            if(!Modifier.isStatic(modifier)) {
                // Определяем название поля
                String fieldName = field.getName();

                // Собираем строку поле-значение для объекта json
                stringBuilder.append("\"").append(fieldName).append("\":");

                // Определяем значение поля
                field.setAccessible(true);
                Object valueOfField = field.get(obj);

                // Если поле представляет собой массив
                if (field.getType().isArray()) {
                    int length = Array.getLength(valueOfField);
                    stringBuilder.append("[");
                    for (int i = 0; i < length; i++) {
                        if (i != 0) stringBuilder.append(",");
                        stringBuilder.append(Array.get(valueOfField, i));
                    }
                    stringBuilder.append("]");
                }

                // Если тип поля - String, значение поля берем в кавычки
                Class<?> fieldType = field.getType();
                String fldType = fieldType.getName();
                System.out.println(fldType);
                if(fldType.equals("String")) {
                    stringBuilder.append("\"").append(valueOfField.toString()).append("\"");
                } else stringBuilder.append(valueOfField.toString());

                stringBuilder.append(",");
            }
        }

        stringBuilder.append("}");
        // Удаляем последнюю запятую
        stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        return stringBuilder.toString();
    }
}
