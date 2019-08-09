package com.test.autothon.ui.core;

import org.openqa.selenium.WebDriver;

public class DriverFactory {

    private static DriverFactory instance = new DriverFactory();
    private ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    private ThreadLocal<AutoWebDriver> iDriver = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static DriverFactory getInstance() {
        return instance;
    }

    public void setDriver() {
        iDriver.set(new AutoWebDriver());
        driver.set(iDriver.get().getDriver());
    }

    public WebDriver getDriver() // call this method to get the driver object and launch the browser
    {
        return driver.get();
    }

    public void tearBrowser() {
        iDriver.get().tearBrowser();
    }
}