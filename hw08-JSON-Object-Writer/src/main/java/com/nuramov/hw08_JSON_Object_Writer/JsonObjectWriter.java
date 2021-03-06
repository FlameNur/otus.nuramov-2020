package com.nuramov.hw08_JSON_Object_Writer;

import javax.json.*;
import java.lang.reflect.Array;
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
        if (obj instanceof Byte || obj instanceof Integer || obj instanceof Double ||
                obj instanceof Float || obj instanceof Short ||
                obj instanceof Long || obj instanceof Boolean) {
            return obj.toString();
        }
        return toJsonObject(obj);
    }

    /**
     *
     * @param obj
     * @return
     */
    private String toJsonObject(Object obj) {
        String result;

        if (obj instanceof Map) result = mapTypeToJson(obj);
        if (obj instanceof Collection) result = collectionTypeToJson(obj);
        if (obj.getClass().isArray()) result = arrayToJson(obj);
        else result = otherClassToJson(obj);

        return result;
    }

    /**
     *
     * @param obj
     * @return
     */
    private String mapTypeToJson(Object obj) {
        String result = "";

        Iterator<Map.Entry<Object, Object>> map = ((Map) obj).entrySet().iterator();

        JsonObjectBuilder builder = Json.createObjectBuilder();
        //(Map) object.forEach(builder::add);
        JsonObject jsonObject = builder.build();

        /*JsonObject jsonObject = Json.createObjectBuilder().build();
        jsonObject.putAll((Map) object);*/

        return result;
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


        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            Object elem = Array.get(obj, i);

        }


        return result;
    }

    /**
     *
     * @param obj
     * @return
     */
    private String otherClassToJson(Object obj) {
        String result = "";
        return result;
    }
}
