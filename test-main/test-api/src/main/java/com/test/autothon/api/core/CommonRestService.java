package com.test.autothon.api.core;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommonRestService {

    private final static Logger logger = Logger.getLogger(CommonRestService.class);


    private String restURL;
    private Map<String, String> inputHeaders = new HashMap<String, String>();
    private String inputStringJsonpayload;
    private Map<String, Object> inputJsonPayload = new HashMap<String, Object>();
    private CloseableHttpResponse httpResponse;

    public void setRestUrl(String url) {
        logger.info("Setting Rest URL to : " + url);
        this.restURL = url;
    }

    public String getResturl() {
        return restURL;
    }

    public void clearInputHeader() {
        logger.info("Clearing all the input headers");
        inputHeaders.clear();
    }

    public void removeInputHeader(String key) {
        logger.info("Removing key : [ " + key + " ] from input header");
        inputHeaders.remove(key);
    }

    public void setInputHeader(String key, String value) {
        logger.info("Setting input header key : [ " + key + " ] with value : [ " + value + "]");
        inputHeaders.put(key, value);
    }

    public void clearInputjsonpayload() {
        logger.info("Clearing Input Json payloads");
        inputStringJsonpayload = null;
        inputJsonPayload.clear();
    }

    public void setInputJsonPayload(String data) {
        logger.info("Setting input json payload to \n" + JsonUtils.parsetoPrettyJson(data));
        inputStringJsonpayload = data;
        inputJsonPayload = JsonUtils.parseJsonStringToMap(data);
    }

    public String getInputStringPayload() {
        return inputStringJsonpayload;
    }

    public void updateInputJsonPayloadkey(String key, String value) {
        logger.info("Updating input json key : [ " + key + " ] with value : [ " + value + " ]");
        inputJsonPayload.put(key, value);
        String jsonUpdatedInput = JsonUtils.parseJsonMapToString(inputJsonPayload);
        logger.info("Updated json input payload set to\n" + JsonUtils.parsetoPrettyJson(jsonUpdatedInput));
    }

    public void udateInputjsonpayloadkeyByDotNotation(String dotNotationPath, String data) {

    }


}
