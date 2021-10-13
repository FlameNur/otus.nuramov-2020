package com.nuramov.hw09_Jdbc_Template.Example_Classes;

import com.nuramov.hw09_Jdbc_Template.Annotations.*;

public class User {
    @id
    private long id = 0;

    private String name;
    private int age;

    public long getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
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
