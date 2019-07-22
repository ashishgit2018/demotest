package com.test.autothon.ui.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AutomationUIUtils {

    private final static Logger logger = LogManager.getLogger(AutomationUIUtils.class);

    static String tagName;
    static String scrFilePath;
    static String testCaseName;
    static List<String> base64Images = new ArrayList<String>();

    public static String getImgSrcFilePath() {
        return scrFilePath;
    }

    public static void setImgSrcFilePath(String filePath) {
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

}
