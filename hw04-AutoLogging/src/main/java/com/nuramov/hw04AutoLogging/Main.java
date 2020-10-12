package com.nuramov.hw04AutoLogging;

public class Main {
    public static void main(String[] args) {
        MyInterface myInterface = (MyInterface) MyProxyClass.classCreater(new MyClass());
        myInterface.method1(4, 5);
        myInterface.method2(2, 2, "Строка");

        MyInterface myInterface2 = (MyInterface) MyProxyClass.classCreater(new MyClass2());
        myInterface2.method1(40, 50);
        myInterface2.method2(20, 20, "Строка2");

        MyInterface2 myInterface3 = (MyInterface2) MyProxyClass.classCreater(new MyClass2());
        myInterface3.method3(5, 8);
    }
}
