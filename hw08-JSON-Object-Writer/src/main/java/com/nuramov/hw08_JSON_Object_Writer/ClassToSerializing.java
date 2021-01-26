package com.nuramov.hw08_JSON_Object_Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassToSerializing {
    public static int publicInt = 1;
    public String publicString = "publicString";
    private final int id;
    private final String name;
    private final String message;
    private final int type;
    private int[] array;
    private final List<String> list;

    public ClassToSerializing(int id, String name, String message, int type) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.type = type;

        this.array = new int[] {this.id, this.type};

        this.list = new ArrayList<>();
        list.add(this.name);
        list.add(this.message);
    }


    @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ClassToSerializing that = (ClassToSerializing) o;

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
                    ", message=" + message +
                    '}';
        }
}
