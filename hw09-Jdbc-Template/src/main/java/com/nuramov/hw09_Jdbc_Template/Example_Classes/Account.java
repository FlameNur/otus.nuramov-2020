package com.nuramov.hw09_Jdbc_Template.Example_Classes;

import com.nuramov.hw09_Jdbc_Template.Annotations.*;

/**
 * class Account используется в качестве примера
 */
public class Account {
    @id
    private long no = 0;

    private String type;
    private int rest;
    private double test;

    public long getNo() {
        return no;
    }

    public String getType() {
        return type;
    }

    public int getRest() {
        return rest;
    }

    public double getTest() {
        return test;
    }


    public void setNo(long no) {
        this.no = no;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public void setTest(double test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                ", test=" + test +
                '}';
    }
}
