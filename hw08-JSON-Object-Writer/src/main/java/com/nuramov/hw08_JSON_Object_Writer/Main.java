package com.nuramov.hw08_JSON_Object_Writer;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        ClassToSerializing obj = new ClassToSerializing(1, "test", "message", 2);
        System.out.println(obj);

        String json = gson.toJson(obj);
        System.out.println(json);

        ClassToSerializing obj2 = gson.fromJson(json, ClassToSerializing.class);
        System.out.println(obj.equals(obj2));
        System.out.println(obj2);
    }
}
