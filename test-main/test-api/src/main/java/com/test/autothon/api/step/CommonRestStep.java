package com.test.autothon.api.step;

import com.test.autothon.api.core.CommonRestService;
import com.test.autothon.common.Constants;
import com.test.autothon.common.FileUtils;
import com.test.autothon.common.ReadPropertiesFile;
import com.test.autothon.common.StepDefinition;
import cucumber.api.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rahul_Goyal
 */
public class CommonRestStep extends StepDefinition {

    private final static Logger logger = LogManager.getLogger(CommonRestStep.class);
    public CommonRestService commonRestService;
    public Map<String, List<String>> data = new HashMap<>();

    public CommonRestStep() {
        this.commonRestService = new CommonRestService();
    }

    @Given("^Set the base uri as \"(.*?)\"$")
    public void setBaseURI(String baseURI) {
        baseURI = getOverlay(baseURI);
        commonRestService.setRestBaseUrl(baseURI);
    }

    @Given("^Set the Request header with key \"(.*?)\" and value \"(.*?)\"$")
    public void setInputHeader(String key, String value) {
        key = getOverlay(key);
        value = getOverlay(value);
        commonRestService.setInputHeader(key, value);
    }

    @Given("^Set the Request parameter with key \"(.*?)\" and value \"(.*?)\"$")
    public void setInputParameters(String key, String value) {
        key = getOverlay(key);
        value = getOverlay(value);
        commonRestService.setInputPathParams(key, value);
    }

    @Given("Set the common headers for a Json Post Request")
    public void setJsonPostRequestHeaders() {
        logger.info("Setting headers for a json post request.");
        setInputHeader("ACCEPT_HDR", "application/json, text/javascript, */*; q=0.01");
        setInputHeader("ENCODING_HDR", "gzip, deflate");
        setInputHeader("LANGUAGE_HDR", "en-US,en;q=0.5");
        setInputHeader("USER_AGENT_HDR", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:31.0) Gecko/20100101 Firefox/31.0");
        setInputHeader("X_REQUESTED_WITH_HDR", "XMLHttpRequest");
        setInputHeader("CONTENT_TYPE_HDR", "application/json; charset=UTF-8");
        setInputHeader("CONNECTION_HDR", "keep-alive");
    }

    @Given("^Set Json payload located in file \"(.*?)\"$")
    public void setJsonDataLocatedInFile(String fileName) {
        fileName = getOverlay(fileName);
        String jsonData = FileUtils.readFileAsString(Constants.jsonResourcePath, fileName);
        jsonData = getOverlay(jsonData);
        commonRestService.setInputJsonPayload(jsonData);
    }

    @Given("^Set Json payload located in temp file with key \"(.*?)\"$")
    public void setJsonDataFromTempFileWithKey(String key) {
        key = getOverlay(key);
        String jsonData = ReadPropertiesFile.getPropertyValue(key);
        jsonData = getOverlay(jsonData);
        commonRestService.setInputJsonPayload(jsonData);
    }

    @Given("^Set Json payload as \"(.*?)\"$")
    public void setJsonDataAs(String jsonData) {
        jsonData = getOverlay(jsonData);
        commonRestService.setInputJsonPayload(jsonData);
    }

    @Given("^Set Json payload located in file \"(.*?)\" by updating payload json key \"(.*?)\" with value \"(.*)\"$")
    public void setJsonDataLocatedInFileByUpdatingKey(String fileName, String key, String value) {
        fileName = getOverlay(fileName);
        String jsonData = FileUtils.readFileAsString(Constants.jsonResourcePath, fileName);
        jsonData = getOverlay(jsonData);
        commonRestService.setInputJsonPayload(jsonData);
        commonRestService.updateInputJsonPayloadKeyValue(key, value);
    }

    @Given("^Set Json payload located in properties file with key \"(.*?)\" by updating payload json key \"(.*?)\" with value \"(.*)\"$")
    public void setJsonDataLocatedInPropertiesFileByUpdatingKey(String key, String updateKey, String value) {
        key = getOverlay(key);
        String jsonData = ReadPropertiesFile.getPropertyValue(key);
        jsonData = getOverlay(jsonData);
        commonRestService.setInputJsonPayload(jsonData);
        commonRestService.updateInputJsonPayloadKeyValue(updateKey, value);

    }

    @Given("^Set Json payload located in file \"(.*?)\" by removing payload json key \"(.*?)\"$")
    public void setJsonDataLocatedInFileByRemovingKey(String fileName, String key) {
        fileName = getOverlay(fileName);
        String jsonData = FileUtils.readFileAsString(Constants.jsonResourcePath, fileName);
        jsonData = getOverlay(jsonData);
        commonRestService.setInputJsonPayload(jsonData);
        commonRestService.removeInputHeaderKey(key);
    }

    @Given("^Set Json payload located in properties file with key \"(.*?)\" by removing payload json key \"(.*?)\"$")
    public void setJsonDataLocatedInPropertiesFileByRemovingKey(String key, String updateKey
    ) {
        key = getOverlay(key);
        String jsonData = ReadPropertiesFile.getPropertyValue(key);
        jsonData = getOverlay(jsonData);
        commonRestService.setInputJsonPayload(jsonData);
        commonRestService.removeInputHeaderKey(updateKey);
    }

    @Given("^Perform GET request where uri is \"(.*?)\"$")
    public void performGET(String uri) {
        uri = getOverlay(uri);
        commonRestService.httpGet(uri);
    }

    @Given("^Perform POST requset where uri is \"(.*?)\"$")
    public void performPOST(String uri) {
        uri = getOverlay(uri);
        commonRestService.httpPost(uri);
    }

    @Given("^Perform PUT request where uri is \"(.*?)\"$")
    public void performPUT(String uri) {
        uri = getOverlay(uri);
        commonRestService.httpPut(uri);
    }

    @Given("^Perform DELETE request where uri is \"(.*?)\"$")
    public void performDELETE(String uri) {
        uri = getOverlay(uri);
        commonRestService.httpDelete(uri);
    }

    @Given("^Save Json Response as \"(.*?)\"$")
    public void saveJsonResponse(String key) {
        key = getOverlay(key);
        String response = commonRestService.getResponseString();
        FileUtils.writeToTempFile(key, response);
    }

    @Given("^Save Json Response Key-Value pair for \"(.*?)\" as \"(.*?)\"$")
    public void saveResponseKeyValueInTempProperties(String responseKey, String tempKey) {
        responseKey = getOverlay(responseKey);
        tempKey = getOverlay(tempKey);
        String value = commonRestService.getResponseJsonKeyValue(responseKey);
        FileUtils.writeToTempFile(tempKey, value);
    }

    @Given("^Save Json Request Key-Value pair for \"(.*?)\" as \"(.*?)\"$")
    public void saveRequestKeyValueInTempProperties(String requestKey, String tempKey) {
        requestKey = getOverlay(requestKey);
        tempKey = getOverlay(tempKey);
        String value = commonRestService.getRequestJsonKeyValue(requestKey);
        FileUtils.writeToTempFile(tempKey, value);
    }

    @Given("^Validate the Response code is \"(.*?)\"$")
    public void assertResponseCode(String responseCode) {
        int intResponseCode = Integer.valueOf(getOverlay(responseCode));
        int actualCode = commonRestService.getResponseCode();
        Assert.assertTrue("Response code mismatch \tExpected : " + responseCode + "\tActual : " + actualCode, intResponseCode == actualCode);
    }

    @Given("^Validate Response contains \"(.*?)\"$")
    public void assertResponseContains(String content) {
        content = getOverlay(content);
        String response = commonRestService.getResponseString();
        Assert.assertTrue("Response does not contains the string : " + content, response.contains(content));
    }

    @Given("^Validate Response does not contains \"(.*?)\"$")
    public void assertResponseDoesNotContains(String content) {
        content = getOverlay(content);
        String response = commonRestService.getResponseString();
        Assert.assertFalse("Response contains the string : " + content, response.contains(content));
    }

    @Given("^Validate Json Response Key \"(.*?)\" have value \"(.*?)\"$")
    public void assertResponseKeyHaveValue(String responseKey, String value) {
        responseKey = getOverlay(responseKey);
        value = getOverlay(value);
        String actualValue = commonRestService.getResponseJsonKeyValue(responseKey);
        Assert.assertTrue("Json Response Key : " + responseKey + " does not have expected value \tExpected : " + value + " \tActual : " + actualValue, actualValue.equalsIgnoreCase(value));
    }

    @Given("^Validate Json Response Key \"(.*?)\" is Not blank$")
    public void assertResponseKeyHaveValue(String responseKey) {
        responseKey = getOverlay(responseKey);
        String actualValue = commonRestService.getResponseJsonKeyValue(responseKey);
        Assert.assertTrue("Json Response Key : " + responseKey + " does have blank value \tActual : " + actualValue, !(actualValue.isEmpty() || actualValue == null || actualValue == ""));
    }

    @Given("^Validate Json Response Key \"(.*?)\" is blank$")
    public void assertResponseKeyHaveNoValue(String responseKey) {
        responseKey = getOverlay(responseKey);
        String actualValue = commonRestService.getResponseJsonKeyValue(responseKey);
        Assert.assertTrue("Json Response Key : " + responseKey + " does Not have blank value \tActual : " + actualValue, (actualValue.isEmpty() || actualValue == null || actualValue == ""));
    }

    @Given("^Validate Json Response Key \"(.*?)\" does not have value \"(.*?)\"$")
    public void assertResponseKeyDoesNotHaveValue(String responseKey, String value) {
        responseKey = getOverlay(responseKey);
        value = getOverlay(value);
        String actualValue = commonRestService.getResponseJsonKeyValue(responseKey);
        Assert.assertFalse("Json Response Key : " + responseKey + " have expected value \tExpected : " + value + " \tActual : " + actualValue, actualValue.equalsIgnoreCase(value));
    }

    @Given("^Validate Json Response is blank$")
    public void assertResponseBlank() {
        Assert.assertTrue("Response is not blank", commonRestService.getResponseString().isEmpty());
    }

    @Given("^Validate Json Response is Not blank$")
    public void assertResponseIsNotBlank() {
        Assert.assertFalse("Response is blank", !commonRestService.getResponseString().isEmpty());
    }

    @Given("^Validate Json response size is \"(.*?)\"$")
    public void assertJsonResponseSize(String responseSize) {
        int intResponseSize = Integer.valueOf(getOverlay(responseSize));
        int size = commonRestService.getResponseSize();
        Assert.assertTrue("Response size mismatch \tExpected : " + intResponseSize + " \tActual : " + size, size == intResponseSize);
    }


}
