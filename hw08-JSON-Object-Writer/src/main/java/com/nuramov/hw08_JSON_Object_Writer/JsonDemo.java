package com.nuramov.hw08_JSON_Object_Writer;

import com.google.gson.Gson;

public class JsonDemo {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        JsonObjectWriter jsonObjectWriter = new JsonObjectWriter();
        Gson gson = new Gson();
        ClassToSerializing obj = new ClassToSerializing(1188, "test", "message", 3);
        System.out.println(obj);
        System.out.println("________________________");

        System.out.println("Мой json:");
        String myJson = jsonObjectWriter.toJson(obj);
        System.out.println(myJson);

        System.out.println("________________________");
        String json = gson.toJson(obj);
        System.out.println("Объект json:");
        System.out.println(json);


        ClassToSerializing obj2 = gson.fromJson(json, ClassToSerializing.class);
        System.out.println(obj.equals(obj2));
        System.out.println(obj2);

        System.out.println("Проверка");
        //ClassToSerializing obj3 = gson.fromJson(myJson, ClassToSerializing.class);
        //System.out.println(obj.equals(obj3));                   // Доработать equals с новыми полями надо
        //System.out.println(obj3);

    }
}
