package com.test.autothon.common;

import java.io.File;

/**
 * @author Rahul_Goyal
 */
public class Constants {

    public final static String env = ReadEnvironmentVariables.getEnvironment();
    public final static String configResourcePath = Thread.currentThread().getContextClassLoader().getResource(env + "/config").getPath();
    public final static String jsonResourcePath = Thread.currentThread().getContextClassLoader().getResource(env + "/json").getPath();
    public final static String jmeterResourcePath = Thread.currentThread().getContextClassLoader().getResource(env + "/jmeter").getPath();
    public final static String tempFileLocation = configResourcePath + "/temp.properties";
    public final static String jmeterHomePath = ReadPropertiesFile.getPropertyValue("jmeter.home");
    public final static File jmeterHome = new File(jmeterHomePath);
    public final static String jmeterlogFilePath = "/target/" + env + "/jmeter_automation.jtl";
    public final static String jmeterHTMLFilePath = "/target/" + env + "/jmeter/";
    public final static String jmeterJMXFilePath = jmeterHomePath + "/" + env + "/jmeter_automation.jmx";

    private Constants() {

    }
}
