package com.test.autothon.ui.core;

import com.test.autothon.common.ReadEnvironmentVariables;
import com.test.autothon.common.ReadPropertiesFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Rahul_Goyal
 */
public class SauceLabsManager {

    private final static Logger logger = LogManager.getLogger(SauceLabsManager.class);
    private static final String USERNAME = ReadPropertiesFile.getPropertyValue("sauce.username");
    private static final String ACCESS_KEY = ReadPropertiesFile.getPropertyValue("sauce.accessKey");
    public WebDriver driver;
    public String sessionId;
    private String browser;
    private String browserVersion;
    private String OSPlatformName;
    private String devicePlatformVersion;
    private String devicePlatformName;
    private String deviceAppiumVersion;
    private String deviceName;

    // https://wiki.saucelabs.com/display/DOCS/Platform+Configurator#/
    public SauceLabsManager(String browser) {
        this.browser = browser;
        this.browserVersion = ReadEnvironmentVariables.getBrowserVersion();
        this.OSPlatformName = ReadEnvironmentVariables.getOSPlatform();
        this.devicePlatformVersion = ReadEnvironmentVariables.getDevicePlatformVersion();
        this.devicePlatformName = ReadEnvironmentVariables.getDevicePlatformName();
        this.deviceAppiumVersion = ReadEnvironmentVariables.getDeviceAppiumVersion();
        this.deviceName = ReadEnvironmentVariables.getDeviceName();
    }

    public void setUpSauceLabsDriver() {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        URL url = null;

        try {
            url = new URL("https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub");
            //http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub
        } catch (MalformedURLException e) {
            logger.error("Sauce labs url is not as expected : " + url.getPath());
            e.printStackTrace();
        }

        //Appium Configuration
        if (browser.contains("mobile_")) {
            browser = browser.split("mobile_")[1];
            if (browser.equalsIgnoreCase("Safari"))
                browser = "Safari";
            else
                browser = "Chrome";
            capabilities.setCapability("appiumVersion", deviceAppiumVersion);
            capabilities.setCapability("deviceName", deviceName);
            capabilities.setCapability("deviceOrientation", "portrait");
            capabilities.setCapability("browserName", browser);
            capabilities.setCapability("platformVersion", devicePlatformVersion);
            capabilities.setCapability("platformName", devicePlatformName);
            capabilities.setCapability("name", "BROWSER :" + browser + " PLATFORM_VERSION : " + devicePlatformVersion + " PLATFORM_NAME : " + devicePlatformName
                    + " DEVICE_NAME : " + deviceName + " APPIUM_VERSION : " + deviceAppiumVersion + " ORIENTATION : portrait");

            logger.info("Running tests on Sauce Lab [ Appium Web ] - \nBROWSER :" + browser + " \tPLATFORM_VERSION : " + devicePlatformVersion + " \tPLATFORM_NAME : " + devicePlatformName
                    + " \tDEVICE_NAME : " + deviceName + " \tAPPIUM_VERSION : " + deviceAppiumVersion + " \tORIENTATION : portrait");
        } else {
            if (browser.equalsIgnoreCase("firefox")) {
                capabilities = DesiredCapabilities.firefox();
            } else if (browser.equalsIgnoreCase("ie"))
                capabilities = DesiredCapabilities.internetExplorer();
            else if (browser.equalsIgnoreCase("safari"))
                capabilities = DesiredCapabilities.safari();
            else
                capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("version", browserVersion);
            capabilities.setCapability("platform", OSPlatformName);
            capabilities.setCapability("name", "BROWSER :" + browser + " BROWSER_VERSION : " + browserVersion + " PLATFORM_NAME : " + OSPlatformName);

            logger.info("Running tests on Sauce Lab - \nBROWSER :" + browser + " \tBROWSER_VERSION : " + browserVersion + " \tPLATFORM_NAME : " + OSPlatformName);

        }
        this.driver = new RemoteWebDriver(url, capabilities);
        this.sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
    }


    public String getSessionId() {
        return sessionId;
    }

    public WebDriver getSauceLabDriver() {
        return driver;
    }

}