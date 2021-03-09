package com.nuramov.hw08_JSON_Object_Writer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
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
        if (obj == null) {
            return null;
        }
        if (obj instanceof String || obj instanceof Character) {
            return "\"" + obj.toString() + "\"";
        }
        /*if (obj instanceof Byte || obj instanceof Integer || obj instanceof Double ||
                obj instanceof Float || obj instanceof Short ||
                obj instanceof Long || obj instanceof Boolean) {
            return obj.toString();
        }*/
        return toJsonObject(obj);
    }

    /**
     *
     * @param obj
     * @return
     */
    private String toJsonObject(Object obj) {
        String result;

        if (obj instanceof Map) result = mapTypeToJson((Map) obj);
        if (obj instanceof Collection) result = collectionTypeToJson((Collection) obj);
        if (obj.getClass().isArray()) result = arrayToJson(obj);
        else result = otherClassToJson(obj);

        return result;
    }

    /**
     *
     * @param obj
     * @return
     */
    private String mapTypeToJson(Map obj) {
        String result = "";
        JSONObject jsonObject = new JSONObject();
        StringWriter writer = new StringWriter();

        try {
            JSONObject.writeJSONString(obj, writer);
            writer.close();                                   // Надо проверить
            return writer.toString();
        } catch(IOException e){
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }




        //return result;
    }

    /**
     *
     * @param obj
     * @return
     */
    private String collectionTypeToJson(Object obj) {
        String result = "";
        return result;
    }

    /**
     *
     * @param obj
     * @return
     */
    private String arrayToJson(Object obj) {
        String result = "";
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        StringWriter writer = new StringWriter();







        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            //jsonArray.add(Array.get(obj, i));
            //jsonArray.add(obj[i]);


        }
        result = jsonArray.toString();

        return result;
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
