package com.nuramov.hw08_JSON_Object_Writer;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * class JsonObjectWriter представляет собой json object writer (object to JSON string) аналогичный gson
 */

public class JsonObjectWriter {

    /**
     * Вместо Object obj можно использовать JsonObject toJsonFunc,
     * но пока не знаю как и в чем плюсы и тогда используется jsonWriter.writeObject(toJsonFunc)
     * @param obj
     * @return
     */
    public String toJson(Object obj, Class beanClass) {
        // Не совсем понятный пример из инета
        /*
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(new PrintWriter(stringWriter));
        //jsonWriter.writeObject(toJsonFunc);
        jsonWriter.writeObject(Json.createObjectBuilder().build());
        jsonWriter.close();
        return stringWriter.toString();
         */


        // На основе примера десериализатора
        //JsonValue value = Json.createReader(new StringReader(json)).read();

        // Какая-то ошибка по Maven dependency
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(new PrintWriter(stringWriter));
        jsonWriter.writeObject((JsonObject) obj);


        //return jsonObject(value, beanClass);
        return "";
    }

    private String jsonObject(JsonValue value, Class beanClass) {
        return "";
    }

}
