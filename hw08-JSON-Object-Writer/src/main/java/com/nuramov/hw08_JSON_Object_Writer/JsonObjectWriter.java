package com.nuramov.hw08_JSON_Object_Writer;

import javax.json.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

/**
 * class JsonObjectWriter представляет собой json object writer (object to JSON string) аналогичный gson
 */

public class JsonObjectWriter {

    /**
     *
     * @param obj
     * @return
     */
    public String toJson(Object obj) {
        if (obj == null) return null;
        if (obj instanceof String || obj instanceof Character) return "\"" + obj.toString() + "\"";

        return toJsonObject(obj).toString();
    }

    private JsonObject toJsonObject(Object obj) {
        JsonObject jsonObject = null;

        // Тип объекта - Map
        if (obj instanceof Map) jsonObject = mapTypeToJson(obj);

        // if
        // if
        // if
        // if


        //JsonObject jsonObject = Json.createObjectBuilder().build();
        return jsonObject;
    }

    private JsonObject mapTypeToJson(Object obj) {

        Iterator<Map.Entry<Object, Object>> map = ((Map) obj).entrySet().iterator();

        JsonObjectBuilder builder = Json.createObjectBuilder();
        //(Map) object.forEach(builder::add);
        JsonObject jsonObject = builder.build();

        /*JsonObject jsonObject = Json.createObjectBuilder().build();
        jsonObject.putAll((Map) object);*/

        return jsonObject;
    }
}
