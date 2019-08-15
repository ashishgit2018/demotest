package com.test.autothon.ui.core;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.test.autothon.common.FileUtils;
import com.test.autothon.common.ReadEnvironmentVariables;
import com.test.autothon.common.ReadPropertiesFile;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
            driver.quit();
            driver = null;
        }
    }

    private void createWebDriver() {
        //just closing any existing driver instance, if any
        tearBrowser();
        String browser = ReadEnvironmentVariables.getBrowserName();
        logger.info("Initializing WebDriver...");
        browser = browser.trim().toLowerCase();

        if (ReadEnvironmentVariables.getRunOnSauceBrowser().equalsIgnoreCase("true")) {
            logger.info("Tests will be executed on Sauce labs --- Browser : " + browser);
            SauceLabsManager sauceLabsManager = new SauceLabsManager(browser);
            sauceLabsManager.setUpSauceLabsDriver();
            driver = sauceLabsManager.getSauceLabDriver();
        /*} else if (ReadEnvironmentVariables.getRunOnHeadlessBrowser().equalsIgnoreCase("true")) {
            logger.info("Tests will be executed on Headless browser");
            ghostPhantomJSDriver();*/
        } else {
            logger.info("Tests will be executed on Local browser --- Browser :" + browser);
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
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);

        File file;
        file = FileUtils.getResourceAsFile(this, "drivers/IEDriverServer.exe", ".exe");
        System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
        if (null == driver) {
            logger.info("Intializing IE browser");
            driver = new InternetExplorerDriver(capabilities);
        }
    }

    private void chromeDriver() {
        if (null == driver) {

            File file = FileUtils.getResourceAsFile(this, "drivers/chromedriver.exe", ".exe");
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

            if (ReadEnvironmentVariables.getRunOnHeadlessBrowser().equalsIgnoreCase("true")) {
                logger.info("Intializing Headless chrome browser");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("headless");
                chromeOptions.addArguments("window-size=1400,600");
                driver = new ChromeDriver(chromeOptions);
            } else {
                logger.info("Intializing chrome browser");
                driver = new ChromeDriver();
            }
        }
    }

    private void fireFoxDriver() {
        if (null == driver) {

            File file = FileUtils.getResourceAsFile(this, "drivers/geckodriver.exe", ".exe");
            System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());

            if (ReadEnvironmentVariables.getRunOnHeadlessBrowser().equalsIgnoreCase("true")) {
                logger.info("Intializing Headless Firefox browser");
                FirefoxBinary firefoxBinary = new FirefoxBinary();
                firefoxBinary.addCommandLineOptions("--headless");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setBinary(firefoxBinary);
                driver = new FirefoxDriver(firefoxOptions);
            } else {
                logger.info("Intializing Firefox browser");
                driver = new FirefoxDriver();
            }
        }
    }

    /*private void ghostPhantomJSDriver(){
        if (null == driver){
            logger.info("Initializing the Ghost PhantomJS driver");
            File file = FileUtils.getResourceAsFile(this, "drivers/phantomjs.exe", ".exe");
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setJavascriptEnabled(true);
            caps.setCapability("takesScreenshot", true);
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {"--web-security=no", "--ignore-ssl-errors=yes"});
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, file.getAbsolutePath());
            driver = new PhantomJSDriver(caps);
        }
    }*/

    private void htmlUnitDriver(String browser) {
        if (null == driver) {
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
    }

    @SuppressWarnings("rawtypes")
    private void mobileChromeDriver() {
        DesiredCapabilities cap = DesiredCapabilities.android();
        cap.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);
        //cap.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, ReadPropertiesFile.getPropertyValue("Appium_Platform_Name"));
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, ReadPropertiesFile.getPropertyValue("Appium_Device_Name"));
        cap.setCapability(MobileCapabilityType.VERSION, ReadPropertiesFile.getPropertyValue("Appium_Version"));
        cap.setCapability(MobileCapabilityType.UDID, ReadPropertiesFile.getPropertyValue("Appium_Device"));
        URL url = null;
        try {
            url = new URL(ReadPropertiesFile.getPropertyValue("Appium_Hub_Url"));
        } catch (MalformedURLException e) {
            logger.info("Mobile driver url is not correct\n" + e);
        }
        if (null == driver) {
            logger.info("Intializing Chrome Browser for Mobile");
            driver = new RemoteWebDriver(url, cap);
        }
    }

    protected WebDriver getDriver() {
        return driver;
    }
}
