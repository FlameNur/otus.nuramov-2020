package com.nuramov.hw08_JSON_Object_Writer;

import java.lang.reflect.Field;

public class JsonObjectWriter {

    public String toJson(Object obj) {
        String str = "";
        StringBuilder stringBuilder = new StringBuilder(str);
        Class<?> cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            Class<?> fld = field.getType();
            str = "Class name: " + field.getName() + " " + "Class type: " + fld.getName();
            String empty = " ";
            stringBuilder.append(empty);
            stringBuilder.append(str);
        }

        return stringBuilder.toString();
    }
}
