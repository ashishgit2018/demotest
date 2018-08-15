package com.test.autothon.step;

import com.test.autothon.common.StepDefinition;
import cucumber.api.java.en.Given;

public class CommonStep extends StepDefinition {

    @Given("^Wait for \"(.*?)\" seconds")
    public void waitForSeconds(String seconds) {
        int intSeconds = Integer.valueOf(getOverlay(seconds));
        waitForSecond(intSeconds);
    }
}
