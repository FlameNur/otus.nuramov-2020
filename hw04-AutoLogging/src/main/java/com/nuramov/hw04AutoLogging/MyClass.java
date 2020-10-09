package com.nuramov.hw04AutoLogging;

import com.nuramov.hw04AutoLogging.annotations.Log;

public class MyClass implements MyInterface {

    @Override
    public void method1(int param1, int param2) {
        int sum = param1 + param2;
        System.out.println("Сумма двух параметров метода method1: " + sum);
    }

    @Override
    public void method2(int param1, int param2, String param3) {
        int sum = param1 + param2;
        System.out.println("Сумма двух параметров метода method2: " + sum);
        System.out.println("Значение param3: " + param3);
    }
}
