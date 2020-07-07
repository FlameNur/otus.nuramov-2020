package com.nuramov.hw03JUnitMyTests;

import com.nuramov.hw03JUnitMyTests.annotations.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyTestFramework {
    public static <T> void run(Class<T> testClass) {
        int successfullyPassedTests = 0;
        Method[] methods = testClass.getDeclaredMethods();
        BeforeAllMethod(methods);

        try {
            Constructor <T> constructor = testClass.getDeclaredConstructor();
            T object = constructor.newInstance();
            successfullyPassedTests = TestMethod(methods, object);
        } catch (IllegalAccessException | InvocationTargetException
                | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        AfterAllMethod(methods);
        System.out.printf("Successfully passed test methods: %d", successfullyPassedTests);
    }

    private static void BeforeAllMethod (Method[] methods) {
        for(Method m : methods) {
            if(m.isAnnotationPresent(BeforeAll.class)) {
                try {
                    m.invoke(null);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static <T> int TestMethod (Method[] methods, T object) {
        int successfullyPassedTests = 0;

        for(Method m : methods) {
            if(m.isAnnotationPresent(Test.class)) {
                BeforeEachMethod(methods, object);
                try {
                    m.invoke(object);
                    successfullyPassedTests++;
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    System.out.println("Failed @Test test method: " + m);
                    e.printStackTrace();
                }
                AfterEachMethod(methods, object);
            }
        }
        return successfullyPassedTests;
    }

    private static <T> void BeforeEachMethod (Method[] methods, T object) {
        for(Method m : methods) {
            if(m.isAnnotationPresent(BeforeEach.class)) {
                try {
                    m.invoke(object);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static <T> void AfterEachMethod (Method[] methods, T object) {
        for(Method m : methods) {
            if(m.isAnnotationPresent(AfterEach.class)) {
                try {
                    m.invoke(object);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void AfterAllMethod (Method[] methods) {
        for(Method m : methods) {
            if(m.isAnnotationPresent(AfterAll.class)) {
                try {
                    m.invoke(null);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
