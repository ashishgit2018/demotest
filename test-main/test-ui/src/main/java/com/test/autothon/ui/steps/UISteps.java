package com.test.autothon.ui.steps;

import com.test.autothon.common.ReadEnvironmentVariables;
import com.test.autothon.ui.core.UIOperations;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

public class UISteps extends UIOperations {

    @Given("^I use \"(.*?)\" browser$")
    public void getDriver(String browser) throws Exception {
        browser = getOverlay(browser);
        ReadEnvironmentVariables.setBrowserName(browser);
    }

    @And("^I launch \"([^\"]*)\"$")
    public void launchUrl(String url) throws Exception {
        url = getOverlay(url);
        launchURL(url);

    }

    @And("^I Click on element \"(.*?)\"$")
    public void clickOn(String element) {
        element = getOverlay(element);
        click(element);
    }

    @And("^I clear text for element \"(.*?)\"$")
    public void clearText(String element) {
        element = getOverlay(element);
        clearData(element);
    }

    @And("^I enter text as \"(.*?)\" in element \"(.*?)\" $")
    public void setText(String value, String element) {
        value = getOverlay(value);
        element = getOverlay(element);
        enterText(element, value);
    }

    @And("^I select \"(.*)\" from drop down with element as \"(.*)\" and selection method is \"(.*?)\"$")
    public void selectFromDropDown(String value, String element, String selectionMethod) {
        value = getOverlay(value);
        element = getOverlay(element);
        selectionMethod = getOverlay(selectionMethod);
        selectValue(element, selectionMethod, value);
    }
}
