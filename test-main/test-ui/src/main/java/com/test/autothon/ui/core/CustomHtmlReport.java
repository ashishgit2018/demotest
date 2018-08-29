package com.test.autothon.ui.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomHtmlReport {

    public StringBuilder customReport;

    public void setHtmlPrefix() {
        String prefix = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <script type=\"text/javascript\">\n" +
                "\tvar myTable= \"<table><tr><td style='width: 100px; color: red;'>Movie ID</td>\";\n" +
                "    myTable+= \"<td style='width: 100px; color: red; text-align: right;'>Movie Name</td>\";\n" +
                "                    myTable+= \"<td style='width: 100px; color: red; text-align: right;'>Wikipedia URL</td>\";\n" +
                "                \tmyTable+=\"<td style='width: 100px; color: red; text-align: right;'>Wikipedia Snapshot</td>\";\n" +
                "                    myTable+=\"<td style='width: 100px; color: red; text-align: right;'>Wikipedia Director</td>\";\n" +
                "                    myTable+=\"<td style='width: 100px; color: red; text-align: right;'>Imdb URL</td>\";\n" +
                "                \tmyTable+=\"<td style='width: 100px; color: red; text-align: right;'>Imdb snapshot</td>\";\n" +
                "                \tmyTable+=\"<td style='width: 100px; color: red; text-align: right;'>Imdb Director</td></tr>\";\n" +
                "                    myTable+=\"<tr><td style='width: 100px;                   '>---------------</td>\";\n" +
                "                    myTable+=\"<td     style='width: 100px; text-align: right;'>---------------</td>\";\n" +
                "                \tmyTable+=\"<td     style='width: 100px; text-align: right;'>---------------</td>\";\n" +
                "                \tmyTable+=\"<td     style='width: 100px; text-align: right;'>---------------</td>\";\n" +
                "                \tmyTable+=\"<td     style='width: 100px; text-align: right;'>---------------</td>\";\n" +
                "                \tmyTable+=\"<td     style='width: 100px; text-align: right;'>---------------</td>\";\n" +
                "                \tmyTable+=\"<td     style='width: 100px; text-align: right;'>---------------</td>\";\n" +
                "                    myTable+=\"<td     style='width: 100px; text-align: right;'>---------------</td></tr>\";\n" +
                "\n" +
                "    var movies={};";
        if (customReport == null)
            customReport = new StringBuilder(prefix);
        customReport.append(prefix);
    }

    public void setHtmlSuffix() {
        String suffix = "\n for (var x in movies)\n" +
                "    {\n" +
                "        myTable+=\"<tr><td style='width: 100px;'> \" + x + \" :</td>\";\n" +
                "        var value = movies[x];\n" +
                "        for (var y in value)\n" +
                "        {\n" +
                "            myTable+=\"<td style='width: 100px; text-align: right;'>\" + value[y] + \"</td>\";\n" +
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
                "     createTableHashmap();\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";
        customReport.append(suffix);

    }

    public void writetoFile() throws IOException {
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
}
