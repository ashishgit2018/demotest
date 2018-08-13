package com.test.autothon.api.core;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HttpClientService {

    private final static Logger logger = Logger.getLogger(HttpClientService.class);

    private String requestMethod;
    private String requestPayload;
    private String requestUri;
    private CookieStore cookieStore = new BasicCookieStore();
    private Map<String, Object> jsonResponse;
    private Map<String, String> requestHeaders = new HashMap<String, String>();
    private CloseableHttpResponse httpResponse;
    private HttpEntity httpResponseEntity;
    private String httpResponseEntityString;

    public CloseableHttpResponse get(String url, Map<String, String> headers, Map<String, String> pathParams) {
        HttpUriRequest httpUriRequest = buildRequest("GET", url, headers, pathParams, null);
        return execueHttprequest(httpUriRequest);
    }

    public CloseableHttpResponse postJson(String url, Map<String, String> headers, Map<String, String> pathParams, Map<String, Object> jsonMap) {
        HttpUriRequest httpUriRequest = buildRequest("POST", url, headers, pathParams, jsonMap);
        return execueHttprequest(httpUriRequest);
    }

    public CloseableHttpResponse putJson(String url, Map<String, String> headers, Map<String, String> pathParams, Map<String, Object> jsonMap) {
        HttpUriRequest httpUriRequest = buildRequest("PUT", url, headers, pathParams, jsonMap);
        return execueHttprequest(httpUriRequest);
    }

    public CloseableHttpResponse delete(String url, Map<String, String> headers, Map<String, String> pathParams) {
        HttpUriRequest httpUriRequest = buildRequest("DELETE", url, headers, pathParams, null);
        return execueHttprequest(httpUriRequest);
    }

    private CloseableHttpResponse execueHttprequest(HttpUriRequest httpUriRequest) {
        CloseableHttpClient httpClient = null;
        clearResponseData();
        try {
            httpResponse = httpClient.execute(httpUriRequest);
            if (httpResponse != null)
                parseHttpResponse();
        } catch (IOException e) {
            logger.error("Error executing the GET request \n" + e);
        } finally {
            try {
                logger.info("Closing the Http connection");
                httpClient.close();
            } catch (IOException e) {
                logger.error("Error closing the Http connection \n" + e);
            }
        }
        return httpResponse;
    }

    private void parseHttpResponse() {
        httpResponseEntity = httpResponse.getEntity();
        logger.info("Http Response Headers :\n" + logResponseHeaders());

        if (httpResponseEntity == null) {
            logger.info("Response is blank");
            return;
        }

        try {
            httpResponseEntityString = EntityUtils.toString(httpResponseEntity);
            logger.info("Http Response code :" + httpResponse.getStatusLine());
            logger.info("Http Response \n" + JsonUtils.parsetoPrettyJson(httpResponseEntityString));
            jsonResponse = JsonUtils.parseJsonStringToMap(httpResponseEntityString);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                logger.error("Error closing Http response \n" + e);
            }
        }
    }

    private String logResponseHeaders() {
        Header[] hdrs = httpResponse.getAllHeaders();
        StringBuffer strbf = new StringBuffer();
        for (int i = 0; i < hdrs.length; i++) {
            strbf.append("Header key: " + hdrs[i].getName() + "\t Value : " + hdrs[i].getValue() + "\n");
        }
        return strbf.toString();
    }

    private HttpUriRequest buildRequest(String method, String url, Map<String, String> headers, Map<String, String> uriPathParams, Map<String, Object> jsonObject) {

        HttpUriRequest httpUriRequest = null;
        RequestBuilder builder = null;
        String jsonString = "";
        String headerKey = "";
        String headerValue = "";
        logger.debug("HTTP_REQUEST_BEGIN");
        logger.debug("HTTP Method : " + method);

        if (jsonObject != null) {
            jsonString = JsonUtils.parseJsonMapToString(jsonObject);
            logger.info("Json payload : \n" + JsonUtils.parsetoPrettyJson(jsonString));
        }

        try {
            clearRequestData();
            if (method.equals("GET")) {
                builder = RequestBuilder.get();
            } else if (method.equals("POST")) {
                builder = RequestBuilder.post();
            } else if (method.equals("PUT")) {
                builder = RequestBuilder.put();
            } else if (method.equals("DELETE")) {
                builder = RequestBuilder.delete();
            }

            builder = builder.setUri(new URI(buildUrl(url, uriPathParams))).setEntity(new StringEntity(jsonString, Consts.UTF_8));
            logger.debug("Request url: " + builder.getUri().toString());
            setRequestUri(builder.getUri().toString());
            setRequestMethod(method);
            setRequestPayload(jsonString);


            if (headers != null) {
                setRequestHeaders(headers);
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    headerKey = header.getKey();
                    headerValue = header.getValue();
                    builder.addHeader(headerKey, headerValue);
                    logger.debug("Request header key=" + headerKey + "    headerValue =" + headerValue);
                }
            }
            httpUriRequest = builder.build();
        } catch (URISyntaxException e) {
            logger.error("buildRequest(): Error in building the json request", e);
        }

        logger.debug("HTTP_REQUEST_END");
        return httpUriRequest;
    }

    private void clearRequestData() {
        clearRequestHeaders();
        clearRequestMethod();
        clearRequestPayload();
        clearRequestUri();
    }

    public void clearResponseData() {
        jsonResponse.clear();
        httpResponseEntityString = "";
    }


    public int getResponseCode() {
        StatusLine status = httpResponse.getStatusLine();
        return status.getStatusCode();
    }


    public void clearCookies() {
        cookieStore.clear();
    }


    public int sizeOfResponse() {
        return jsonResponse.size();
    }

    private void clearRequestMethod() {
        this.requestMethod = null;
    }

    private void clearRequestPayload() {
        this.requestPayload = null;
    }

    private void clearRequestUri() {
        this.requestUri = null;
    }

    private void clearRequestHeaders() {
        this.requestHeaders.clear();
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    private void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestPayload() {
        return requestPayload;
    }

    private void setRequestPayload(String requestPayload) {
        this.requestPayload = requestPayload;
    }

    public String getRequestUri() {
        return requestUri;
    }

    private void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    private void setRequestHeaders(Map<String, String> requestHeaders) {
        clearRequestHeaders();
        this.requestHeaders.putAll(requestHeaders);
    }

    public String getHttpResponseEntityString() {
        return httpResponseEntityString;
    }

    public Map<String, Object> getJsonResponse() {
        return jsonResponse;
    }

    private String buildUrl(String url, Map<String, String> vars) {
        if (vars != null) {
            return StrSubstitutor.replace(url, vars, "{", "}");
        } else {
            return url;
        }
    }
}
