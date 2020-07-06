package com.nuramov.hw03JUnitMyTests;

import com.nuramov.hw03JUnitMyTests.annotations.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyTestFramework {
    private static int allTests = 0;
    private static int passedTests = 0;

    public static void run(Class<?> testClass) {
        Method[] methods = testClass.getDeclaredMethods();
        BeforeAllMethod(methods);

        try {
            Constructor constructor = testClass.getDeclaredConstructor();
            Object object = constructor.newInstance();
            TestMethod(methods, object);
        } catch (IllegalAccessException | InvocationTargetException
                | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        AfterAllMethod(methods);
        System.out.printf("Passed test methods: %d, Failed test methods: %d%n", passedTests, allTests - passedTests);
    }

    private static void BeforeAllMethod (Method[] methods) {
        for(Method m : methods) {
            if(m.isAnnotationPresent(BeforeAll.class)) {
                allTests++;
                try {
                    m.invoke(null);
                    passedTests++;
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    System.out.println("Failed @BeforeAll test method: " + m);
                }
            }
        }
    }

    private static void TestMethod (Method[] methods, Object object) {
        for(Method m : methods) {
            if(m.isAnnotationPresent(Test.class)) {
                BeforeEachMethod(methods, object);
                allTests++;
                try {
                    m.invoke(object);
                    passedTests++;
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    System.out.println("Failed @Test test method: " + m);
                }
                AfterEachMethod(methods, object);
            }
        }
    }

    private static void BeforeEachMethod (Method[] methods, Object object) {
        for(Method m : methods) {
            if(m.isAnnotationPresent(BeforeEach.class)) {
                allTests++;
                try {
                    m.invoke(object);
                    passedTests++;
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    System.out.println("Failed @BeforeEach test method: " + m);
                }
            }
        }
    }

    private static void AfterEachMethod (Method[] methods, Object object) {
        for(Method m : methods) {
            if(m.isAnnotationPresent(AfterEach.class)) {
                allTests++;
                try {
                    m.invoke(object);
                    passedTests++;
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    System.out.println("Failed @AfterEach. test method: " + m);
                }
            }
        }
    }

    private static void AfterAllMethod (Method[] methods) {
        for(Method m : methods) {
            if(m.isAnnotationPresent(AfterAll.class)) {
                allTests++;
                try {
                    m.invoke(null);
                    passedTests++;
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    System.out.println("Failed @AfterAll test method: " + m);
                }
            }
        }
    }
}
