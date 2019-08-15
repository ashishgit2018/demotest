package com.test.autothon.ui.core;

import com.test.autothon.common.ReadPropertiesFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AutomationUIUtils {

    private final static Logger logger = LogManager.getLogger(AutomationUIUtils.class);

    static String tagName;
    static String scrFilePath;
    static String testCaseName;
    static List<String> base64Images = new ArrayList<String>();


    public static String getTestCaseName() {
        return testCaseName;
    }

    public static void setTestCaseName(String tcName) {
        testCaseName = tcName;
    }

    public static String getTagName() {
        return tagName;
    }

    public static void setTagName(String tag) {
        tagName = tag;
    }

    public static String getSrcFilePath() {
        return scrFilePath;
    }

    public static void setSrcFilePath(String filePath) {
        scrFilePath = filePath;
    }

    public static List<String> getBase64Images() {
        return base64Images;
    }

    public static void setBase64Image(String base64Image) {
        base64Images.add(base64Image);
    }

    public static void setBase64ImageToNull() {
        base64Images.clear();
    }

    public static String getDateTimeStamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyymmddhhmmss");
        return format.format(new Date());
    }
}
