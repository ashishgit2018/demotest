package com.test.autothon.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CustomHtmlReport {

    private static StringBuilder customReport;
    private static ConcurrentMap<Integer, List<String>> concurrentResult = new ConcurrentHashMap();
    private static int stepNo = 1;

    public static void setHtmlReportPrefix() {
        String prefix = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "    <h4> Scenario: " + Hooks.scenarioName + "</h4>\n" +
                "    <h4> Computer Name: " + System.getenv("COMPUTERNAME") + "</h4>\n" +
                "    <h4> Date & Time: " + StepDefinition.getDateTimeStamp("dd-MMM-yyyy HH:mm:ss.SSS") + "</h4>\n" +
                "</body>\n" +
                "<head>\n" +
                "    <script type=\"text/javascript\">\n" +
                "\tvar myTable= \"<table><tr style='outline: thin solid'>" +
                "<th style='width: 100px; color: blue ; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'>Step No</th>\";\n" +
                "    myTable+= \"<th style='width: 100px; color: blue; text-align: center ; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'>Step Detail</th>\";\n" +
                "    myTable+= \"<th style='width: 100px; color: blue; text-align: center ; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'>Expected Value</th>\";\n" +
                "    myTable+= \"<th style='width: 100px; color: blue; text-align: center; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'>Actual Value</th>\";\n" +
                "    myTable+=\"<th style='width: 100px; color: blue; text-align: center; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'>Result</th>\";\n" +
                "    var htmlResult={};";
        if (customReport == null)
            customReport = new StringBuilder(prefix);

    }

    public static void setHtmlReportSuffix() {
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
                "    <title>" + Hooks.scenarioName + "</title><meta charset=\"utf-8\"/>\n" +
                "</head>\n" +
                "</html>";
        customReport.append(suffix);
    }

    public static void writetoHtmlReportFile() {
        String scrName = Hooks.scenarioName;
        String folderFormat = StepDefinition.getDateTimeStamp("ddMMMyy");
        String scrFilePath = System.getProperty("user.dir") + "/output/" + folderFormat;

        FileUtils.createFolder(scrFilePath);

        scrFilePath = scrFilePath + "/" + "HTML_Report_" + scrName + "_" + StepDefinition.getDateTimeStamp("yyyyMMMddhhmmss") + ".html";

        File file2 = new File(scrFilePath);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file2);
            fileWriter.write(customReport.toString());
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

    public static void addReportStep(String stepInfo, String expVal, String actVal, String result) {
        List<String> liResult = new ArrayList<>();
        liResult.add(stepInfo);
        liResult.add(expVal);
        liResult.add(actVal);
        liResult.add(result);
        concurrentResult.put(stepNo, liResult);
        stepNo++;
    }

    public static void writeColumnValues() {
        for (Map.Entry<Integer, List<String>> entry : concurrentResult.entrySet()) {
            int key = entry.getKey();
            List<String> valueList = entry.getValue();
            customReport.append("\n htmlResult['" + key + "']={stepNo:'" + key + "',stepInfo:'" + valueList.get(0).replaceAll("\n", "r_e_p") + "',expValue:'" + valueList.get(1) +
                    "',actValue:'" + valueList.get(2) + "',result:'" + valueList.get(3).toUpperCase() + "'}");
        }
    }

}
