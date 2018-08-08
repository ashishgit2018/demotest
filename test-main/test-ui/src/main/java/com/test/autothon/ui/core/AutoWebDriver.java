package com.test.autothon.ui.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class AutoWebDriver {
	private final static Logger logger = Logger.getLogger(AutoWebDriver.class);
	private static WebDriver driver;
	AutoWebDriver(String browser) throws Exception{
		logger.info("Initializing webdriver");
		browser = browser.trim().toLowerCase();
		if (browser == null)
			browser = "chrome";
		
		switch(browser) {
		case "chrome":
			chromeDriver();
			break;
		case "ie":
			ieDriver();
			break;
		case "firefox":
			fireFoxDriver();
			break;
		case "mobile_chrome":
			mobileChromeDriver();
			break;
		default:
			logger.info("invalid browser name");
			throw new Exception("Invalid browser name");
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				if(null==driver) {
					
				}else {
					logger.info("closing webdriver");
					driver.close();
					driver.quit();
				}
			}
		});
	}
	protected void ieDriver() {
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		System.setProperty("webdriver.ie.driver", "src/test/resources/drivers/IEDriverServer.exe");
		if(null==driver) {
			logger.info("Intializing ie browser");
			driver=new InternetExplorerDriver(capabilities);
		}
	}
	protected void chromeDriver() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
		if(null==driver) {
			logger.info("Intializing chrome browser");
			driver=new ChromeDriver();
		}
	}
	protected void fireFoxDriver() {
		System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
		if(null==driver) {
			logger.info("Intializing firefox browser");
			driver=new FirefoxDriver();
		}
	}
	protected void mobileChromeDriver() throws MalformedURLException {
		DesiredCapabilities cap=DesiredCapabilities.android();
		cap.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);
		//cap.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "My_Device");
		cap.setCapability(MobileCapabilityType.VERSION, "4.2.2");
		URL url=new URL("http://0.0.0.0:4723/wd/hub");
		if(null==driver) {
			logger.info("Intializing chrome browser for mobile");
			driver=new AndroidDriver(url, cap);
		}
	}
	protected WebDriver getDriver() {
		return driver;	
	}
}
