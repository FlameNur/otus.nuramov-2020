package com.nuramov.hw08_JSON_Object_Writer;

import com.google.gson.Gson;
import com.nuramov.hw08_JSON_Object_Writer.testObjects.Car;

import javax.json.JsonObject;
import java.util.*;

public class Test {
    public static void main(String[] args) throws IllegalAccessException {

        JsonObjectWriter test = new JsonObjectWriter();

        myObjectTest(test);

        arrayListTest(test);

        arrayStringTest(test);

        linkedListTest(test);

        mapTest(test);

        hashSetTest(test);

        stringTest(test);

        arrayPrimitiveTest(test);

        arrayObjTest(test);

        intTest(test);

        nullTest(test);

        charTest(test);
    }

    private static void mapTest(JsonObjectWriter test) throws IllegalAccessException {
        Map<String, int[]> cars = new HashMap<>();
        int[] ints = {1, 2, 3};
        cars.put("1", ints);
//        cars.put("2", new Car());
        //cars.put(null, ints);
        System.out.println(test.toJson(cars));


        Gson gson = new Gson();
        System.out.println(gson.toJson(cars));
        System.out.println();
    }

    private static void linkedListTest(JsonObjectWriter test) throws IllegalAccessException {
        List<Car> carList = new LinkedList<>();
        carList.add(new Car());
        carList.add(new Car());
        System.out.println(test.toJson(carList));


        Gson gson = new Gson();
        System.out.println(gson.toJson(carList));
        System.out.println();
    }

    private static void arrayListTest(JsonObjectWriter test) throws IllegalAccessException {
        List<String> carList = new ArrayList<>();
        carList.add("1");
        carList.add(null);
//        carList.add(new Car());
        System.out.println(test.toJson(carList));              //исправить!!!

        //System.out.println("Gson: ");
        Gson gson = new Gson();
        System.out.println(gson.toJson(carList));
        System.out.println();
    }

    private static JsonObjectWriter myObjectTest(JsonObjectWriter test) throws IllegalAccessException {

        Car ford = new Car();
        System.out.println(test.toJson(ford));


        Gson gson = new Gson();
        System.out.println(gson.toJson(ford));
        System.out.println();
        return test;
    }

    private static void hashSetTest(JsonObjectWriter test) throws IllegalAccessException {
        Set<Car> carList = new HashSet<>();
        carList.add(new Car());
//        carList.add(new Car());
        System.out.println(test.toJson(carList));


        Gson gson = new Gson();
        System.out.println(gson.toJson(carList));
        System.out.println();
    }

    private static void stringTest(JsonObjectWriter test) throws IllegalAccessException {

        System.out.println(test.toJson("Привет"));


        Gson gson = new Gson();
        System.out.println(gson.toJson("Привет"));
        System.out.println();
    }

    private static void arrayPrimitiveTest(JsonObjectWriter test) throws IllegalAccessException {
        int[][] ints = {{1, 2, 3}, {4, 5, 6}};
        System.out.println(test.toJson(ints));


        Gson gson = new Gson();
        System.out.println(gson.toJson(ints));
        System.out.println();
    }

    private static void arrayStringTest(JsonObjectWriter test) throws IllegalAccessException {
        String[][] strings= {{"s1", "s2", "s3"}, {"s4", "s5", "s6"}};
        //String[] strings= {"s1", "s2", "s3"};
        System.out.println(test.toJson(strings));


        Gson gson = new Gson();
        System.out.println(gson.toJson(strings));
        System.out.println();
    }

    private static void arrayObjTest(JsonObjectWriter test) throws IllegalAccessException {
        Car[][] cars = {{null}, {new Car()}};
        System.out.println(test.toJson(cars));


        Gson gson = new Gson();
        System.out.println(gson.toJson(cars));
        System.out.println();
    }

    private static void intTest(JsonObjectWriter test) throws IllegalAccessException {

        System.out.println(test.toJson(1));


        Gson gson = new Gson();
        System.out.println(gson.toJson(1));
        System.out.println();
    }

    private static void nullTest(JsonObjectWriter test) throws IllegalAccessException {

        System.out.println(test.toJson(null));


        Gson gson = new Gson();
        System.out.println(gson.toJson(null));
        System.out.println();
    }

    private static void charTest(JsonObjectWriter test) throws IllegalAccessException {

        System.out.println(test.toJson('Z'));


        Gson gson = new Gson();
        System.out.println(gson.toJson('Z'));
        System.out.println();
    }
}
