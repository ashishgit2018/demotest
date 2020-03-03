package com.test.autothon.ui.core;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SeleniumWebDriverHelper {

    public WebDriver driver;

    public WebDriver createChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "chromedriver_file_location");
        driver = new ChromeDriver();
        return driver;
    }


    public WebDriver createRemoteChromeDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(CapabilityType.BROWSER_NAME, "chrome");
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, "LINUX"); //Platform
        capabilities.setCapability(CapabilityType.BROWSER_VERSION, ""); //Browser Version
        try {
            driver = new RemoteWebDriver(new URL("https://localhost:4444/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }
}
