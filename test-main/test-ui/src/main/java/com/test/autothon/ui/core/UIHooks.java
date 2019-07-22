package com.test.autothon.ui.core;

import com.test.autothon.common.FileUtils;
import com.test.autothon.common.Hooks;
import com.test.autothon.common.StepDefinition;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UIHooks {

    private final static Logger logger = LogManager.getLogger(UIHooks.class);

    @After
    public void writeImagesToHTMLFile(Scenario scenario) throws IOException {
        String scName = Hooks.scenarioName;

        if (AutomationUIUtils.getBase64Images().size() == 0)
            return;

        String folderFormat = new SimpleDateFormat("ddMMMyy").format(new Date());
        String scrFilePath = System.getProperty("user.dir") + "/output/" + folderFormat;

        FileUtils.createFolder(scrFilePath);

        scrFilePath = scrFilePath + "/" + scName + "_" + StepDefinition.getDateTimeStamp("yyyyMMMddhhmmss") + ".html";

        AutomationUIUtils.setImgSrcFilePath(scrFilePath);

        File file = new File(scrFilePath);
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
