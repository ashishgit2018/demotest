package com.test.autothon.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Rahul_Goyal
 */
public class ReadEnvironmentVariables {

    private final static Logger logger = LogManager.getLogger(ReadEnvironmentVariables.class);
    private ThreadLocal myThreadLocal = new ThreadLocal();

    public static String getEnvironment() {
        return System.getProperty("env", "dev");
    }

    // https://wiki.saucelabs.com/display/DOCS/Platform+Configurator#/

    public static String getRunOnSauceBrowser() {
        return System.getProperty("runOnSauce", "false");
    }

    public static boolean isHeadlessBrowser() {
        boolean isBoolHeadless = false;
        String isHeadless = System.getProperty("runHeadless", "false");
        if (isHeadless.equalsIgnoreCase("true"))
            isBoolHeadless = true;
        return isBoolHeadless;
    }

    public static String getBrowserName() {
        //return System.getProperty("browserName", "Chrome");
        return (String) new ReadEnvironmentVariables().myThreadLocal.get();
    }

    public static void setBrowserName(String browserName) {
        logger.info("Setting environment variable browserName as: " + browserName);
        //System.setProperty("browserName", browserName);
        new ReadEnvironmentVariables().myThreadLocal.set(browserName);
    }

    public static String getBrowserVersion() {
        return System.getProperty("browserVersion", "latest");
    }

    public static String getOSPlatform() {
        return System.getProperty("OSPlatform", "Windows 10");
    }

    public static String getDevicePlatformName() {
        return System.getProperty("devicePlatformName", "Android");
    }

    public static String getDevicePlatformVersion() {
        return System.getProperty("devidePlatformVersion", "6.0");
    }

    public static String getDeviceAppiumVersion() {
        return System.getProperty("deviceAppiumVersion", "1.8.1");
    }

    public static String getDeviceName() {
        return System.getProperty("deviceName", "Android Emulator");
    }

}
