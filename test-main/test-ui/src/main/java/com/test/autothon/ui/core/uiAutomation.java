package com.test.autothon.ui.core;

import org.openqa.selenium.WebDriver;

import com.test.autothon.ui.AutomationUIUtils;

public class uiAutomation extends UIOperations{
	WebDriver driver;
	
	public void getWebDriver(String broswer) throws Exception {
		AutoWebDriver autoDriver=new AutoWebDriver(broswer);
        driver=autoDriver.getDriver();
        setDriver(driver);
	}
	
	public void launchURL(String url) throws Exception {
        driver.get(url);
	}
	
	public void setTestCaseName(String testCaseName) {
		AutomationUIUtils.setTestCaseName(testCaseName);
	}
	
	public void searchItem(String item) throws Exception {
		String searchTextBox="id_twotabsearchtextbox";
		String searchButton="xpath_//input[contains(@class,'nav-input')]";
		enterText(searchTextBox, item);
		waitForElementToBeClickable(searchButton);
		click(searchButton);
	}
	
	public void selectItem(String ItemName) throws InterruptedException {
		String itemLocator="linkText_" + ItemName;
		waitForElementToBeClickable(itemLocator);
		click(itemLocator);
		Thread.sleep(5000);
	}
}
