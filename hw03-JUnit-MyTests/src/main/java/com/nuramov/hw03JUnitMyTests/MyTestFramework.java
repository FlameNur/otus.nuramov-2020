package com.nuramov.hw03JUnitMyTests;

import com.nuramov.hw03JUnitMyTests.annotations.*;
import com.nuramov.hw02DIYarrayList.DIYarrayList;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyTestFramework {
    public static <T> void run(Class<T> testClass) {
        int successfullyPassedTests = 0;
        Method[] methods = testClass.getDeclaredMethods();

        try {
            Constructor <T> constructor = testClass.getDeclaredConstructor();
            T object = constructor.newInstance();

            BeforeAllMethod(methods);
            successfullyPassedTests = TestMethod(methods, object);
            AfterAllMethod(methods);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException
                | NoSuchMethodException | IllegalArgumentException e) {
            System.out.println("Successfully passed test methods: 0");
            e.printStackTrace();
            return;
        }
        System.out.printf("Successfully passed test methods: %d", successfullyPassedTests);
    }

    private static void BeforeAllMethod (Method[] methods) throws InvocationTargetException, IllegalAccessException {
        for(Method m : methods) {
            if(m.isAnnotationPresent(BeforeAll.class)) {
                m.invoke(null);
            }
        }
    }

    private static <T> int TestMethod (Method[] methods, T object) {
        int successfullyPassedTests = 0;

        for(Method m : methods) {
            if(m.isAnnotationPresent(Test.class)) {
                try {
                    BeforeEachMethod(methods, object);
                    m.invoke(object);
                    AfterEachMethod(methods, object);
                    successfullyPassedTests++;
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return successfullyPassedTests;
    }

    private static <T> void BeforeEachMethod (Method[] methods, T object) throws InvocationTargetException, IllegalAccessException {
        for(Method m : methods) {
            if(m.isAnnotationPresent(BeforeEach.class)) {
                m.invoke(object);
            }

        }
    }

    private static <T> void AfterEachMethod (Method[] methods, T object) throws InvocationTargetException, IllegalAccessException {
        for(Method m : methods) {
            if(m.isAnnotationPresent(AfterEach.class)) {
                m.invoke(object);
            }
        }
    }

    private static void AfterAllMethod (Method[] methods) throws InvocationTargetException, IllegalAccessException {
        for(Method m : methods) {
            if(m.isAnnotationPresent(AfterAll.class)) {
                m.invoke(null);
            }
        }
    }
}