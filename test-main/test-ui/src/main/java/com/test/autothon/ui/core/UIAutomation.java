package com.test.autothon.ui.core;

import com.test.autothon.common.Constants;
import com.test.autothon.common.ReadPropertiesFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class UIAutomation extends UIOperations {
    private final static Logger logger = LogManager.getLogger(ReadPropertiesFile.class);

    WebDriver driver;
    Properties propMovieName;
    HashMap<String, List<String>> movieDetails = new HashMap<String, List<String>>();
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

    public void searchAllMovies() {
        String movieNum = "";
        String movieName = "";
        for (int intCnt = 0; intCnt <= 5; intCnt++) {
            try {
                movieNum = "movie" + intCnt;
                movieName = propMovieName.getProperty("movie" + intCnt);
                searchMovie(movieNum, movieName);
            } catch (Exception e) {
                System.out.println("EOF");
                break;
            }
        }
        System.out.println(movieDetails);
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
        String clickMovie = "partiallinktext_" + movieName + " - Wikipedia";
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
}
