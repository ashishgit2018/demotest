package com.test.autothon.auto.steps;

import com.test.autothon.auto.core.TestAutothonRest2019;
import com.test.autothon.common.StepDefinition;
import cucumber.api.java.en.Given;

public class TestAutothonRest2019Steps extends StepDefinition {

    TestAutothonRest2019 testAutothonRest2019 = new TestAutothonRest2019();

    @Given("^Execute the multipart form-data post request where uri is \"(.*?)\" and file name is \"(.*?)\"$")
    public void executeMultiPartFormData(String uri, String fileName) {
        uri = getOverlay(uri);
        fileName = getOverlay(fileName);
        testAutothonRest2019.executeTheMultiPartFormDataReq(uri, fileName);
    }
}
