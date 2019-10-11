package com.test.autothon.common;

/**
 * @author Rahul_Goyal
 */
public class ReadEnvironmentVariables {

    private static final InheritableThreadLocal<String> myBrowser = new InheritableThreadLocal();
    private static ReadEnvironmentVariables instance = new ReadEnvironmentVariables();

    private static ReadEnvironmentVariables getInstance() {
        return instance;
    }

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
        String browserName = myBrowser.get();
        if (browserName == "" || browserName == null)
            browserName = System.getProperty("browserName", "Chrome");
        return browserName;
    }

    public static void setBrowserName(String browserName) {
        //System.setProperty("browserName", browserName);
        myBrowser.set(browserName);
    }

    public static String getBrowserVersion() {
        return System.getProperty("browserVersion", "");
    }

    public static String getOSPlatform() {
        return System.getProperty("OSPlatform", "").trim();
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

    public static boolean isRunTestsOnRemoteHost() {
        boolean isRunTestsOnRemote = false;
        String isHeadless = System.getProperty("runTestsonRemoteHost", "false");
        if (isHeadless.equalsIgnoreCase("true"))
            isRunTestsOnRemote = true;
        return isRunTestsOnRemote;
    }

    public static String getRemoteHostUrl() {
        return System.getProperty("remoteHostUrl", "").trim();
    }

}
