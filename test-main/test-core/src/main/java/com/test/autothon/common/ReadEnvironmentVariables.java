package com.test.autothon.common;

/**
 * @author Rahul_Goyal
 */
public final class ReadEnvironmentVariables {

    public static String getEnvironment() {
        return System.getProperty("env", "dev");
    }

    // https://wiki.saucelabs.com/display/DOCS/Platform+Configurator#/

    public static String getRunOnSauceBrowser() {
        return System.getProperty("runOnSauce", "false");
    }

    public static String getRunOnHeadlessBrowser() {
        return System.getProperty("runHeadless", "false");
    }

    public static String getBrowserName() {
        return System.getProperty("browserName", "Chrome");
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
