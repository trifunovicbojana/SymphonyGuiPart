package utils;


import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * Helpers class provides utility methods for interacting with WebDriver,
 * handling properties files, performing file operations, and other common tasks.
 */

public class Helpers {
    protected WebDriver driver;
    public String testingEnvironment = System.getProperty("environment", "https://symphony.is/");

    /**
     * Constructor initializes WebDriver instance based on system properties.
     * Defaults to Chrome browser if no browser property is specified.
     */
    public Helpers() {
        driver = DriverManager.getDriver(System.getProperty("browser", "chrome"));

    }

    /**
     * Navigates to the specified URL.
     *
     * @param Url The URL to navigate to.
     */
    public void getUrl(String Url) {
        driver.get(Url);
    }

    /**
     * Finds and returns the WebElement identified by the given locator.
     *
     * @param locator The locator strategy to find the element.
     * @return The WebElement found.
     */

    public WebElement find(By locator) {
        WebElement element;

        element = new WebDriverWait(driver, Duration.of(30, SECONDS)).
                until(ExpectedConditions.presenceOfElementLocated(locator));
        return element;
    }

    /**
     * Clicks on the element identified by the locator once it becomes interactable.
     *
     * @param locator The locator strategy to find the element to click.
     */
    public void clickOnLocatorOnceItBecomesInteractable(By locator) {
        try {
            WebElement element = new WebDriverWait(driver, Duration.of(10, SECONDS))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(ElementClickInterceptedException.class)
                    .ignoring(ElementNotInteractableException.class)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.click();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    /**
     * Hovers over the element identified by the locator.
     *
     * @param locator The locator strategy to find the element to hover over.
     */


    public void hoverElement(By locator) {
        Actions action = new Actions(driver);
        try {
            WebElement element = new WebDriverWait(driver, Duration.of(10, SECONDS))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(ElementClickInterceptedException.class)
                    .ignoring(ElementNotInteractableException.class)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            action.moveToElement(element).perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the current URL of the page.
     *
     * @return The current URL as a String.
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();

    }

    /**
     * Waits for the element identified by the locator to be visible.
     *
     * @param locator The locator strategy to find the element to wait for visibility.
     * @return True if the element is visible within the timeout, false otherwise.
     */

    public boolean visibilityOfElement(By locator) {
        try {
            new WebDriverWait(driver, Duration.of(10, SECONDS))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(ElementClickInterceptedException.class)
                    .ignoring(ElementNotInteractableException.class)
                    .ignoring(TimeoutException.class)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<WebElement> findAllElements(By locator) {
        return driver.findElements(locator);
    }


    public static HashMap<String, List<String>> loadPropertiesToHashMap(String fileName, String... keys) {
        Properties properties = new Properties();
        HashMap<String, List<String>> map = new HashMap<>();

        try (FileInputStream fis = new FileInputStream("src/test/Resources/" + fileName + ".properties")) {
            properties.load(fis);

            for (String key : keys) {
                String value = properties.getProperty(key);
                if (value != null) {
                    String formattedKey = key.replace("_", " ");
                    List<String> valuesList = Stream.of(value.split(","))
                            .map(String::trim)
                            .collect(Collectors.toList());
                    map.put(formattedKey, valuesList);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public String expectedUrl(String testedUrl) {
        HashMap<String, List<String>> expectedUrlMap = loadPropertiesToHashMap("urls", testedUrl);
        List<String> values = expectedUrlMap.get(testedUrl);
        return testingEnvironment + values.get(0);
    }

    public boolean visibilityOfAllElements(By locator) {
        boolean visible = true;

        List<WebElement> elementList = findAllElements(locator);
        for (WebElement element : elementList) {
            try {
                new WebDriverWait(driver, Duration.of(10, SECONDS)).
                        until(ExpectedConditions.visibilityOf(element));
            } catch (TimeoutException e) {
                e.printStackTrace();
                visible = false;

            }
        }

        return visible;
    }

    public By getelementbytype(String type, String access_name) {
        switch (type) {
            case "id":
                return By.id(access_name);
            case "name":
                return By.name(access_name);
            case "class":
                return By.className(access_name);
            case "xpath":
                return By.xpath(access_name);
            case "css":
                return By.cssSelector(access_name);
            case "linkText":
                return By.linkText(access_name);
            case "partialLinkText":
                return By.partialLinkText(access_name);
            case "tagName":
                return By.tagName(access_name);
            default:
                return null;

        }
    }

    public String getElementAttributeValue(By locator, String attribute) {
        return find(locator).getAttribute(attribute);

    }

    public void clickForcefully(WebElement element) {
        element = new WebDriverWait(driver, Duration.of(10, SECONDS))
                .until(ExpectedConditions.visibilityOf(element));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public String getTextContent(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String textContent = (String) js.executeScript("return arguments[0].textContent;", element);
        return textContent;
    }

    public void addTextToFile(String fileName, String entryData) {

        try {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                writer.write(entryData);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteIfFileExists(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    public static boolean isFileCreated(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public String getSingleValueFromProperties(Map<String, List<String>> map) {
        if (map != null && !map.isEmpty()) {
            Map.Entry<String, List<String>> firstEntry = map.entrySet().iterator().next();
            List<String> values = firstEntry.getValue();
            if (values != null && !values.isEmpty()) {
                return values.get(0);
            }
        }
        return null;
    }
}




