package com.test.autothon.api.core;

import com.test.autothon.common.FileUtils;
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
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rahul_Goyal
 */
public class HttpClientService {

    private final static Logger logger = LogManager.getLogger(HttpClientService.class);

    private String requestMethod;
    private String requestPayload;
    private String requestUri;
    private CookieStore cookieStore = new BasicCookieStore();
    private Map<String, Object> jsonResponse;
    private Map<String, String> requestHeaders = new HashMap<String, String>();
    private CloseableHttpResponse httpResponse;
    private HttpEntity httpResponseEntity;
    private String httpResponseEntityString;

    public String getFullUrl(String restURL, String uri) {
        if (uri.startsWith("https://") || uri.startsWith("http://"))
            return uri;
        return restURL + uri;
    }

    public CloseableHttpResponse httpGetRequest(String url, Map<String, String> headers, Map<String, String> pathParams) {
        HttpUriRequest httpUriRequest = buildRequest("GET", url, headers, pathParams, null);
        return execueHttpRequest(httpUriRequest);
    }

    public CloseableHttpResponse httpPostRequest(String url, Map<String, String> headers, Map<String, String> pathParams, Map<String, Object> jsonMap) {
        HttpUriRequest httpUriRequest = buildRequest("POST", url, headers, pathParams, jsonMap);
        return execueHttpRequest(httpUriRequest);
    }

    public CloseableHttpResponse httpPutRequest(String url, Map<String, String> headers, Map<String, String> pathParams, Map<String, Object> jsonMap) {
        HttpUriRequest httpUriRequest = buildRequest("PUT", url, headers, pathParams, jsonMap);
        return execueHttpRequest(httpUriRequest);
    }

    public CloseableHttpResponse httpDeleteRequest(String url, Map<String, String> headers, Map<String, String> pathParams) {
        HttpUriRequest httpUriRequest = buildRequest("DELETE", url, headers, pathParams, null);
        return execueHttpRequest(httpUriRequest);
    }

    private CloseableHttpResponse execueHttpRequest(HttpUriRequest httpUriRequest) {
        CloseableHttpClient httpClient = null;
        clearResponseData();
        try {
            httpClient = HttpClients.custom()
                    .disableRedirectHandling()
                    .build();
            httpResponse = httpClient.execute(httpUriRequest);
            if (httpResponse != null)
                parseHttpResponse();
        } catch (IOException e) {
            logger.error("Error executing the request \n" + e);
        } finally {
            try {
                logger.info("Closing the Http connection");
                if (httpClient != null)
                    httpClient.close();
            } catch (IOException e) {
                logger.error("Error closing the Http connection \n" + e);
            }
        }
        logger.info("HTTP_REQUEST_END");

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
            FileUtils.writeToTempFile(hdrs[i].getName(), hdrs[i].getValue());
        }
        return strbf.toString();
    }

    private HttpUriRequest buildRequest(String method, String url, Map<String, String> headers, Map<String, String> uriPathParams, Map<String, Object> jsonObject) {

        HttpUriRequest httpUriRequest = null;
        RequestBuilder builder = null;
        String jsonString = "";
        String headerKey = "";
        String headerValue = "";
        logger.info("HTTP_REQUEST_BEGIN");
        logger.info("HTTP Method : " + method);
        logger.info("HTTP URI " + url);

        if (jsonObject != null) {
            jsonString = JsonUtils.parseJsonMapToString(jsonObject);
            logger.info("HTTP Json Payload : \n" + JsonUtils.parsetoPrettyJson(jsonString));
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
            url = url.trim();
            builder = builder.setUri(new URI(buildUrl(url, uriPathParams))).setEntity(new StringEntity(jsonString, Consts.UTF_8));
            logger.info("Request url: " + builder.getUri().toString());
            setRequestUri(builder.getUri().toString());
            setRequestMethod(method);
            setRequestPayload(jsonString);


            if (headers != null) {
                setRequestHeaders(headers);
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    headerKey = header.getKey();
                    headerValue = header.getValue();
                    builder.addHeader(headerKey, headerValue);
                    logger.info("Request Header Key=" + headerKey + "\tValue =" + headerValue);
                }
            }
            httpUriRequest = builder.build();
        } catch (URISyntaxException e) {
            logger.error("buildRequest(): Error in building the json request", e);
        }

        return httpUriRequest;
    }

    private void clearRequestData() {
        logger.info("Clearing Request Headers, Cookies & Payload");
        clearRequestHeaders();
        clearRequestMethod();
        clearRequestPayload();
        clearRequestUri();
        clearCookies();
    }

    public void clearResponseData() {
        if (jsonResponse != null)
            jsonResponse.clear();
        httpResponseEntityString = "";
    }

    public int getResponseCode() {
        StatusLine status = httpResponse.getStatusLine();
        return status.getStatusCode();
    }

    public void clearCookies() {
        if (cookieStore != null)
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
        if (this.requestHeaders != null) this.requestHeaders.clear();
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
