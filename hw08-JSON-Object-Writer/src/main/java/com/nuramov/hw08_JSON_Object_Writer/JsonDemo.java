package com.nuramov.hw08_JSON_Object_Writer;

import com.google.gson.Gson;

public class JsonDemo {
    public static void main(String[] args) {
        JsonObjectWriter jsonObjectWriter = new JsonObjectWriter();   // Доработать!!!
        Gson gson = new Gson();
        ClassToSerializing obj = new ClassToSerializing(1188, "test", "message", 3);
        System.out.println(obj);
        System.out.println("________________________");

        String myJson = jsonObjectWriter.toJson(obj);       // Доработать!!!
        System.out.println(myJson);                         // Доработать!!!

        System.out.println("________________________");
        String json = gson.toJson(obj);
        System.out.println(json);


        ClassToSerializing obj2 = gson.fromJson(json, ClassToSerializing.class);
        System.out.println(obj.equals(obj2));
        System.out.println(obj2);

        System.out.println("Проверка");
        //ClassToSerializing obj3 = gson.fromJson(myJson, ClassToSerializing.class);
        //System.out.println(obj.equals(obj3));
        //System.out.println(obj3);

    }
}
