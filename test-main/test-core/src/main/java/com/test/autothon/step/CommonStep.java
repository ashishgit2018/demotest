package com.test.autothon.step;

import com.test.autothon.common.Constants;
import com.test.autothon.common.FileUtils;
import com.test.autothon.common.StepDefinition;
import cucumber.api.java.en.Given;

/**
 * @author Rahul_Goyal
 */
public class CommonStep extends StepDefinition {

    @Given("^Wait for \"(.*?)\" seconds")
    public void waitForSeconds(String seconds) {
        int intSeconds = Integer.valueOf(getOverlay(seconds));
        waitForSecond(intSeconds);
    }

    @Given("^Write to temp properties as key \"(.*?)\" and value \"(.*?)\"$")
    public void writeValueToTempProperties(String key, String value) {
        FileUtils.writeToTempFile(key, value);
    }

    @Given("^Write to json file at \"(.*?)\" and value \"(.*?)\"$")
    public void writeValueToJson(String file, String value) {
        file = getOverlay(file);
        value = getOverlay(value);
        file = Constants.jsonResourcePath + "/" + file;
        FileUtils.writeToFile(file, value);
    }
}
