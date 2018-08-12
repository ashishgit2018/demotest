package com.test.autothon.api.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonMapConvertor {
    private final static Logger logger = Logger.getLogger(JsonMapConvertor.class);

    ObjectMapper mapper = new ObjectMapper();

    public Map<String, Object> convertJsonStringToMap(String input) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            result = mapper.readValue(input, new TypeReference<HashMap<String, Object>>() {
            });
        } catch (IOException e) {
            logger.error("Error parsing Json String to Map" + e);
        }
        return result;
    }

    public String convertMapToJsonString(Map<String, Object> input) {
        String json = null;
        try {
            json = mapper.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            logger.error("Error converting Json Map to String" + e);
        }
        return json;
    }

    public String toJsonStringPrettyFormat(String jsonInput) {
        Object jsonOutput = new Object();
        String prettyJson = null;
        try {
            jsonOutput = mapper.readValue(jsonInput, Object.class);
            prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonOutput);
        } catch (IOException e) {
            logger.error("Error converting json string to pretty format" + e);
        }
        return prettyJson;
    }

    public Map<String, Object> setpayloadkeyByDotNotation(Map<String, Object> inputMap, String key, String value) {
        return new HashMap<>();
    }
}
