package com.test.autothon.ui.core;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class UIHooks {

    private final static Logger logger = Logger.getLogger(UIHooks.class);

    @After
    public void getScenarioName(Scenario scenario) throws IOException {
        String tagName = "";
        Collection<String> tags = scenario.getSourceTagNames();
        for (String tag : tags) {
            if (tag.contains("SN")) {
                tagName = tag;
            }
        }
        if (AutomationUIUtils.getBase64Images().size() == 0)
            return;

        String folderFormat = new SimpleDateFormat("ddMMMyy").format(new Date());
        String scrFilePath = System.getProperty("user.dir") + "/output/" + folderFormat;
        File file = new File(scrFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        scrFilePath = scrFilePath + "/" + tagName + "_" + AutomationUIUtils.getTestCaseName() + "_" + AutomationUIUtils.getDateTimeStamp() + ".html";

        file = new File(scrFilePath);
        FileOutputStream io = new FileOutputStream(file);
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
