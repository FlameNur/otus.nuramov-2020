package com.nuramov.hw03JUnitMyTests;

import com.nuramov.hw03JUnitMyTests.annotations.*;
import com.nuramov.hw02DIYarrayList.DIYarrayList;
import static org.junit.jupiter.api.Assertions.*;

public class AnnotationsTestClass {

    static DIYarrayList<Integer> myList;

    AnnotationsTestClass() {
        System.out.println("Call of the constructor");
    }

    @BeforeAll
    static void beforeAll1() {
        myList = new DIYarrayList<>();
        System.out.println("BeforeAll1");
    }

    @BeforeAll
    static void beforeAll2() {
        System.out.println("BeforeAll2");
    }

    @BeforeEach
    void beforeEach1() {
        myList.add(0);
        myList.add(1);
        myList.add(2);
        System.out.println("BeforeEach1");
    }

    @BeforeEach
    void beforeEach2() {
        System.out.println("BeforeEach2");
    }

    @Test
    void test1() {
        assertArrayEquals(new Integer[]{0, 1, 2}, myList.toArray());
        System.out.println("test1");
    }

    @Test
    void test2() {
        assertEquals(1, myList.get(1));
        System.out.println("test2");
    }

    @Test
    void test3() {
        assertEquals(3, myList.size());
        System.out.println("test3");
    }

    @AfterEach
    void afterEach1() {
        myList.clear();
        System.out.println("AfterEach1");
    }

    @AfterEach
    void afterEach2() {
        System.out.println("AfterEach2");
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
