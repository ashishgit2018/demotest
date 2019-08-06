package com.test.autothon.ui.core;

import com.test.autothon.common.ScreenshotUtils;
import com.test.autothon.common.StepDefinition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Set;

public class UIOperations extends StepDefinition {

    private final static Logger logger = LogManager.getLogger(UIOperations.class);

    public void launchURL(String url) {
        logger.info("launching url : " + url);
        DriverFactory.getInstance().getDriver().get(url);
    }

    public void navigateToUrl(String url) {
        DriverFactory.getInstance().getDriver().navigate().to(url);
    }

    public String getCurrentUrl() {
        return DriverFactory.getInstance().getDriver().getCurrentUrl();
    }

    public String getPageTitle() {
        return DriverFactory.getInstance().getDriver().getTitle().trim();
    }

    public void checkPageTitle(String expectedPageTitle) {
        String pageTitle = getPageTitle();
        Assert.assertTrue("Page title mismatch Expected :" + expectedPageTitle + " \tActual :" + pageTitle, pageTitle.contains(expectedPageTitle));

    }

    public String getPageSource() {
        return DriverFactory.getInstance().getDriver().getPageSource();
    }

    public void checkPageSourceContainsText(String expectedText) {
        String pageSource = getPageSource();
        Assert.assertTrue("Page does not conatins Text :" + expectedText, pageSource.contains(expectedText));
    }

    public void checkPageSourceDoesNotContainsText(String expectedText) {
        String pageSource = getPageSource();
        Assert.assertFalse("Page conatins Text :" + expectedText, pageSource.contains(expectedText));
    }

    public boolean elementContainsAttributeValue(String elemLocator, String attribute, String value) {
        WebElement webElement = getElement(elemLocator).get(0);
        return elementContainsAttributeValue(webElement, attribute, value);
    }

    public boolean elementContainsAttributeValue(WebElement webElement, String attribute, String value) {
        boolean result = false;
        String actVal = webElement.getAttribute(attribute);
        if (actVal != null) {
            if (actVal.contains(value)) {
                result = true;
                return true;
            }
        }
        logger.error("WebElement :" + webElement + " Attribute : " + attribute + " does not contains expected value" +
                "Expected :" + value + " Actual :" + actVal, result);
        return result;
    }

    public void checkElementContainsText(String elemLocator, String expectedValue) {
        WebElement webElement = getElement(elemLocator).get(0);
        checkElementContainsText(webElement, expectedValue);
    }

    public void checkElementContainsText(WebElement webElement, String expectedValue) {
        waitForVisible(webElement);
        String actualValue = webElement.getText().trim();
        Assert.assertTrue("WebElement :" + webElement + " does not contains expected text" +
                "Expected :" + expectedValue + " Actual :" + actualValue, actualValue.contains(expectedValue));
    }

    public void enterText(String elemLocator, String setValue) {
        WebElement elem = getElement(elemLocator).get(0);
        enterText(elem, setValue);
    }

    public void enterText(WebElement elem, String setValue) {
        logger.info("Entering text :" + setValue + " for element :" + elem);
        waitForVisible(elem);
        elem.sendKeys(setValue);
        takeScreenShot();
    }

    public void clearData(String elemLocator) {
        WebElement webElement = getElement(elemLocator).get(0);
        clearData(webElement);
    }

    public String getTextFromElement(String elemLocator) {
        WebElement webElement = getElement(elemLocator).get(0);
        String text = getTextFromElement(webElement);
        return text;
    }

    public String getTextFromElement(WebElement element) {
        String text = element.getText();
        return text;
    }

    public String getTextFromElementAttribute(String elemLocator, String attribute) {
        WebElement webElement = getElement(elemLocator).get(0);
        String text = getTextFromElementAttribute(webElement, attribute);
        return text;
    }

    public String getTextFromElementAttribute(WebElement element, String attribute) {
        String text = element.getAttribute(attribute);
        return text;
    }

    public void clearData(WebElement webElement) {
        logger.info("Clearing text " + "for element :" + webElement);
        waitForVisible(webElement);
        webElement.clear();
    }

    public void setViewToFrameId(String frameId) {
        logger.info("Setting frame view to " + frameId);
        DriverFactory.getInstance().getDriver().switchTo().frame(frameId);
    }

    public void setViewToFrameId(int frameId) {
        logger.info("Setting frame view to " + frameId);
        DriverFactory.getInstance().getDriver().switchTo().frame(frameId);
    }

    public void setViewToFrameByElementLocator(String elementLocator) {
        WebElement webElement = getElement(elementLocator).get(0);
        setViewToFrameByElementLocator(webElement);
    }

    public void setViewToFrameByElementLocator(WebElement webElement) {
        logger.info("Setting frame view to " + webElement);
        DriverFactory.getInstance().getDriver().switchTo().frame(webElement);
    }

    public void setViewToDefaultFrame() {
        logger.info("Setting to default frame");
        DriverFactory.getInstance().getDriver().switchTo().defaultContent();
    }

    public void selectValue(String elemLocator, String selecionMethod, String setValue) {
        WebElement elem = getElement(elemLocator).get(0);
        selectValue(elem, selecionMethod, setValue);
    }

    public void selectValue(WebElement elem, String selecionMethod, String setValue) {
        waitForVisible(elem);
        waitForElementToBeClickable(elem);
        Select select = new Select(elem);
        if (selecionMethod.equalsIgnoreCase("ByValue")) {
            select.selectByValue(setValue);
        } else if (selecionMethod.equalsIgnoreCase("ByIndex")) {
            select.selectByIndex(Integer.parseInt(setValue));
        } else if (selecionMethod.equalsIgnoreCase("ByVisibleText")) {
            select.selectByVisibleText(setValue);
        } else {
            logger.error("Please put valid select method");
        }
        takeScreenShot();
    }

    public void CheckBox(String elemLocator, boolean op) throws Exception {
        WebElement elem = getElement(elemLocator).get(0);
        CheckBox(elem, op);
    }

    public void CheckBox(WebElement elem, boolean op) throws Exception {
        waitForVisible(elem);
        if (!elem.isSelected() == op) {
            elem.click();
        }
        takeScreenShot();
    }

    public void selectRadioButton(String elemLocator, String buttonName) throws Exception {
        List<WebElement> elems = getElement(elemLocator);
        for (WebElement elem : elems) {
            if (elementContainsAttributeValue(elem, "name", buttonName)) {
                click(elem);
            }
        }
        takeScreenShot();
    }

    public void click(String elemLocator) {
        WebElement elem = getElement(elemLocator).get(0);
        waitForElementToBeClickable(elem);
        elem.click();
    }

    public void click(WebElement elem) {
        waitForElementToBeClickable(elem);
        elem.click();
    }

    public void waitForVisible(String elemLocator) {
        WebElement element = getElement(elemLocator).get(0);
        waitForVisible(element);
    }

    public void waitForVisible(WebElement element) {
        logger.info("Waiting for element " + element + " to be visible");
        WebDriverWait webDriverWait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 20, 5);
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeClickable(String elemLocator) {
        WebElement elem = getElement(elemLocator).get(0);
        waitForElementToBeClickable(elem);
    }

    public void waitForElementToBeClickable(WebElement elem) {
        logger.info("Waiting for element " + elem + " to be clickable");
        WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 20);
        wait.until(ExpectedConditions.elementToBeClickable(elem));
    }

    public List<WebElement> getElement(String elemLocator) {
        String[] element = elemLocator.split("_", 2);
        List<WebElement> elem = null;
        logger.info("Locator Type: " + element[0] + " \tLocator:" + element[1]);
        switch (element[0].toLowerCase().trim()) {
            case "xpath":
                elem = DriverFactory.getInstance().getDriver().findElements(By.xpath(element[1]));
                break;
            case "id":
                elem = DriverFactory.getInstance().getDriver().findElements(By.id(element[1]));
                break;
            case "css":
                elem = DriverFactory.getInstance().getDriver().findElements(By.cssSelector(element[1]));
                break;
            case "name":
                elem = DriverFactory.getInstance().getDriver().findElements(By.name(element[1]));
                break;
            case "class":
                elem = DriverFactory.getInstance().getDriver().findElements(By.className(element[1]));
                break;
            case "linktext":
                elem = DriverFactory.getInstance().getDriver().findElements(By.linkText(element[1]));
                break;
            case "tagname":
                elem = DriverFactory.getInstance().getDriver().findElements(By.tagName(element[1]));
                break;
            case "partiallinktext":
                elem = DriverFactory.getInstance().getDriver().findElements(By.partialLinkText(element[1]));
                break;
            default:
                logger.error("Please enter valid locator method; This should be like LOCATORNAME_EXPRESSION; for eg. xpath_//name['login']");
        }
        return elem;
    }

    public void takeScreenShot() {
        TakesScreenshot scrShot = ((TakesScreenshot) DriverFactory.getInstance().getDriver());
        String SrcFile = scrShot.getScreenshotAs(OutputType.BASE64);
        ScreenshotUtils.setBase64Image(SrcFile);
    }

    public void acceptAlertModalDialog() {
        try {
            Alert alert = DriverFactory.getInstance().getDriver().switchTo().alert();
            logger.info("Accepting alert");
            alert.accept();
            logger.info("Accepted alert");
        } catch (NoAlertPresentException ex) {
            logger.error("No Alert Dialog present");
        }
    }

    public void dimissAlertModalDialog() {
        try {
            Alert alert = DriverFactory.getInstance().getDriver().switchTo().alert();
            logger.info("Dismissing alert");
            alert.dismiss();
            logger.info("Dismissed alert");
        } catch (NoAlertPresentException ex) {
            logger.error("No Alert Dialog present");
        }
    }

    public String getTextAlertModalDialog() {
        try {
            Alert alert = DriverFactory.getInstance().getDriver().switchTo().alert();
            logger.info("Getting text in alert box");
            return alert.getText();
        } catch (NoAlertPresentException ex) {
            logger.error("No Alert Dialog present");
        }
        return null;
    }

    public void switchToWindow(String findWindowByCriteria, String value) {
        String newWindowHandle;

        logger.info("Switching to window with property " + findWindowByCriteria
                + " = " + value);
        newWindowHandle = findWindow(findWindowByCriteria, value);
        Assert.assertNotNull("Unable to find a window with property "
                + findWindowByCriteria + " = " + value, newWindowHandle);
        DriverFactory.getInstance().getDriver().switchTo().window(newWindowHandle);
        takeScreenShot();
    }

    public void closeWindow(String findWindowByCriteria, String value) {
        String startingWindowHandle;
        String newWindowHandle;

        startingWindowHandle = DriverFactory.getInstance().getDriver().getWindowHandle();
        newWindowHandle = findWindow(findWindowByCriteria, value);

        Assert.assertNotNull("Unable to find a window with property "
                + findWindowByCriteria + " = " + value, newWindowHandle);

        Assert.assertNotEquals(
                "Attempting to close the currently active window.  Switch to another window before closing this window.  "
                        + "Current Selenium window handle = "
                        + startingWindowHandle
                        + " handle for window being closed =  "
                        + newWindowHandle, startingWindowHandle,
                newWindowHandle);

        DriverFactory.getInstance().getDriver().switchTo().window(newWindowHandle);
        DriverFactory.getInstance().getDriver().close();
        DriverFactory.getInstance().getDriver().switchTo().window(startingWindowHandle);
    }

    protected String findWindow(String findWindowByCriteria, String value) {
        Set<String> handles = DriverFactory.getInstance().getDriver().getWindowHandles();
        // In order to evaluate the properties on the open windows, Selenium
        // requires that you switch to each window
        // you're evaluating - keep track of the original window
        // so we can switch back to it.
        String startingWindowHandle = DriverFactory.getInstance().getDriver().getWindowHandle();
        String matchingWindowHandle = null;
        boolean found = false;

        logger.info("Searching for window with property "
                + findWindowByCriteria + " = " + value + " ...");

        for (String handle : handles) {
            DriverFactory.getInstance().getDriver().switchTo().window(handle);

            if (findWindowByCriteria.equals("FIND_WINDOW_BY_TITLE")
                    && getPageTitle().equals(value)) {
                found = true;
                break;
            } else if (findWindowByCriteria
                    .equals("FIND_WINDOW_BY_URL")
                    && getCurrentUrl().equals(value)) {
                found = true;
                break;
            } else if (findWindowByCriteria
                    .equals("FIND_WINDOW_BY_TITLE_SUBSTRING")
                    && getPageTitle().contains(value)) {
                found = true;
                break;
            } else if (findWindowByCriteria
                    .equals("FIND_WINDOW_CONTAINING_ELEMENT")
                    && (getElement(value).size() > 0)) {
                found = true;
                break;
            }
        }

        if (found) {
            matchingWindowHandle = DriverFactory.getInstance().getDriver().getWindowHandle();
            logger.info("Found window with property " + findWindowByCriteria
                    + " = " + value + ".  Window handle ID = "
                    + matchingWindowHandle);
        } else {
            logger.info("Unable to find window with property "
                    + findWindowByCriteria + " = " + value);
        }

        // Switch back to the original window.
        DriverFactory.getInstance().getDriver().switchTo().window(startingWindowHandle);

        return matchingWindowHandle;
    }

    public void scrollIntoView() {
        ((JavascriptExecutor) this).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void scrollIntoViewbyElementId(String elementLocator) {
        WebElement element = getElement(elementLocator).get(0);
        scrollIntoViewbyElementId(element);
    }

    public void scrollIntoViewbyElementId(WebElement element) {
        logger.info("scroll element into view by Id : " + element);
        ((JavascriptExecutor) this).executeScript(
                "arguments[0].scrollIntoView(true);", element);
    }

    public void moveToElemet(String elementLocator) {
        WebElement webElement = getElement(elementLocator).get(0);
        moveToElemet(webElement);
    }

    public void moveToElemet(WebElement webElement) {
        logger.info("Moving to element :" + webElement);
        Actions action = new Actions(DriverFactory.getInstance().getDriver());
        action.moveToElement(webElement);
    }

    public void doubleClick(String elementLocator) {
        WebElement webElement = getElement(elementLocator).get(0);
        doubleClick(webElement);
    }

    public void doubleClick(WebElement webElement) {
        Actions actions = new Actions(DriverFactory.getInstance().getDriver());
        logger.info("Double click on element :" + webElement);
        actions.doubleClick(webElement).perform();
    }

    public void dragAndDrop(String elementLocatorSource, String elementLocatorTarget) {
        WebElement webElementSource = getElement(elementLocatorSource).get(0);
        WebElement webElementTarget = getElement(elementLocatorTarget).get(0);
        dragAndDrop(webElementSource, webElementTarget);
    }

    public void dragAndDrop(WebElement webElementSource, WebElement webElementTarget) {
        Actions actions = new Actions(DriverFactory.getInstance().getDriver());
        logger.info("Drag element from :" + webElementSource + " to :" + webElementTarget);
        actions.dragAndDrop(webElementSource, webElementTarget).perform();
    }

    public void javaScriptClick(WebElement webElement) {
        try {
            if (webElement.isDisplayed() && webElement.isEnabled()) {
                logger.info("Element is enabled and is present, clicking using Java script click");
                ((JavascriptExecutor) DriverFactory.getInstance().getDriver()).executeScript("arguments[0].click();", webElement);
            } else {
                logger.error("Unable to click using Java script click");
            }
        } catch (Exception e) {
            logger.error("Unable to click using Java script click");
        }
    }

    public boolean isLinkBroken(String url) {
        int responseCode = 200;
        HttpURLConnection connection = null;
        boolean isBroken = false;
        logger.info("Checking if the link [" + url + "] is broken or not");

        if (url == null || url.isEmpty()) {
            logger.info("Link url is balnk");
            return true;
        }
        try {
            connection = (HttpURLConnection) (new URL(url).openConnection());
            connection.setRequestMethod("HEAD");
            connection.connect();
            responseCode = connection.getResponseCode();
            logger.info("Response code is: " + responseCode);
            if (responseCode >= 400)
                isBroken = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        logger.info("Link broken: " + isBroken);
        return isBroken;
    }


}
