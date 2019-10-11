package com.test.autothon.auto.core;

import com.test.autothon.common.Constants;
import com.test.autothon.common.CustomHtmlReport;
import com.test.autothon.common.ReadPropertiesFile;
import com.test.autothon.ui.core.DriverFactory;
import com.test.autothon.ui.core.UIOperations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Autothon2018 extends UIOperations {

    private final static Logger logger = LogManager.getLogger(Autothon2018.class);

    private static HashMap<String, List<String>> movieDetails = new HashMap<String, List<String>>();
    Properties propMovieName;


    public void readMovieNames(String propertyFile) {
        String propFileName = Constants.configResourcePath + "/" + propertyFile;
        propMovieName = ReadPropertiesFile.loadPropertiesfile(propFileName);
    }

    public void searchAllMovies() throws InterruptedException, IOException {

        String movieNum = "";
        String movieName = "";
        Set<Object> keySetName = propMovieName.keySet();
        Iterator itr = keySetName.iterator();

        while (itr.hasNext()) {
            Object obj = itr.next();
            movieNum = obj.toString();
            movieName = (String) propMovieName.get(obj);
            searchMovie(movieNum, movieName);
        }
        logger.info(movieDetails);
        runWikiTest(movieDetails);

    }

    public void searchMovie(String movieNo, String movieName) throws InterruptedException {
        List<String> nameUrl = new ArrayList<String>();
        String searchTextBox = ReadPropertiesFile.getPropertyValue("google.search.textbox");
        String urlsPath = ReadPropertiesFile.getPropertyValue("google.wiki.path");
        String movieNameW = "";
        String movieURL = "";
        movieNameW = movieName + " movie wikipedia";
        enterText(searchTextBox, movieNameW);
        getElement(searchTextBox).get(0).sendKeys(Keys.ENTER);
        waitForSecond(2);
        takeScreenShot();
        try {
            movieURL = getTextFromElement(urlsPath);
        } catch (Exception e) {

        } finally {
            nameUrl.add(movieName);
            nameUrl.add(movieURL);
            movieDetails.put(movieNo, nameUrl);
            clearData(searchTextBox);
        }
    }

    public void searchItem(String item) throws Exception {
        String searchTextBox = "id_twotabsearchtextbox";
        String searchButton = "xpath_//input[contains(@class,'nav-input')]";
        enterText(searchTextBox, item);
        click(searchButton);
    }

    public void selectItem(String ItemName) throws InterruptedException {
        String itemLocator = "linkText_" + ItemName;
        click(itemLocator);
        waitForSecond(5);
        switchToWindow("FIND_WINDOW_BY_TITLE_SUBSTRING", ItemName);
    }

    public void assertMovie(String movieNo, String movieName, String wikiLink) {
        String data = getWikiDirectorAndImdbLink(wikiLink);
        String expDirName = data.split(" - ")[0].trim();
        String imdblink = data.split(" - ")[1].trim();
        String actDirName = getDirNameFromIMDB(imdblink);
        actDirName = actDirName.trim();
        String result = "pass";

        if (!actDirName.equalsIgnoreCase(expDirName))
            result = "fail";

        String stepInfo = "Validate the director name in IMDB and Wiki Link \n" + movieNo + "\n Movie Name: " + movieName +
                "\n Wiki Link: " + wikiLink + "\n IMDBLink: " + imdblink;
        CustomHtmlReport.addReportStep(stepInfo, expDirName, actDirName, result);
        DriverFactory.getInstance().tearBrowser();
    }

    public String getDirNameFromIMDB(String imdbLink) {
        navigateToUrl(imdbLink);
        takeScreenShot();
        String actDirName = getTextFromElement(ReadPropertiesFile.getPropertyValue("imdb.director.name"));
        return actDirName;
    }

    public String getWikiDirectorAndImdbLink(String wikiLink) {
        launchURL(wikiLink);
        takeScreenShot();
        String expDirName = getTextFromElement(ReadPropertiesFile.getPropertyValue("wiki.director.name"));
        String imdbLink = getTextFromElementAttribute(ReadPropertiesFile.getPropertyValue("wiki.imdb.url"), "href");
        return expDirName + " - " + imdbLink;
    }

    public void runWikiTest(Map<String, List<String>> movieDeatils) {

        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        for (String key : movieDeatils.keySet()) {

            String movieNo = key;
            List<String> movieDetail = movieDeatils.get(key);
            String movieName = movieDetail.get(0);
            String wikiLink = movieDetail.get(1);
            logger.info("Movie no: " + movieNo + " Movie Name: " + movieName +
                    "Wiki Link: " + wikiLink);

            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    assertMovie(movieNo, movieName, wikiLink);
                }
            });

        }
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}