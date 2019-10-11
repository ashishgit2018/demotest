package com.test.autothon.ui.core;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.test.autothon.common.FileUtils;
import com.test.autothon.common.ReadEnvironmentVariables;
import com.test.autothon.common.ReadPropertiesFile;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AutoWebDriver {

    private final static Logger logger = LogManager.getLogger(AutoWebDriver.class);
    private WebDriver driver;

    public AutoWebDriver() {
        createWebDriver();
    }

    public void tearBrowser() {
        if (driver != null) {
            logger.info("closing existing running instance of webdriver...");
            try {
                driver.close();
                driver.quit();
            } catch (Exception e) {
                logger.warn(e);
            }
            driver = null;
        }
    }

    private void createWebDriver() {
        String browser = ReadEnvironmentVariables.getBrowserName();
        logger.info("Initializing WebDriver...");
        browser = browser.trim().toLowerCase();

        if (ReadEnvironmentVariables.getRunOnSauceBrowser().equalsIgnoreCase("true")) {
            logger.info("Tests will be executed on Sauce labs --- Browser : " + browser);
            SauceLabsManager sauceLabsManager = new SauceLabsManager(browser);
            sauceLabsManager.setUpSauceLabsDriver();
            driver = sauceLabsManager.getSauceLabDriver();
        } else {
            logger.info("Browser used for testing will be --- :" + browser);
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
        if (!browser.contains("mobile_")) {
            try {
                driver.manage().window().maximize();
            } catch (UnsupportedCommandException e) {
                logger.error("Driver does not support maximise");
            } catch (UnsupportedOperationException e) {
                logger.error("Dsriver does not support maximizes");
            }
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    private void ieDriver() {
        if (null != driver)
            return;

        if (ReadEnvironmentVariables.isRunTestsOnRemoteHost()) {
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            createRemoteDriverWithCapabilities(capabilities);
            return;
        }

        File file = FileUtils.getResourceAsFile(this, "drivers/IEDriverServer.exe", ".exe");
        System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
        if (null == driver) {
            logger.info("Initializing IE browser");
            driver = new InternetExplorerDriver();
        }
    }

    private void chromeDriver() {
        if (null != driver)
            return;

        logger.info("Initializing Chrome browser - isHeadless :  " + ReadEnvironmentVariables.isHeadlessBrowser());

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setHeadless(ReadEnvironmentVariables.isHeadlessBrowser());
            chromeOptions.addArguments("start-maximized"); // open Browser in maximized mode
            chromeOptions.addArguments("disable-infobars"); // disabling infobars
            chromeOptions.addArguments("--disable-extensions"); // disabling extensions
            chromeOptions.addArguments("--disable-gpu"); // applicable to windows os only
            chromeOptions.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
            chromeOptions.addArguments("--no-sandbox"); // Bypass OS security model

        if (ReadEnvironmentVariables.isRunTestsOnRemoteHost()) {
            DesiredCapabilities capabilities = new DesiredCapabilities(chromeOptions);
            createRemoteDriverWithCapabilities(capabilities);
            return;
        }

        File file = FileUtils.getResourceAsFile(this, "drivers/chromedriver.exe", ".exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
            driver = new ChromeDriver(chromeOptions);

    }

    private void fireFoxDriver() {
        if (null != driver)
            return;

        logger.info("Initializing Firefox browser - isHeadless :  " + ReadEnvironmentVariables.isHeadlessBrowser());

        FirefoxBinary firefoxBinary = new FirefoxBinary();
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        if (ReadEnvironmentVariables.isHeadlessBrowser()) {
                firefoxBinary.addCommandLineOptions("--headless");
                firefoxOptions.setBinary(firefoxBinary);
        }

        if (ReadEnvironmentVariables.isRunTestsOnRemoteHost()) {
            DesiredCapabilities capabilities = new DesiredCapabilities(firefoxOptions);
            createRemoteDriverWithCapabilities(capabilities);
            return;
        }

        File file = FileUtils.getResourceAsFile(this, "drivers/geckodriver.exe", ".exe");
        System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        driver = new FirefoxDriver(firefoxOptions);
        return;

    }

    private void htmlUnitDriver(String browser) {
        if (null != driver)
            return;

            logger.info("Initializing HTMLUnitDriver " + browser + " browser");
            if (browser.equalsIgnoreCase("chrome"))
                driver = new HtmlUnitDriver(BrowserVersion.CHROME);
            else if (browser.equalsIgnoreCase("firefox"))
                driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_52);
            else if (browser.equalsIgnoreCase("ie"))
                driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER);
            else if (browser.contains("mobile_")) {
                logger.info("Headless browser not supported for Mobile browsers...falling back to GUI based driver");
                mobileChromeDriver();
            }
    }

    @SuppressWarnings("rawtypes")
    private void mobileChromeDriver() {
        if (null != driver)
            return;

        DesiredCapabilities cap = DesiredCapabilities.android();
        cap.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);
        cap.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, ReadPropertiesFile.getPropertyValue("Appium_Platform_Name"));
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, ReadPropertiesFile.getPropertyValue("Appium_Device_Name"));
        cap.setCapability(MobileCapabilityType.VERSION, ReadPropertiesFile.getPropertyValue("Appium_Version"));
        cap.setCapability(MobileCapabilityType.UDID, ReadPropertiesFile.getPropertyValue("Appium_Device"));

        if (ReadEnvironmentVariables.isRunTestsOnRemoteHost()) {
            String remoteHostURL = ReadEnvironmentVariables.getRemoteHostUrl();
            try {
                driver = new RemoteWebDriver(new URL(remoteHostURL), cap);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return;
        }

        File file = FileUtils.getResourceAsFile(this, "drivers/chromedriver.exe", ".exe");
        cap.setCapability("chromedriverExecutable", file.getAbsolutePath());
        try {
            driver = new AndroidDriver(new URL(ReadPropertiesFile.getPropertyValue("Appium_Hub_Url")), cap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private void createRemoteDriverWithCapabilities(DesiredCapabilities capabilities) {
        String OS = ReadEnvironmentVariables.getOSPlatform().trim();
        String browserVersion = ReadEnvironmentVariables.getBrowserVersion().trim();
        String remoteHostURL = ReadEnvironmentVariables.getRemoteHostUrl();
        if (!OS.equals(""))
            capabilities.setCapability(CapabilityType.PLATFORM_NAME, OS);
        if (!browserVersion.equals(""))
            capabilities.setCapability(CapabilityType.BROWSER_VERSION, browserVersion);
        logger.info("Running Tests on Remote Host : " + remoteHostURL + ", Browser Version : " + browserVersion + ", OS : " + OS);
        try {
            driver = new RemoteWebDriver(new URL(remoteHostURL), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    protected WebDriver getDriver() {
        return driver;
    }
}
