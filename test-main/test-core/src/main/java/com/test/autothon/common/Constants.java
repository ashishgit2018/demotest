package com.test.autothon.common;

/**
 * @author Rahul_Goyal
 */
public class Constants {

    public final static String configResourcePath = Thread.currentThread().getContextClassLoader().getResource("config").getPath();
    public final static String jsonResourcePath = Thread.currentThread().getContextClassLoader().getResource("json").getPath();
    public final static String jmeterResourcePath = Thread.currentThread().getContextClassLoader().getResource("jmeter").getPath();
    public final static String tempFileLocation = configResourcePath + "/temp.properties";

    private Constants() {

    }
}
