package com.test.autothon.api.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * @author Rahul_Goyal
 */
public class JsonUtils {

    private final static Logger logger = LogManager.getLogger(JsonUtils.class);

    public static Map<String, Object> parseJsonStringToMap(String json) {
        return new JsonMapConvertor().convertJsonStringToMap(json);
    }

    public static String parseJsonMapToString(Map<String, Object> json) {
        return new JsonMapConvertor().convertMapToJsonString(json);
    }

    public static String parsetoPrettyJson(String json) {
        return new JsonMapConvertor().toJsonStringPrettyFormat(json);
    }


}
