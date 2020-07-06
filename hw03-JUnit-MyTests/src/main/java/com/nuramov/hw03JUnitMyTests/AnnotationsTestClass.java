package com.nuramov.hw03JUnitMyTests;

import com.nuramov.hw03JUnitMyTests.annotations.*;

public class AnnotationsTestClass {

    // Пока мало понятно как с этим работать

    AnnotationsTestClass() {
        System.out.println("Call of the constructor");
    }  // ????

    @BeforeAll
    static void beforeAll1() {
        System.out.println("BeforeAll1");
    }

    @BeforeAll
    static void beforeAll2() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @BeforeEach
    void beforeEach1() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @BeforeEach
    void beforeEach2() {
        System.out.println("BeforeEach2");
    }

    @BeforeEach
    void beforeEach3() {
        System.out.println("BeforeEach3");
    }

    @Test
    void test1() {
        System.out.println("test1");
    }

    @Test
    void test2() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @AfterEach
    void afterEach1() {
        System.out.println("AfterEach1");
    }

    @AfterEach
    void afterEach2() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @AfterEach
    void afterEach3() {
        System.out.println("AfterEach3");
    }

    @AfterAll
    static void afterAll1() {
        System.out.println("AfterAll1");
    }

    @AfterAll
    static void afterAll2() {
        System.out.println("AfterAll2");
    }
}
