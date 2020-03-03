package com.test.autothon;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class TestZaleniumFirefox {

    WebDriver driver;

    @BeforeTest
    public void initializeWebDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "firefox");
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, "LINUX");
        try {
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
            driver.manage().window().maximize();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLinkedin() {
        driver.get("https://www.linkedin.com");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(driver.getTitle());
    }


    @AfterTest
    public void quitBrowser() {
        driver.quit();
    }
}
