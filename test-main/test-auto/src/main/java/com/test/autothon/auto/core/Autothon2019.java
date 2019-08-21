package com.test.autothon.auto.core;

import com.test.autothon.common.CustomHtmlReport;
import com.test.autothon.common.FileUtils;
import com.test.autothon.ui.core.UIOperations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Autothon2019 extends UIOperations {

    private final static Logger logger = LogManager.getLogger(Autothon2019.class);

    public void searchStepIn() {
        String videoName = getOverlay("<PROPVALUE(youtubeVideoName)>");

        if (!videoName.isEmpty()) {
            CustomHtmlReport.addReportStep("Validate the rest call response", "Reponse should not be blank", videoName, "PASS");
        } else {
            CustomHtmlReport.addReportStep("Validate the rest call response", "Reponse should not be blank", videoName, "FAIL");
        }
        WebElement search = getElement("id_search").get(0);

        search.sendKeys("step-inforum");
        takeScreenShot();
        waitForSecond(1);
        click(getElement("id_search-icon-legacy").get(0));
        waitForSecond(1);
        takeScreenShot();
        getElement("xpath_//h3[contains(.,'STeP-IN Forum')]").get(0).click();
        waitForSecond(2);
        getElement("xpath_//div[@id='tabsContent']/paper-tab[2]").get(0).click();
        takeScreenShot();
        boolean flag = true;
        while (flag) {
            try {
                waitForVisible("xpath_//a[contains(.,'" + videoName + "')]");
                waitForSecond(1);
                //scrollIntoView();
                takeScreenShot();
                waitForVisible("xpath_//a[contains(.,'" + videoName + "')]").click();
                flag = false;
                waitForSecond(1);
                takeScreenShot();
                logger.info("Took screen shot");
            } catch (Exception e) {
                System.out.println("exception : " + e.getMessage());
                scrollIntoView();
            }
        }
        // System.out.println("test");

//        try {
//            waitForVisible("xpath_//button[@class='ytp-ad-skip-button ytp-button']").click();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        waitForSecond(1);
//        try {
//            waitForVisible("xpath_//paper-button[@id='button']/*[contains(.,'Skip trial')]").click();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
        waitForSecond(2);
        click(getElement("xpath_//button[@title='Pause (k)']").get(0));
        waitForSecond(2);
        click(getElement("xpath_//button[contains(@class,'settings-button')]").get(0));
        waitForSecond(3);
        try {
            getElement("xpath_//div[@class='ytp-menuitem']/div[contains(.,'Quality')]/following-sibling::div").get(0).click();
        } catch (Exception e) {
            waitForSecond(3);
            getElement("xpath_//div[@class='ytp-menuitem']/div[contains(.,'Quality')]/following-sibling::div").get(0).click();
        }
        waitForSecond(2);
        getElement("xpath_//div[@class='ytp-menuitem']/div[contains(.,'360p')]").get(0).click();
        waitForSecond(3);
        int i = 2;
        while (i > 0) {
            scrollIntoView();
            i--;
        }
        List<String> upcomingVideosValues = new ArrayList<>();
        StringBuilder upcomingVideNames = new StringBuilder("");
        waitForSecond(3);
        List<WebElement> upcomingVideosList = getElement("xpath_//ytd-compact-video-renderer");

        for (WebElement we : upcomingVideosList) {
            upcomingVideNames.append("\"").append(patternMatching("\n[^\n]*", we.getText())).append("\",");
        }
        System.out.println("values : " + upcomingVideNames);
        if (upcomingVideNames.length() > 0) {
            CustomHtmlReport.addReportStep("Validate upcoming videos names", "Should not be blank", upcomingVideNames.toString(), "PASS");
        } else {
            CustomHtmlReport.addReportStep("Validate upcoming videos names", "Should not be blank", upcomingVideNames.toString(), "FAIL");
        }
        FileUtils.writeToTempFile("allVideoList", upcomingVideNames.toString().substring(0, upcomingVideNames.toString().length() - 1));

    }

    public String patternMatching(String pattern, String value) {
        String val = "";
        Pattern p = Pattern.compile(pattern);//. represents single character
        Matcher m = p.matcher(value);
        if (m.find()) {
            val = m.group().replaceAll("\n", "");
        }
        return val;
    }

}
