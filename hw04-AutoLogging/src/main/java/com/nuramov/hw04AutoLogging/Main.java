package com.nuramov.hw04AutoLogging;

public class Main {
    public static void main(String[] args) {
        MyInterface myInterface = MyProxyClass.classCreater(new MyClass());
        myInterface.method1(4, 5);
        myInterface.method2(2, 2, "Строка");

        MyInterface myInterface2 = MyProxyClass.classCreater(new MyClass2());
        myInterface.method1(40, 50);
        myInterface.method2(20, 20, "Строка2");
    }
}
