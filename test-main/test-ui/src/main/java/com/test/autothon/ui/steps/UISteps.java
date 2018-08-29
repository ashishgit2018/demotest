package com.test.autothon.ui.steps;

import java.io.IOException;

import com.test.autothon.ui.core.CustomHtmlReport;
import com.test.autothon.ui.core.UIAutomation;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

public class UISteps extends UIAutomation {

    public UISteps() {

    }

    @Given("^Execute \"(.*?)\"$")
    public void executeTC(String testCaseName) throws Exception {
        testCaseName = getOverlay(testCaseName);
        setTestCaseName(testCaseName);
    }

    @Given("^I use \"(.*?)\"$")
    public void getDriver(String browser) throws Exception {
        browser = getOverlay(browser);
        getWebDriver(browser);
    }

    @And("^I launch \"([^\"]*)\"$")
    public void launchUrl(String url) throws Exception {
        url = getOverlay(url);
        launchURL(url);
     
    }

    @And("^I select \"([^\"]*)\"$")
    public void BuyItem(String itemName) throws Exception {
        itemName = getOverlay(itemName);
        selectItem(itemName);
    }

    @And("^I search \"([^\"]*)\"$")
    public void SearchItem(String item) throws Exception {
        item = getOverlay(item);
        searchItem(item);
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

    @Given("^Read Property File \"(.*?)\"$")
    public void ReadProperty(String propertyFile) throws Exception {
        readMovieNames(propertyFile);
    }

    @Given("^I search for movie \"(.*?)\" \"(.*?)\"$")
    public void searchMovies(String movieNo, String movieName) throws InterruptedException, IOException {
        //searchMovie(movieNo, movieName);
        searchAllMovies();
        
    }
    
    
    
    
}
