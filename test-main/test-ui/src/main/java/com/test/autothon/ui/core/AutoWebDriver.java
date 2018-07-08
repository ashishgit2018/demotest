package com.test.autothon.ui.core;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AutoWebDriver {
	private final static Logger logger = Logger.getLogger(AutoWebDriver.class);
	private WebDriver driver;
	AutoWebDriver(String browser) throws Exception{
		logger.info("Initializing webdriver");
		
		switch(browser) {
		case "chrome":
			chromeDriver();
			break;
		case "firefox":
			fireFoxDriver();
			break;
		default:
			logger.info("invalid browser name");
			throw new Exception("Invalid browser name");
		}
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				if(null==driver) {
					
				}else {
					logger.info("closing webdriver");
					driver.quit();
				}
			}
		});
	}
	protected void chromeDriver() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
		if(null==driver) {
			driver=new ChromeDriver();
		}
	}
	protected void fireFoxDriver() {
		System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
		if(null==driver) {
			driver=new FirefoxDriver();
		}
	}
	protected WebDriver getDriver() {
		return driver;	
	}
}
