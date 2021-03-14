package com.nuramov.hw08_JSON_Object_Writer.testObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Car {
    private static final int wheels = 4;
    private final int doors = 5;
    private final int[] drivers = {1, 2, 3, 4};
    private final Rule[] rules = {new Rule(), new Rule(), new Rule()};
    private final Rule[][] rules1 = {{new Rule()}, {new Rule()}, {new Rule()}};
    private final String color = null;
    private final boolean working = true;
    private final Salon salon = null;
    private final ArrayList<Map<String, Rule>> rules3 = new ArrayList<>();

    {
        Map<String, Rule> maps = new HashMap<>();
        maps.put("1", null);
        rules3.add(maps);
    }

    /*
    // Если потребуется сравнивать объекты через equals()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car that = (Car) o;

        if (!Objects.equals(anyString, that.anyString)) return false;
        if (anyInt != that.anyInt) return false;

        // Сравнение map
        if (map.size() != that.map.size()) return false;
        for (Integer key : map.keySet()) {
            if (!map.get(key).equals(that.map.get(key))) return false;
        }

        // Сравнение list
        if (list.size() != that.list.size()) return false;
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals(that.list.get(i))) return false;
        }

        return Objects.equals(anyName, that.anyName);
    }
     */

    @Override
    public String toString() {
        return "Car{" +
                "doors=" + doors +
                ", color='" + color + '\'' +
                ", working=" + working +
                ", salon=" + salon +
                '}';
    }
}
