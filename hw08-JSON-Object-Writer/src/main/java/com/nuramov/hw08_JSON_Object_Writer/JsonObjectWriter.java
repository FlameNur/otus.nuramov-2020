package com.nuramov.hw08_JSON_Object_Writer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.*;

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
        StringWriter writer = new StringWriter();

        if(obj == null){
            writer.write("null");
            return writer.toString();
        }

        if(obj instanceof String){
            writer.write('\"');
            writer.write((String) obj);
            writer.write('\"');
            return writer.toString();
        }

        if(obj instanceof Character){
            writer.write('\"');
            writer.write((Character) obj);
            writer.write('\"');
            return writer.toString();
        }

        if(obj instanceof Number){
            writer.write(obj.toString());
            return writer.toString();
        }

        if(obj instanceof Boolean){
            writer.write(obj.toString());
            return writer.toString();
        }

        if(obj instanceof Map) {
            return mapTypeToJson((Map) obj);
        }

        if (obj instanceof Collection) {
            return collectionTypeToJson((Collection) obj);
        }

        if (obj.getClass().isArray()) {
            return arrayToJson(obj);
        }

        return "otherClassToJson(obj)";
    }

    /**
     *
     * @param obj
     * @return
     */
    private String mapTypeToJson(Map obj) {
        StringWriter writer = new StringWriter();
        try {
            JSONObject.writeJSONString(obj, writer);
            return writer.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param obj
     * @return
     */
    private String collectionTypeToJson(Collection obj) {
        String result = "";
        JSONArray jsonArray = new JSONArray();

        for (Object o : obj) {
            jsonArray.add(toJson(o));
        }
        result = jsonArray.toString();

        return result;
    }

    /**
     *
     * @param obj
     * @return
     */
    private String arrayToJson(Object obj) {
        JSONArray jsonArray = new JSONArray();
        int length = Array.getLength(obj);

        for (int i = 0; i < length; i++) {
            Object o = Array.get(obj, i);
            jsonArray.add(toJson(o));
        }

        return jsonArray.toString();
    }

    /**
     *
     * @param obj
     * @return
     */
    private String otherClassToJson(Object obj) {
        String result = "";
        JSONArray jsonArray = new JSONArray();


        return result;
    }
}
