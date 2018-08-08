package com.test.autothon.ui.steps;

import com.test.autothon.ui.core.uiAutomation;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

public class uiSteps extends uiAutomation {
	
	@Given("^Execute \"(.*?)\"$")
	public void executeTC(String testCaseName) throws Exception {
		setTestCaseName(testCaseName);
	}

	@Given("^I use \"(.*?)\"$")
	public void getDriver(String browser) throws Exception {
		getWebDriver(browser);
	}
	
	@And("^I launch \"([^\"]*)\"$")
	public void launchUrl(String url) throws Exception {
		launchURL(url);
	}
	
	@And("^I select \"([^\"]*)\"$")
	public void BuyItem(String itemName) throws Exception {
		selectItem(itemName);
	}
	
	@And("^I search \"([^\"]*)\"$")
	public void SearchItem(String item) throws Exception {
		searchItem(item);
	}
}
