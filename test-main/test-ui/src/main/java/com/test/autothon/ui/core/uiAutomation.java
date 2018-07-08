package com.test.autothon.ui.core;

import org.openqa.selenium.WebDriver;

public class uiAutomation {
	WebDriver driver;
	public void getWebDriver(String broswer) throws Exception {
		AutoWebDriver autoDriver=new AutoWebDriver(broswer);
        driver=autoDriver.getDriver();
        driver.get("https://google.com");
	}
	
	public void launchURL(String url) throws Exception {
        driver.get(url);
	}
}
