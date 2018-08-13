package com.test.autothon.api.core;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommonRestService {

    private final static Logger logger = Logger.getLogger(CommonRestService.class);


    private String restURL;
    private Map<String, String> inputHeaders = new HashMap<String, String>();
    private String inputStringJsonpayload;
    private Map<String, Object> inputJsonPayload = new HashMap<String, Object>();
    private Map<String, String> inputPathParams = new HashMap<String, String>();
    private HttpClientService httpClientService;

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

    public void clearInputPathParams() {
        logger.info("Clearing all the input path parameters");
        inputPathParams.clear();
    }

    public void removeInputHeaderKey(String key) {
        logger.info("Removing key : [ " + key + " ] from input header");
        inputHeaders.remove(key);
    }

    public void removeInputPathParamKey(String key) {
        logger.info("Removing key : [ " + key + " ] from input path parameter");
        inputPathParams.remove(key);
    }

    public void setInputHeader(String key, String value) {
        logger.info("Setting input header key : [ " + key + " ] with value : [ " + value + "]");
        inputHeaders.put(key, value);
    }

    public void setInputPathParams(String key, String value) {
        logger.info("Setting input path parameter key : [ " + key + " ] with value : [ " + value + "]");
        inputPathParams.put(key, value);
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

    public void updateInputJsonPayloadKeyValue(String key, String value) {
        logger.info("Updating input json key : [ " + key + " ] with value : [ " + value + " ]");
        JsonMapConvertor jsmc = new JsonMapConvertor();
        inputJsonPayload = jsmc.setPayloadkeyByDotNotation(inputJsonPayload, key, value);
        inputStringJsonpayload = JsonUtils.parseJsonMapToString(inputJsonPayload);
        logger.info("Updated json input payload set to\n" + JsonUtils.parsetoPrettyJson(inputStringJsonpayload));
    }

    public void removeInputJsonPayloadKey(String key) {
        logger.info("Removing input json key : [ " + key + " ]");
        JsonMapConvertor jsmc = new JsonMapConvertor();
        inputJsonPayload = jsmc.removePayloadKeyByDotNotation(inputJsonPayload, key);
        inputStringJsonpayload = JsonUtils.parseJsonMapToString(inputJsonPayload);
        logger.info("Updated json input payload set to\n" + JsonUtils.parsetoPrettyJson(inputStringJsonpayload));
    }

    public void get(String uri) {
        httpClientService.get(uri, inputHeaders, inputPathParams);
    }

    public void postjJson(String uri) {
        httpClientService.postJson(uri, inputHeaders, inputPathParams, inputJsonPayload);
    }

    public void putJson(String uri) {
        httpClientService.putJson(uri, inputHeaders, inputPathParams, inputJsonPayload);
    }

    public void delete(String uri) {
        httpClientService.delete(uri, inputHeaders, inputPathParams);
    }

    public int getResponseCode() {
        return httpClientService.getResponseCode();
    }

    public String getResponseString() {
        return httpClientService.getHttpResponseEntityString();
    }

    public Map<String, Object> getResponseJson() {
        return httpClientService.getJsonResponse();
    }

    public String getResponseJsonKeyValue(String key) {
        JsonMapConvertor jmc = new JsonMapConvertor();
        return jmc.getPayloadKeyByDotNotation(httpClientService.getJsonResponse(), key);
    }

    public String getInputJsonKeyValue(String key) {
        JsonMapConvertor jmc = new JsonMapConvertor();
        return jmc.getPayloadKeyByDotNotation(inputJsonPayload, key);
    }


}
