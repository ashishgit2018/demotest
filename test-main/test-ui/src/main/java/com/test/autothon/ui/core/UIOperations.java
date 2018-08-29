package com.test.autothon.ui.core;

import com.test.autothon.common.StepDefinition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

public class UIOperations extends StepDefinition {

    private final static Logger logger = LogManager.getLogger(UIOperations.class);

    WebDriver driver;

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public void launchURL(String url) {
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle().trim();
    }

    public void checkPageTitle(String expectedPageTitle) {
        String pageTitle = getPageTitle();
        Assert.assertTrue("Page title mismatch Expected :" + expectedPageTitle + " \tActual :" + pageTitle, pageTitle.contains(expectedPageTitle));

    }

    public String getPageSource() {
        return driver.getPageSource();
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
        takeScreenShot(driver);
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
        ;
        return text;
    }

    public void clearData(WebElement webElement) {
        logger.info("Clearing text " + "for element :" + webElement);
        waitForVisible(webElement);
        webElement.clear();
    }

    public void setViewToFrameId(String frameId) {
        logger.info("Setting frame view to " + frameId);
        driver.switchTo().frame(frameId);
    }

    public void setViewToFrameId(int frameId) {
        logger.info("Setting frame view to " + frameId);
        driver.switchTo().frame(frameId);
    }

    public void setViewToFrameByElementLocator(String elementLocator) {
        WebElement webElement = getElement(elementLocator).get(0);
        setViewToFrameByElementLocator(webElement);
    }

    public void setViewToFrameByElementLocator(WebElement webElement) {
        logger.info("Setting frame view to " + webElement);
        driver.switchTo().frame(webElement);
    }

    public void setViewToDefaultFrame() {
        logger.info("Setting to default frame");
        driver.switchTo().defaultContent();
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
            System.out.println("Please put valid select method");
        }
        takeScreenShot(driver);
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
        takeScreenShot(driver);
    }

    public void selectRadioButton(String elemLocator, String buttonName) throws Exception {
        List<WebElement> elems = getElement(elemLocator);
        for (WebElement elem : elems) {
            if (elementContainsAttributeValue(elem, "name", buttonName)) {
                click(elem);
            }
        }
        takeScreenShot(driver);
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
        WebDriverWait webDriverWait = new WebDriverWait(driver, 20, 5);
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeClickable(String elemLocator) {
        WebElement elem = getElement(elemLocator).get(0);
        waitForElementToBeClickable(elem);
    }

    public void waitForElementToBeClickable(WebElement elem) {
        logger.info("Waiting for element " + elem + " to be clickable");
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(elem));
    }

    public List<WebElement> getElement(String elemLocator) {
        String element[] = elemLocator.split("_", 2);
        List<WebElement> elem = null;
        logger.info("Locator Type: " + element[0] + " \tLocator:" + element[1]);
        switch (element[0].toLowerCase().trim()) {
            case "xpath":
                elem = driver.findElements(By.xpath(element[1]));
                break;
            case "id":
                elem = driver.findElements(By.id(element[1]));
                break;
            case "css":
                elem = driver.findElements(By.cssSelector(element[1]));
                break;
            case "name":
                elem = driver.findElements(By.name(element[1]));
                break;
            case "class":
                elem = driver.findElements(By.className(element[1]));
                break;
            case "linktext":
                elem = driver.findElements(By.linkText(element[1]));
                break;
            case "tagname":
                elem = driver.findElements(By.tagName(element[1]));
                break;
            case "partiallinktext":
                elem = driver.findElements(By.partialLinkText(element[1]));
                break;
            default:
                logger.error("Please enter valid locator method; This should be like LOCATORNAME_EXPRESSION; for eg. xpath_//name['login']");
        }
        return elem;
    }

    public void takeScreenShot(WebDriver driver) {
        TakesScreenshot scrShot = ((TakesScreenshot) driver);
        String SrcFile = scrShot.getScreenshotAs(OutputType.BASE64);
        AutomationUIUtils.setBase64Image(SrcFile);
    }

    public void acceptAlertModalDialog() {
        try {
            Alert alert = driver.switchTo().alert();
            logger.info("Accepting alert");
            alert.accept();
            logger.info("Accepted alert");
        } catch (NoAlertPresentException ex) {
            logger.error("No Alert Dialog present");
        }
    }

    public void dimissAlertModalDialog() {
        try {
            Alert alert = driver.switchTo().alert();
            logger.info("Dismissing alert");
            alert.dismiss();
            logger.info("Dismissed alert");
        } catch (NoAlertPresentException ex) {
            logger.error("No Alert Dialog present");
        }
    }

    public String getTextAlertModalDialog() {
        try {
            Alert alert = driver.switchTo().alert();
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
        driver.switchTo().window(newWindowHandle);
        takeScreenShot(driver);
    }

    public void closeWindow(String findWindowByCriteria, String value) {
        String startingWindowHandle;
        String newWindowHandle;

        startingWindowHandle = driver.getWindowHandle();
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

        driver.switchTo().window(newWindowHandle);
        driver.close();
        driver.switchTo().window(startingWindowHandle);
    }

    protected String findWindow(String findWindowByCriteria, String value) {
        Set<String> handles = driver.getWindowHandles();
        // In order to evaluate the properties on the open windows, Selenium
        // requires that you switch to each window
        // you're evaluating - keep track of the original window
        // so we can switch back to it.
        String startingWindowHandle = driver.getWindowHandle();
        String matchingWindowHandle = null;
        boolean found = false;

        logger.info("Searching for window with property "
                + findWindowByCriteria + " = " + value + " ...");

        for (String handle : handles) {
            driver.switchTo().window(handle);

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
            matchingWindowHandle = driver.getWindowHandle();
            logger.info("Found window with property " + findWindowByCriteria
                    + " = " + value + ".  Window handle ID = "
                    + matchingWindowHandle);
        } else {
            logger.info("Unable to find window with property "
                    + findWindowByCriteria + " = " + value);
        }

        // Switch back to the original window.
        driver.switchTo().window(startingWindowHandle);

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
        Actions action = new Actions(driver);
        action.moveToElement(webElement);
    }

    public void doubleClick(String elementLocator) {
        WebElement webElement = getElement(elementLocator).get(0);
        doubleClick(webElement);
    }

    public void doubleClick(WebElement webElement) {
        Actions actions = new Actions(driver);
        logger.info("Double click on element :" + webElement);
        actions.doubleClick(webElement).perform();
    }

    public void dragAndDrop(String elementLocatorSource, String elementLocatorTarget) {
        WebElement webElementSource = getElement(elementLocatorSource).get(0);
        WebElement webElementTarget = getElement(elementLocatorTarget).get(0);
        dragAndDrop(webElementSource, webElementTarget);
    }

    public void dragAndDrop(WebElement webElementSource, WebElement webElementTarget) {
        Actions actions = new Actions(driver);
        logger.info("Drag element from :" + webElementSource + " to :" + webElementTarget);
        actions.dragAndDrop(webElementSource, webElementTarget).perform();
    }

    public void javaScriptClick(WebElement webElement) {
        try {
            if (webElement.isDisplayed() && webElement.isEnabled()) {
                logger.info("Element is enabled and is present, clicking using Java script click");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
            } else {
                logger.error("Unable to click using Java script click");
            }
        } catch (Exception e) {
            logger.error("Unable to click using Java script click");
        }
    }


}
