package com.nuramov.hw08_JSON_Object_Writer;

import java.util.*;

/**
 * class ClassToSerializing - класс-пример для сериализаци
 */

public class ClassToSerializing {
    public static int publicInt = 1;

    public String publicString = "publicString";
    public Map<Integer, String> map;

    private final int id;
    private final String name;
    private final List<String> list;

    private String message;
    private int type;
    private int[] array;


    public ClassToSerializing(int id, String name, String message, int type) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.type = type;

        this.array = new int[] {this.id, this.type};

        this.list = new ArrayList<>();
        list.add(this.name);
        list.add(this.message);

        this.map = new TreeMap<>();
        map.put(this.id, this.name);
        map.put(this.type, this.message);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassToSerializing that = (ClassToSerializing) o;

        if (!Objects.equals(publicString, that.publicString)) return false;
        if (!Objects.equals(message, that.message)) return false;
        if (id != that.id) return false;
        if (type != that.type) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public String toString() {
        return "ClassToSerializing{" +
                "class id=" + id +
                ", class name='" + name + '\'' +
                ", class type=" + type +
                ", message='" + message + '\'' +
                ", publicString='" + publicString + '\'' +
                '}';
    }
}
