package com.test.autothon.common;

/**
 * @author Rahul_Goyal
 */
public class Constants {

    public final static String env = ReadEnvironmentVariables.getEnvironment();
    public final static String configResourcePath = Thread.currentThread().getContextClassLoader().getResource(env + "/config").getPath();
    public final static String jsonResourcePath = Thread.currentThread().getContextClassLoader().getResource(env + "/json").getPath();
    public final static String jmeterResourcePath = Thread.currentThread().getContextClassLoader().getResource(env + "/jmeter").getPath();
    public final static String tempFileLocation = configResourcePath + "/temp_" + ReadEnvironmentVariables.getBrowserName() + ".properties";

    private Constants() {

    }
}
