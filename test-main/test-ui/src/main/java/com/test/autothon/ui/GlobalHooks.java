package com.test.autothon.ui;

import cucumber.api.Scenario;
import cucumber.api.java.After;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

public class GlobalHooks {
    @After
    public void getScenarioName(Scenario scenario) throws IOException {
        String tagName = "";
        Collection<String> tags = scenario.getSourceTagNames();
        for (String tag : tags) {
            if (tag.contains("SN")) {
                tagName = tag;
            }
        }
        String scrFilePath = System.getProperty("user.dir") + "/output";
        File file = new File(scrFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        scrFilePath = System.getProperty("user.dir") + "/output/" + tagName + "_" + AutomationUIUtils.getTestCaseName() + "_" + AutomationUIUtils.getDateTimeStamp() + ".html";

        File file2 = new File(scrFilePath);
        FileOutputStream io = new FileOutputStream(file2);
        io.write("<!DOCTYPE html><html><head></head><body width=\"600px\">".getBytes());
        for (String base64Image : AutomationUIUtils.getBase64Images()) {
            io.write("<img src=\"data:image/png;base64,".getBytes());
            io.write(base64Image.getBytes());
            io.write("\">".getBytes());
        }
        io.write("</body></html>".getBytes());
        io.close();
        AutomationUIUtils.setBase64ImageToNull();
    }
}
