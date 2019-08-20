package com.test.autothon.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScreenshotUtils {

    private final static Logger logger = LogManager.getLogger(ScreenshotUtils.class);

    private static InheritableThreadLocal<String> scrFilePath = new InheritableThreadLocal<String>() {
        @Override
        public String initialValue() {
            return null;
        }
    };
    private static InheritableThreadLocal<List<String>> base64Images = new InheritableThreadLocal<List<String>>() {
        @Override
        public List<String> initialValue() {
            return new ArrayList<>();
        }
    };

    public static void initialize() {
        scrFilePath.set(null);
        base64Images.set(new ArrayList<>());
    }

    public static String getImgSrcFilePath() {
        return scrFilePath.get();
    }

    private static void setImgSrcFilePath(String filePath) {
        scrFilePath.set(filePath);
    }

    private static List<String> getBase64Images() {
        return base64Images.get();
    }

    synchronized public static void setBase64Image(String base64Image) {
        base64Images.get().add(base64Image);
    }

    private static void setBase64ImageToNull() {
        base64Images.get().clear();
    }

    public static void writeImagesToHTMLFile() throws IOException {
        String scName = Hooks.scenarioName;

        if (getBase64Images().size() == 0)
            return;

        String folderFormat = StepDefinition.getDateTimeStamp("ddMMMyy");
        String screenShotFolderName = "/output/screenshots/" + folderFormat;
        String scrFolderPath = System.getProperty("user.dir") + screenShotFolderName;
        FileUtils.createFolder(scrFolderPath);
        String screenShotFileName = screenShotFolderName + "/" + scName + "_" + ReadEnvironmentVariables.getBrowserName() + "_" + StepDefinition.getDateTimeStamp("HH-mm-ss-SSS") + ".html";
        String scrFilePath = System.getProperty("user.dir") + screenShotFileName;

        setImgSrcFilePath(screenShotFileName);

        File file = new File(scrFilePath);
        FileOutputStream io = new FileOutputStream(file);
        io.write("<!DOCTYPE html><html><head></head><body>".getBytes());
        boolean isMobile = ReadEnvironmentVariables.getBrowserName().toLowerCase().contains("mobile");
        String imgSize = "\" style=\"width:1000px;height:600px;\"";
        if (isMobile)
            imgSize = "\" style=\"width:350px;height:550px;\"";
        for (String base64Image : getBase64Images()) {
            io.write("<img border=\"1\" src=\"data:image/png;base64,".getBytes());
            io.write(base64Image.getBytes());
            io.write((imgSize + "> </br></br>").getBytes());
        }
        io.write("</body></html>".getBytes());
        io.close();
        setBase64ImageToNull();
    }

}
