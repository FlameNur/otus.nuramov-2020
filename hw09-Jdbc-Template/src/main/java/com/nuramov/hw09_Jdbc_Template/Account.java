package com.nuramov.hw09_Jdbc_Template;

import com.nuramov.hw09_Jdbc_Template.Annotations.*;

public class Account {
    @id
    private long no = 0;

    private static long count = 0;
    private String type;
    private int rest;

    public Account(String name, int age) {
        no = ++count;
        this.type = name;
        this.rest = age;
    }

    public long getID() {
        return no;
    }

    public String getType() {
        return type;
    }

    public int getRest() {
        return rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }
}
