package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Annotations.*;

import java.math.BigInteger;

public class User {
    @id
    private long id = 0;

    private static long count = 0;
    private String name;
    private int age;

    public User(String name, int age) {
        id = ++count;
        this.name = name;
        this.age = age;
    }

    public long getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}