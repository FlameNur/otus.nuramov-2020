package com.nuramov.hw08_JSON_Object_Writer;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Тест проводит проверку метода toJson класса JsonObjectWriter
 *  Метод toJson из заданного объекта/экземпляра класса формирует json объект для сериализации
 *  Десерилизация проводится методом fromJson класса Gson
 */

class JsonObjectWriterTest {
    ClassToSerializing classToSerializing;
    JsonObjectWriterDNTWRK jsonObjectWriter;
    Gson gson;

    @BeforeEach
    void initClass() {
        classToSerializing = new ClassToSerializing(1188, "test", "message", 3);
    }

    @BeforeEach
    void initObjectWriter() {
        jsonObjectWriter = new JsonObjectWriterDNTWRK();
    }

    @BeforeEach
    void initGson() {
        gson = new Gson();
    }

    @Test
    void toJsonTest() {
        String json = jsonObjectWriter.toJson(classToSerializing);
        ClassToSerializing obj = gson.fromJson(json, ClassToSerializing.class);
        assertEquals(classToSerializing, obj);
    }

    @AfterEach
    void TestEnd() {
        System.out.println("Тест успешно завершен");
    }

}