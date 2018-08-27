package com.test.autothon.ui.core;

import com.test.autothon.common.FileUtils;
import com.test.autothon.common.ReadEnvironmentVariables;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AutoWebDriver {

    private final static Logger logger = LogManager.getLogger(AutoWebDriver.class);
    private static WebDriver driver;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                tearBrowser();
            }
        });
    }

    public AutoWebDriver() {
        createWebDriver(null);
    }

    AutoWebDriver(String browser) {
        createWebDriver(browser);
    }

    public static void tearBrowser() {
        if (driver != null) {
            logger.info("closing existing running instance of webdriver...");
            driver.quit();
            driver = null;
        }
    }

    private void createWebDriver(String browser) {
        //just closing any existing driver instance, if any
        tearBrowser();

        if (browser == null || browser == "")
            browser = ReadEnvironmentVariables.getBrowserName();

        logger.info("Initializing webdriver...");
        browser = browser.trim().toLowerCase();

        if (ReadEnvironmentVariables.runOnSauceBrowser().equalsIgnoreCase("true")) {
            logger.info("Tests will be executed on Sauce labs ....");
            SauceLabsManager sauceLabsManager = new SauceLabsManager(browser);
            sauceLabsManager.setUpSauceLabsDriver();
            driver = sauceLabsManager.getSauceLabDriver();
        } else {
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
                    try {
                        mobileChromeDriver();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    logger.info("invalid browser name");
                    try {
                        throw new Exception("Invalid browser name");
                    } catch (Exception e) {
                        logger.error("Invalid Browser...Please provide correct browser name" + e);
                    }
            }
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                tearBrowser();
            }
        });

        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    private void ieDriver() {
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);

        File file;
        file = FileUtils.getResourceAsFile(this, "drivers/IEDriverServer.exe");
        System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
        if (null == driver) {
            logger.info("Intializing IE browser");
            driver = new InternetExplorerDriver(capabilities);
        }
    }

    private void chromeDriver() {
        File file;
        file = FileUtils.getResourceAsFile(this, "drivers/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        if (null == driver) {
            logger.info("Intializing chrome browser");
            driver = new ChromeDriver();
        }
    }

    private void fireFoxDriver() {
        File file;
        file = FileUtils.getResourceAsFile(this, "drivers/geckodriver.exe");
        System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
        if (null == driver) {
            logger.info("Intializing firefox browser");
            driver = new FirefoxDriver();
        }
    }

    private void mobileChromeDriver() throws MalformedURLException {
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
