package com.test.autothon.auto.core;

import com.test.autothon.common.Constants;
import com.test.autothon.common.CustomHtmlReport;
import com.test.autothon.common.ReadPropertiesFile;
import com.test.autothon.ui.core.UIOperations;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestDemo2019 extends UIOperations {

    public static Map<String, Map<String, String>> movieDeatils = new HashMap<>();
    private static Map<String, String> movieInfo = new HashMap<>();

    public void getAllValuesFromPropFile(String propFile) {
        Properties prop = ReadPropertiesFile.loadPropertiesfile(Constants.configResourcePath + "/" + propFile);
        Set<String> keys = prop.stringPropertyNames();
        for (String key : keys) {
            String movieName = prop.getProperty(key);
            movieDeatils.put(movieName, null);
        }
    }

    public void searchMoviesInGoogle() {
        launchURL(ReadPropertiesFile.getPropertyValue("google.url.new"));
        String searchElementGoogle = ReadPropertiesFile.getPropertyValue("google.search.textbox");
        String searchButtonGoogle = ReadPropertiesFile.getPropertyValue("google.submit.button");
        String wikiLinkElementGoogle = ReadPropertiesFile.getPropertyValue("link.wiki");
        for (Map.Entry<String, Map<String, String>> entry : movieDeatils.entrySet()) {
            String movieName = entry.getKey() + "+ wikipedia";
            enterText(searchElementGoogle, movieName);
            click(searchButtonGoogle);
            String wikiLinkUrl = getTextFromElementAttribute(wikiLinkElementGoogle, "href");
            movieInfo.put("wikilink", wikiLinkUrl);
            movieDeatils.put(movieName, movieInfo);
        }
    }

    public void searchAndAssertTest() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        for (Map.Entry<String, Map<String, String>> entry : movieDeatils.entrySet()) {
            String wikiLink = entry.getValue().get("wikilink");

            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    getDirectorNameAndIMDBLink(wikiLink);
                }
            });

        }
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertResult();
    }

    private void getDirectorNameAndIMDBLink(String wikiLink) {
        launchURL(wikiLink);
        String wikiDirName = getDirectorNameFromWiki();
        String imdbLink = getIMDBLinkFromWiki();
        String imdbDirName = getDirectorNameFromIMDB(imdbLink);
        movieInfo.put("wikidir", wikiDirName);
        movieInfo.put("imdblink", imdbLink);
        movieInfo.put("imdbdir", imdbDirName);
    }

    private String getDirectorNameFromWiki() {
        String dirElementWiki = ReadPropertiesFile.getPropertyValue("wiki.DirectedBy.value");
        String wikiDirName = getTextFromElement(dirElementWiki);
        return wikiDirName;
    }

    private String getIMDBLinkFromWiki() {
        String imdbElementWiki = ReadPropertiesFile.getPropertyValue("wiki.imdb.url");
        String imdbLink = getTextFromElement(imdbElementWiki);
        return imdbLink;
    }

    private String getDirectorNameFromIMDB(String url) {
        if (url == "" || url.isEmpty() || url == null)
            return null;
        launchURL(url);
        String dirElementIMDB = ReadPropertiesFile.getPropertyValue("imdb.DirectedBy.value");
        String imdbDirName = getTextFromElement(dirElementIMDB);
        return imdbDirName;
    }

    private void assertResult() {
        for (Map.Entry<String, Map<String, String>> entry : movieDeatils.entrySet()) {
            String movieName = entry.getKey();
            String wikiDir = entry.getValue().get("wikidir");
            String imdbUrl = entry.getValue().get("imdblink");
            String imdbDir = entry.getValue().get("imdbdir");

            if (wikiDir.isEmpty() || wikiDir == "" || wikiDir == null)
                CustomHtmlReport.addReportStep("validate for movie name: " + movieName, "Wiki Director value is blank", "", "FAIL");

            else if (imdbUrl == null)
                CustomHtmlReport.addReportStep("validate for movie name: " + movieName, "IMDB url value is blank", "", "FAIL");

            else if (imdbDir.isEmpty() || imdbDir == "" || imdbDir == null)
                CustomHtmlReport.addReportStep("validate for movie name: " + movieName, "IMDB Director value is blank", "", "FAIL");

            else if (wikiDir.equalsIgnoreCase(imdbDir))
                CustomHtmlReport.addReportStep("validate for movie name: " + movieName, wikiDir, imdbDir, "Pass");
            else
                CustomHtmlReport.addReportStep("validate for movie name: " + movieName, wikiDir, imdbDir, "FAIL");

        }
    }

}
