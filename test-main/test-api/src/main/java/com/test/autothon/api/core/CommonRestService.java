package com.test.autothon.api.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rahul_Goyal
 */
public class CommonRestService {

    private final static Logger logger = LogManager.getLogger(CommonRestService.class);


    private String restURL;
    private Map<String, String> inputHeaders = new HashMap<String, String>();
    private String inputStringJsonpayload;
    private Map<String, Object> inputJsonPayload = new HashMap<String, Object>();
    private Map<String, String> inputPathParams = new HashMap<String, String>();
    private HttpClientService httpClientService;

    public CommonRestService() {
        httpClientService = new HttpClientService();
    }

    public void setRestBaseUrl(String url) {
        logger.info("Setting Rest Base URL to : " + url);
        this.restURL = url;
    }

    public String getFullUrl(String uri) {
        if (uri.startsWith("https://") || uri.startsWith("http://"))
            return uri;
        return restURL + uri;
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
        JsonMapConvertor jsmc = new JsonMapConvertor();
        inputJsonPayload = jsmc.setPayloadkeyByDotNotation(inputJsonPayload, key, value);
        inputStringJsonpayload = JsonUtils.parseJsonMapToString(inputJsonPayload);
        logger.info("Updated json input payload set to\n" + JsonUtils.parsetoPrettyJson(inputStringJsonpayload));
    }

    public void removeInputJsonPayloadKey(String key) {
        JsonMapConvertor jsmc = new JsonMapConvertor();
        inputJsonPayload = jsmc.removePayloadKeyByDotNotation(inputJsonPayload, key);
        inputStringJsonpayload = JsonUtils.parseJsonMapToString(inputJsonPayload);
        logger.info("Updated json input payload set to\n" + JsonUtils.parsetoPrettyJson(inputStringJsonpayload));
    }

    public void httpGet(String uri) {
        httpClientService.httpGetRequest(getFullUrl(uri), inputHeaders, inputPathParams);
    }

    public void httpPost(String uri) {
        httpClientService.httpPostRequest(getFullUrl(uri), inputHeaders, inputPathParams, inputJsonPayload);
    }

    public void httpPut(String uri) {
        httpClientService.httpPutRequest(getFullUrl(uri), inputHeaders, inputPathParams, inputJsonPayload);
    }

    public void httpDelete(String uri) {
        httpClientService.httpDeleteRequest(getFullUrl(uri), inputHeaders, inputPathParams);
    }

    public int getResponseCode() {
        return httpClientService.getResponseCode();
    }

    public String getResponseString() {
        return httpClientService.getHttpResponseEntityString();
    }

    public String getReadableResponseString() {
        return JsonUtils.parsetoPrettyJson(getResponseString());
    }

    public Map<String, Object> getResponseJson() {
        return httpClientService.getJsonResponse();
    }

    public String getResponseJsonKeyValue(String key) {
        JsonMapConvertor jmc = new JsonMapConvertor();
        String value = jmc.getPayloadKeyByDotNotation(httpClientService.getJsonResponse(), key);
        logger.info(" Retrieved Key [ " + key + " ]   with Value [ " + value + " ]");
        return value;
    }

    public String getRequestJsonKeyValue(String key) {
        JsonMapConvertor jmc = new JsonMapConvertor();
        String value = jmc.getPayloadKeyByDotNotation(inputJsonPayload, key);
        logger.info(" Retrieved Key [ " + key + " ]   with Value [ " + value + " ]");
        return value;
    }

    public String getInputJsonKeyValue(String key) {
        JsonMapConvertor jmc = new JsonMapConvertor();
        return jmc.getPayloadKeyByDotNotation(inputJsonPayload, key);
    }

    public void clearResponse() {
        httpClientService.clearResponseData();
    }

    public void clearCookies() {
        httpClientService.clearCookies();
    }

    public int getResponseSize() {
        return httpClientService.sizeOfResponse();
    }


}
