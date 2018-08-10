package com.test.autothon.ui.core;

import com.test.autothon.common.StepDefinition;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class UIOperations extends StepDefinition {

    private final static Logger logger = Logger.getLogger(UIOperations.class);

    WebDriver driver;

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public void enterText(String elemLocator, String setValue) throws Exception {
        WebElement elem = getElement(elemLocator).get(0);
        elem.sendKeys(setValue);
        takeScreenShot(driver);
    }

    public void selectValue(String elemLocator, String selecionMethod, String setValue) throws Exception {
        WebElement elem = getElement(elemLocator).get(0);
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
        if (!elem.isSelected() == op) {
            elem.click();
        }
        takeScreenShot(driver);
    }

    public void selectRadioButton(String elemLocator, String buttonName) throws Exception {
        List<WebElement> elems = getElement(elemLocator);
        for (WebElement elem : elems) {
            if (elem.getAttribute("name").equals(buttonName)) {
                elem.click();
            }
        }
        takeScreenShot(driver);
    }

    public void click(String elemLocator) {
        WebElement elem = getElement(elemLocator).get(0);
        elem.click();
    }

    public void waitForElementToBeClickable(String elemLocator) {
        WebElement elem = getElement(elemLocator).get(0);
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

    public void takeScreenShot(WebDriver driver) throws Exception {
        TakesScreenshot scrShot = ((TakesScreenshot) driver);
        String SrcFile = scrShot.getScreenshotAs(OutputType.BASE64);
        AutomationUIUtils.setBase64Image(SrcFile);
    }
}
