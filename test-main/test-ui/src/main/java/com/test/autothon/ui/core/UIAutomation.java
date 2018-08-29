package com.test.autothon.ui.core;

import com.test.autothon.common.ReadPropertiesFile;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UIAutomation extends UIOperations {

    WebDriver driver;
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

