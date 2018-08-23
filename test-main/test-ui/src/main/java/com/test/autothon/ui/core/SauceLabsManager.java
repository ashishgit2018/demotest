package com.test.autothon.ui.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SauceLabsManager {

    private final static Logger logger = LogManager.getLogger(SauceLabsManager.class);
    private static final String USERNAME = "ashishgit2018";
    private static final String ACCESS_KEY = "4fd284cd-aa85-4686-be10-ae14b5f14624";
    public WebDriver driver;
    public String sessionId;
    private String browser;
    private String version;
    private String os;

    public SauceLabsManager(String browser, String version, String os) {
        this.browser = browser;
        this.version = version;
        this.os = os;
    }

    public void setUpSauceLabsDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (browser.equalsIgnoreCase("chrome"))
            capabilities = DesiredCapabilities.chrome();
        else if (browser.equalsIgnoreCase("firefox"))
            capabilities = DesiredCapabilities.firefox();
        else if (browser.equalsIgnoreCase("ie"))
            capabilities = DesiredCapabilities.internetExplorer();
        else if (browser.equalsIgnoreCase("safari"))
            capabilities = DesiredCapabilities.safari();

        capabilities.setCapability(CapabilityType.BROWSER_VERSION, version);
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, os);
        capabilities.setCapability("name", "Sauce Labs Test");
        logger.info("Running test on Sauce Lab - Browser :" + browser + " \tVersion : " + version + " \tOS : " + os);
        try {
            this.driver = new RemoteWebDriver(
                    new URL("http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub"),
                    capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
    }


    public String getSessionId() {
        return sessionId;
    }

    public WebDriver getSauceLabDriver() {
        return driver;
    }

}