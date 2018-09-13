package com.test.autothon.ui.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class CustomHtmlReport {

    public static StringBuilder customReport;

    public static void setHtmlPrefix() {
        String prefix = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <script type=\"text/javascript\">\n" +
                "\tvar myTable= \"<table><tr style='outline: thin solid'>" +
                "<th style='width: 100px; color: blue ; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'>Movie ID</th>\";\n" +
                "    myTable+= \"<th style='width: 100px; color: blue; text-align: center ; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'>Movie Name</th>\";\n" +
                "                    myTable+= \"<th style='width: 100px; color: blue; text-align: center; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'>Wikipedia URL</th>\";\n" +
                "                    myTable+=\"<th style='width: 100px; color: blue; text-align: center; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'>Wikipedia Director</th>\";\n" +
                "                    myTable+=\"<th style='width: 100px; color: blue; text-align: center; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'>Imdb URL</th>\";\n" +
                "                \tmyTable+=\"<th style='width: 100px; color: blue; text-align: center; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'>Imdb Director</th>\";\n" +
                "                \tmyTable+=\"<th style='width: 100px; color: blue; text-align: center; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'>Result</th></tr>\";\n" +
                "\n" +
                "    var movies={};";
        if (customReport == null)
            customReport = new StringBuilder(prefix);

    }

    public static void setHtmlSuffix() {
        String suffix = "\n for (var x in movies)\n" +
                "    {\n" +
                "        myTable+=\"<tr style='outline: thin solid'><td style='width: 100px; text-align: center; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'> \" + x + \" :</td>\";\n" +
                "        var value = movies[x];\n" +
                "        for (var y in value)\n" +
                "        {\n" +
                "            myTable+=\"<td style='width: 100px; text-align: center; border-top: thin solid; border-bottom: thin solid; border-left: thin solid; border-right: thin solid;'>\" + value[y] + \"</td>\";\n" +
                "\n" +
                "        }\n" +
                "    }\n" +
                "    myTable+=\"</table>\";\n" +
                "    document.write( myTable);\n" +
                "\t</script>\n" +
                "    <title>abc</title><meta charset=\"utf-8\"/>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <script>\n" +
                //"     createTableHashmap();\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";
        customReport.append(suffix);

    }

    public static void writetoFile() throws IOException {
        String folderFormat = new SimpleDateFormat("ddMMMyy").format(new Date());
        String scrFilePath = System.getProperty("user.dir") + "/output/" + folderFormat;

        File file = new File(scrFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        scrFilePath = scrFilePath + "/" + "CustomHtmlReport_" + AutomationUIUtils.getDateTimeStamp() + ".html";
        File file2 = new File(scrFilePath);
        FileWriter fileWriter = new FileWriter(file2);
        fileWriter.write(customReport.toString());
        fileWriter.flush();
        fileWriter.close();
    }

    public static void writeColumnValues(ConcurrentMap<String, List<String>> concurrentMap) {
        for (Map.Entry<String, List<String>> entry : concurrentMap.entrySet()) {
            String key = entry.getKey();
            List<String> valueList = entry.getValue();
            customReport.append("\n movies['" + key + "']={movieName:'" + valueList.get(0) + "',wikiurl:'" + valueList.get(1) +
                    "',expDirName:'" + valueList.get(2) + "',imdblink:'" + valueList.get(3) + "',imdbDirect:'" + valueList.get(4) + "'," +
                    "result:'" + valueList.get(5) + "'}");
        }
    }

}
