package com.test.autothon.auto.core;

import com.test.autothon.common.CustomHtmlReport;
import com.test.autothon.common.FileUtils;
import com.test.autothon.common.ReadPropertiesFile;
import com.test.autothon.ui.core.UIOperations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestAutothonMain2019 extends UIOperations {

    private final static Logger logger = LogManager.getLogger(TestAutothonMain2019.class);

    public void youtubeSearch(String searchText) throws Exception {
        String videoName = getOverlay("<PROPVALUE(youtubeVideoName)>");
        logger.info("Video Name : " + videoName);
        String objPropSearchInputField = ReadPropertiesFile.getPropertyValue("youtube.search.input");
        String objPropSearchIcon = ReadPropertiesFile.getPropertyValue("youtube.search.icon");
        String objPropStepInForumLink = ReadPropertiesFile.getPropertyValue("youtube.stepinforum.link");
        String objPropVideosTab = ReadPropertiesFile.getPropertyValue("youtube.videos.tab");
        String objPropPauseButton = ReadPropertiesFile.getPropertyValue("youtube.pause.button");
        String objPropSettingsButton = ReadPropertiesFile.getPropertyValue("youtube.settings.button");
        String objPropQualityMenuItem = ReadPropertiesFile.getPropertyValue("youtube.quality.menuitem");
        String objProp360pMenuItem = ReadPropertiesFile.getPropertyValue("youtube.360p.menuitem");
        String objPropUpcomingVideosDiv = ReadPropertiesFile.getPropertyValue("youtube.upcoming.video.div");

        if (!videoName.isEmpty()) {
            CustomHtmlReport.addReportStep("Validate the rest call response", "Reponse should not be blank", videoName, "PASS");
        } else {
            CustomHtmlReport.addReportStep("Validate the rest call response", "Reponse should not be blank", videoName, "FAIL");
            throw new Exception("Video name in the response is empty");
        }

        WebElement search = getElement(objPropSearchInputField).get(0);
        search.sendKeys(searchText);
        takeScreenShot();
        waitForSecond(1);
        click(getElement(objPropSearchIcon).get(0));
        waitForSecond(1);
        takeScreenShot();
        getElement(objPropStepInForumLink).get(0).click();
        waitForSecond(2);
        getElement(objPropVideosTab).get(0).click();
        takeScreenShot();
        boolean flag = true;
        int counter = 0;
        while (flag) {
            try {
                waitForVisible("xpath_//a[@title='" + videoName + "']");
                waitForSecond(1);
                // scrollIntoViewbyElementId("xpath_//a[@title='" + videoName + "']");
                takeScreenShot();
                getElement("xpath_//a[@title='" + videoName + "']").get(0).click();
                CustomHtmlReport.addReportStep("Click on the Video Link", "Video link should be identified", videoName, "PASS");
                flag = false;
                waitForSecond(1);
                takeScreenShot();
            } catch (Exception e) {
                System.out.println("Counter : " + counter + " - exception : " + e.getMessage());
                scrollIntoView();
                counter++;
                if (counter > 10) {
                    CustomHtmlReport.addReportStep("Click on the Video Link", "Video link should be identified", "Video Not Found", "FAIL");
                    throw new Exception("Unable to find the Video after 10 attempts,aborting the search");
                }
            }
        }

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

        waitForSecond(1);
        click(getElement(objPropPauseButton).get(0));
        waitForSecond(2);
        click(getElement(objPropSettingsButton).get(0));
        waitForSecond(3);
        try {
            getElement(objPropQualityMenuItem).get(0).click();
        } catch (Exception e) {
            logger.info("Got exception while clicking Quality menu item, re-trying to click");
            waitForSecond(3);
            getElement(objPropQualityMenuItem).get(0).click();
        }
        waitForSecond(2);
        takeScreenShot();
        getElement(objProp360pMenuItem).get(0).click();
        waitForSecond(2);
        click(getElement(objPropSettingsButton).get(0));
        waitForSecond(1);
        takeScreenShot();
        String strQuality = getElement(objPropQualityMenuItem).get(0).getText();
        logger.info("Video Quality = {}", strQuality);
        if (strQuality.contains("360p")) {
            CustomHtmlReport.addReportStep("Validate video quality set to 360p", "360p quality should be set", strQuality, "PASS");
        } else {
            CustomHtmlReport.addReportStep("Validate video quality set to 360p", "360p quality should be set", strQuality, "FAIL");
        }
        waitForSecond(2);
        logger.info("Scrolling down to get the upcoming videos");
        int i = 2;
        while (i > 0) {
            scrollIntoView();
            i--;
        }

        StringBuilder upcomingVideNames = new StringBuilder("");
        waitForSecond(3);
        List<WebElement> upcomingVideosList = getElement(objPropUpcomingVideosDiv);
        String strListItem = "";
        for (WebElement we : upcomingVideosList) {
            strListItem = patternMatching("\n[^\n]*", we.getText());
            logger.info("Upcoming video name : {}", strListItem);
            upcomingVideNames.append("\"").append(strListItem).append("\",");
        }

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
