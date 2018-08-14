package com.test.autothon.ui.core;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AutoWebDriver {

    private final static Logger logger = Logger.getLogger(AutoWebDriver.class);
    private static WebDriver driver;

    AutoWebDriver(String browser) throws Exception {
        //just closing any existing driver instance, if any
        tearBrowser();

        logger.info("Initializing webdriver...");
        browser = browser.trim().toLowerCase();
        if (browser == null)
            browser = "chrome";

        switch (browser) {
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
                tearBrowser();
            }
        });
    }

    public void tearBrowser() {
        if (driver != null) {
            logger.info("closing existing running instance of webdriver...");
            driver.close();
            driver.quit();
            driver = null;
        }
    }

    protected void ieDriver() {
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);

        File file;
        try {
            file = new File(AutoWebDriver.class.getResource("/drivers/IEDriverServer.exe").toURI());
            System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
            if (null == driver) {
                logger.info("Intializing IE browser");
                driver = new InternetExplorerDriver(capabilities);
            }
        } catch (URISyntaxException e) {
            logger.error("IE driver Resource not found \n" + e);
        }
    }

    protected void chromeDriver() {
        try {
            File file = new File(AutoWebDriver.class.getResource("/drivers/chromedriver.exe").toURI());
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
            if (null == driver) {
                logger.info("Intializing chrome browser");
                driver = new ChromeDriver();
            }
        } catch (URISyntaxException e) {
            logger.error("Chrome driver Resource not found \n" + e);
        }
    }

    protected void fireFoxDriver() {
        File file;
        try {
            file = new File(AutoWebDriver.class.getResource("/drivers/geckodriver.exe").toURI());
            System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
            if (null == driver) {
                logger.info("Intializing firefox browser");
                driver = new FirefoxDriver();
            }
        } catch (URISyntaxException e) {
            logger.error("Firefox driver Resource not found \n" + e);

        }
    }

    protected void mobileChromeDriver() throws MalformedURLException {
        DesiredCapabilities cap = DesiredCapabilities.android();
        cap.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);
        //cap.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "My_Device");
        cap.setCapability(MobileCapabilityType.VERSION, "4.2.2");
        URL url = new URL("http://0.0.0.0:4723/wd/hub");
        if (null == driver) {
            logger.info("Intializing chrome browser for mobile");
            driver = new AndroidDriver(url, cap);
        }
    }

    protected WebDriver getDriver() {
        return driver;
    }
}
