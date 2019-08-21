package com.test.autothon.auto.steps;

import com.test.autothon.auto.core.TestAutothonMain2019;
import com.test.autothon.auto.core.TestAutothonRest2019;
import com.test.autothon.common.StepDefinition;
import cucumber.api.java.en.Given;

public class TestAutothonRest2019Steps extends StepDefinition {

    TestAutothonRest2019 testAutothonRest2019 = new TestAutothonRest2019();

    TestAutothonMain2019 testAutothonMain2019 = new TestAutothonMain2019();

    @Given("^Execute the multipart form-data post request where uri is \"(.*?)\" and file name is \"(.*?)\"$")
    public void executeMultiPartFormData(String uri, String fileName) {
        uri = getOverlay(uri);
        fileName = getOverlay(fileName);
        testAutothonRest2019.executeTheMultiPartFormDataReq(uri, fileName);
    }

    @Given("^I do youtube search for \"(.*?)\"$")
    public void youtubesearch(String searchText) throws Exception {
        testAutothonMain2019.youtubeSearch(searchText);
    }
}
