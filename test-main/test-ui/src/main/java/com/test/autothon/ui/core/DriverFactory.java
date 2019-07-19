package com.test.autothon.ui.core;

import org.openqa.selenium.WebDriver;

public class DriverFactory {

    private static DriverFactory instance = new DriverFactory();
    ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>() // thread local driver object for webdriver
    {
        @Override
        protected WebDriver initialValue() {
            AutoWebDriver autoWebDriver = new AutoWebDriver();
            return autoWebDriver.getDriver();
        }
    };

    private DriverFactory() {
    }

    public static DriverFactory getInstance() {
        return instance;
    }

    public WebDriver getDriver() // call this method to get the driver object and launch the browser
    {
        return driver.get();
    }
}