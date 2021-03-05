package com.nuramov.hw08_JSON_Object_Writer;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        JsonObjectWriter objectWriter = new JsonObjectWriter();

        ClassToSerializing obj = new ClassToSerializing(1, "test", "message", 2);
        System.out.println("Базовый объект:");
        System.out.println(obj);

        String json = gson.toJson(obj);
        System.out.println();
        System.out.println("Объект json после сериализации gson:");
        System.out.println(json);

        /*String json2 = objectWriter.toJson(obj, ClassToSerializing.class);
        System.out.println();
        System.out.println("Объект json после сериализации JsonObjectWriter:");
        System.out.println(json2);*/

        ClassToSerializing obj2 = gson.fromJson(json, ClassToSerializing.class);
        System.out.println();
        System.out.println("Базовый объект после десериализации gson:");
        //System.out.println(obj.equals(obj2));
        System.out.println(obj2);
    }
}
