package com.test.autothon.ui.core;

import com.test.autothon.common.Constants;
import com.test.autothon.common.ReadPropertiesFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UIAutomation extends UIOperations {
    private final static Logger logger = LogManager.getLogger(ReadPropertiesFile.class);

    WebDriver driver;
    Properties propMovieName;
    HashMap<String, List<String>> movieDetails = new HashMap<String, List<String>>();
    int movieNo = 0;
    String movieName = "";
    String wikiLink = "";
    ConcurrentMap<String, List<String>> concurrentResult = new ConcurrentHashMap();

    public void getWebDriver(String broswer) throws Exception {
        AutoWebDriver autoDriver = new AutoWebDriver(broswer);
        driver = autoDriver.getDriver();
        setDriver(driver);
    }

    public void setTestCaseName(String testCaseName) {
        AutomationUIUtils.setTestCaseName(testCaseName);
    }

    public void readMovieNames(String propertyFile) {
        String propFileName = Constants.configResourcePath + "/movieName.properties";
        propMovieName = ReadPropertiesFile.loadPropertiesfile(propFileName);
        System.out.println(propMovieName);
    }

    public void searchAllMovies() throws InterruptedException {
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

    public void searchAllMovies_1() {
        String movieNum = "";
        String movieName = "";
        for (int intCnt = 0; intCnt <= 200; intCnt++) {
            try {
                movieNum = "movie" + intCnt;
                movieName = propMovieName.getProperty("movie" + intCnt);
                searchMovie(movieNum, movieName);
            } catch (Exception e) {
                System.out.println("EOF");
                break;
            }
        }
        logger.info(movieDetails);
        runWikiTest(movieDetails);
    }

    public void searchMovie(String movieNo, String movieName) throws InterruptedException {
        List<String> nameUrl = new ArrayList<String>();
        String searchTextBox = "id_lst-ib";
        String urlsPath = "xpath_(//*[@class='iUh30'])[1]";
        String movieNameW = "";
        String movieURL = "";
        movieNameW = movieName + " movie wikipedia";
        enterText(searchTextBox, movieNameW);
        driver.findElement(By.id("lst-ib")).sendKeys(Keys.ENTER);
        //String clickMovie = "partiallinktext_" + movieName + " - Wikipedia";
        try {
            //movieURL = getHref(clickMovie);
            movieURL = getHref(urlsPath);
        } catch (Exception e) {

        } finally {
            Thread.sleep(5000);
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

    public void assertMovie(int movieNo, String movieName, String wikiLink) {
        String data = getWikiDirectorAndImdbLink(wikiLink);
        List<String> details = new ArrayList<>();
        String expDirName = data.split(" - ")[0].trim();
        String imdblink = data.split(" - ")[1].trim();
        String actDirName = getDirNameFromIMDB(imdblink);
        actDirName = actDirName.trim();
        String result = "fail";
        if (actDirName.equalsIgnoreCase(actDirName)) {
            result = "pass";
        }
        details.add(movieName);
        details.add(wikiLink);
        details.add(expDirName);
        details.add(imdblink);
        details.add(actDirName);
        details.add(result);
        details.add(AutomationUIUtils.getSrcFilePath());
    }

    public String getDirNameFromIMDB(String imdbLink) {
        launchURL(imdbLink);
        takeScreenShot(driver);
        String actDirName = getTextFromElement(ReadPropertiesFile.getPropertyValue("imdb.director.name"));
        return actDirName;
    }

    public String getWikiDirectorAndImdbLink(String wikiLink) {
        launchURL(wikiLink);
        takeScreenShot(driver);
        String expDirName = getTextFromElement(ReadPropertiesFile.getPropertyValue("wiki.director.name"));
        String imdbLink = getTextFromElementAttribute(ReadPropertiesFile.getPropertyValue("wiki.imdb.url"), "href");
        return expDirName + " - " + imdbLink;
    }

    public void runWikiTest(Map<String, List<String>> movieDeatils) {
        for (String key : movieDeatils.keySet()) {
            movieNo = Integer.parseInt(key);

            List<String> movieDetail = movieDeatils.get(key);
            movieName = movieDetail.get(1);
            wikiLink = movieDetail.get(2);
        }
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                assertMovie(movieNo, movieName, wikiLink);
            }
        });
    }
}

