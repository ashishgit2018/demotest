package com.test.autothon.auto.core;

import com.test.autothon.common.Constants;
import com.test.autothon.common.CustomHtmlReport;
import com.test.autothon.common.FileUtils;
import com.test.autothon.common.StepDefinition;

import java.io.File;

public class TestAutothonRest2019 extends StepDefinition {

    public void executeTheMultiPartFormDataReq(String url, String fileName) {
        String jsonParent = Constants.jsonResourcePath;
        String fullJsonPath = jsonParent + "/" + fileName;
        File file = new File(fullJsonPath);
        fullJsonPath = file.getAbsolutePath().replaceAll("\\\\", "/");
        String cmd = "curl -X POST " + url + " -H 'Content-Type:multipart/form-data' -F \"file=@" + fullJsonPath + "\"";
        String op = executeCMDCommand(cmd);
        op = op.replace("% Total    % Received % Xferd  Average Speed   Time    Time     Time  Current", "");
        FileUtils.writeToTempFile("uploadResult", op);
        if (!op.isEmpty()) {
            CustomHtmlReport.addReportStep("Validate file upload", "File should be uploaded", op, "PASS");
        } else {
            CustomHtmlReport.addReportStep("Validate file upload", "File should be uploaded", op, "FAILs");
        }
    }
}
