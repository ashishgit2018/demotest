package com.test.autothon.common;

import org.apache.commons.lang.StringEscapeUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomHtmlReport {

    private static InheritableThreadLocal<StringBuilder> customReport = new InheritableThreadLocal<>();
    private static InheritableThreadLocal<Map<Integer, List<String>>> concurrentResult = new InheritableThreadLocal<>();

    public static void initialize() {
        customReport.set(new StringBuilder());
        concurrentResult.set(new HashMap<>());
    }

    private static void setHtmlReportPrefix() {
        String prefix = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "    <h4> Scenario: " + Hooks.scenarioName + "</h4>\n" +
                "    <h4> Computer Name: " + System.getenv("COMPUTERNAME") + "</h4>\n" +
                "    <h4> Date & Time: " + StepDefinition.getDateTimeStamp("dd-MMM-yyyy HH:mm:ss.SSS") + "</h4>\n" +
                "    <h4 style=\"display:inline;\"> Browser: " + ReadEnvironmentVariables.getBrowserName() + "<h5 style=\"display:inline;\">&emsp; &emsp;<i><u>[ Ignore if not a UI test ] </u></i></h5></h4>\n" +
                "    <a style=\"display:inline;\" target=\"_blank\" href=\"./../../.." + ScreenshotUtils.getImgSrcFilePath() + "\"> <h4>screenshot link</h4> </a>\n" +
                "</body>\n" +
                "<head>\n" +
                "    <script type=\"text/javascript\">\n" +
                "var myTable= \"<table><tr style='outline: thin solid'>" +
                "<th style='width: 100px; color: blue ; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'>Step No</th>\";\n" +
                "    myTable+= \"<th style='width: 100px; color: blue; text-align: center ; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'>Step Detail</th>\";\n" +
                "    myTable+= \"<th style='width: 100px; color: blue; text-align: center ; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'>Expected Value</th>\";\n" +
                "    myTable+= \"<th style='width: 100px; color: blue; text-align: center; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'>Actual Value</th>\";\n" +
                "    myTable+=\"<th style='width: 100px; color: blue; text-align: center; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'>Result</th>\";\n" +
                "    var htmlResult={};";
        customReport.get().append(prefix);

    }

    private static void setHtmlReportSuffix() {
        String suffix = "\n for (var x in htmlResult)\n" +
                "    {\n" +
                "        myTable+=\"<tr style='outline: thin solid'>\"\n" +
                "        var value = htmlResult[x];\n" +
                "        for (var y in value)\n" +
                "        {\n" +
                "            var val = value[y].replace(/r_e_p/g,'<br />');\n" +
                "            if (val === \"FAIL\")\n" +
                "            myTable+=\"<td style='width: 100px; text-align: center; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid; background-color: red'>\" + val + \"</td>\";\n" +
                "            else if (val === \"PASS\")\n" +
                "            myTable+=\"<td style='width: 100px; text-align: center; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid; background-color: green'>\" + val + \"</td>\";\n" +
                "            else \n" +
                "            myTable+=\"<td style='width: 100px; text-align: center; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'>\" + val + \"</td>\";\n" +
                "\n" +
                "        }\n" +
                "       myTable+=\"</tr>\"\n" +
                "    }\n" +
                "    myTable+=\"</table>\";\n" +
                "    document.write( myTable);\n" +
                "\t</script>\n" +
                "    <title>Test Execution HTML Report</title><meta charset=\"utf-8\"/>\n" +
                "</head>\n" +
                "</html>";
        customReport.get().append(suffix);
    }

    public static void writeToHtmlReportFile() {
        String scrName = Hooks.scenarioName;
        String folderFormat = StepDefinition.getDateTimeStamp("ddMMMyy");
        String scrFilePath = System.getProperty("user.dir") + "/output/htmlreports/" + folderFormat;
        FileUtils.createFolder(scrFilePath);
        scrFilePath = scrFilePath + "/" + scrName + "_" + ReadEnvironmentVariables.getBrowserName() + "_" + StepDefinition.getDateTimeStamp("HH-mm-ss-SSS") + ".html";

        setHtmlReportPrefix();
        writeColumnValues();
        setHtmlReportSuffix();

        File file = new File(scrFilePath);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(customReport.get().toString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String escapedANSIText(String text) {
        return StringEscapeUtils.escapeJavaScript(text);
    }

    public synchronized static void addReportStep(String stepInfo, String expVal, String actVal, String result) {
        List<String> liResult = new ArrayList<>();
        liResult.add(escapedANSIText(stepInfo));
        liResult.add(escapedANSIText(expVal));
        liResult.add(escapedANSIText(actVal));
        liResult.add(result);
        int stepINo = concurrentResult.get().size() + 1;
        concurrentResult.get().put(stepINo, liResult);
    }

    private static Map<Integer, List<String>> getResultData() {
        return concurrentResult.get();
    }

    private static void writeColumnValues() {
        for (Map.Entry<Integer, List<String>> entry : getResultData().entrySet()) {
            int key = entry.getKey();
            List<String> valueList = entry.getValue();
            customReport.get().append("\n htmlResult['" + key + "']={stepNo:'" + key + "',stepInfo:'" + valueList.get(0).replaceAll("\n", "r_e_p") + "',expValue:'" + valueList.get(1) +
                    "',actValue:'" + valueList.get(2) + "',result:'" + valueList.get(3).toUpperCase() + "'}");
        }
    }

}
