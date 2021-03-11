package com.nuramov.hw08_JSON_Object_Writer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.json.JsonArray;
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
        if(obj == null){
            return null;
        }

        if(obj instanceof String || obj instanceof Character){
            return '\"' + obj.toString() + '\"';
        }

        /*if(obj instanceof Number || obj instanceof Boolean){
            return obj.toString();
        }*/

        if(obj instanceof Map) {
            return mapTypeToJson((Map) obj);
        }

        if (obj instanceof Collection) {
            return collectionTypeToJson((Collection) obj);
        }

        /*if (obj.getClass().isArray()) {
            String s = arrayToJson(obj).toString();
            System.out.println(s);
            return s;
        }*/

        return toJsonObject(obj).toString();
    }

    private Object toJsonObject (Object obj) {
        if(obj instanceof Number || obj instanceof Boolean){
            return obj;
        }

        if (obj.getClass().isArray()) {

            return arrayToJson(obj);
        }
        return null;
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
        JSONArray jsonArray = new JSONArray();

        for (Object o : obj) {
            jsonArray.add(toJson(o));
        }
        return jsonArray.toString();
    }

    /**
     *
     * @param obj
     * @return
     */
    private Object arrayToJson(Object obj) {
        JSONArray jsonArray = new JSONArray();
        int length = Array.getLength(obj);

        for (int i = 0; i < length; i++) {
            Object o = Array.get(obj, i);
            jsonArray.add(toJson(o));
        }
        return jsonArray;
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
