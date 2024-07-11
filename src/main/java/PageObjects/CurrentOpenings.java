package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Helpers;

import java.time.Duration;
import java.util.List;

import static java.time.temporal.ChronoUnit.SECONDS;

public class CurrentOpenings extends Helpers {
    protected By openPositionBox = By.xpath("//li[contains(@class, 'currentOpenings--job')]");
    protected By locationTab = By.xpath("//nav[@class='currentOpenings--nav']//button");
    protected By positionTittleName=By.xpath(".//div[@class='currentOpenings--job-title']");
    protected By positionLocation=By.xpath(".//div[@class='currentOpenings--job-locationWrapper-name']");

    public int countOpenPositions() {
        int openPositionsCount = 0;
        String classAttribute = getElementAttributeValue(openPositionBox, "class");
        if (!classAttribute.contains("hidden")) {
            openPositionsCount = findAllElements(openPositionBox).size();
        }
        return openPositionsCount;
    }

    public int CountOpenPositionsByLocations() {
        int totalCount = 0;
        List<WebElement> locationTabs = findAllElements(locationTab);
        for (WebElement tab : locationTabs) {
            String locationName = tab.getText();
            if (!locationName.equals("All Locations")) {
                clickForcefully(tab);
                totalCount += countOpenPositions();
            }
        }
        return totalCount;

    }

    public void getPositionTitleAndLocation(String fileName) {
        deleteIfFileExists(fileName);
        visibilityOfElement(openPositionBox);
        List<WebElement> openPositonBoxElementList = findAllElements(openPositionBox);

        for (WebElement elementBoxList : openPositonBoxElementList) {
           String titleName = getTextContent(elementBoxList.findElement(positionTittleName));

           String location = getTextContent(elementBoxList.findElement((positionLocation)));
            String entryData = titleName + ", " + location;
            addTextToFile(fileName, entryData);
        }


    }
}

