package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.Helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AboutUsCompany extends Helpers {

    /**
     * Generates a locator for identifying metadata keys on the page.
     *
     * @param keyName The name of the metadata key to locate.
     * @return By object representing the locator for the metadata key.
     */
    protected By locatorForMetaDataKey(String keyName) {
        By metaDataKey = By.xpath("//strong[contains(text(),'" + keyName + "')]");
        return metaDataKey;
    }

    /**
     * Generates a locator for identifying metadata values associated with a key.
     *
     * @param keyName The name of the metadata key to locate values for.
     * @return By object representing the locator for the metadata values.
     */
    protected By locatorForMetaDataValues(String keyName) {
        By metaDataValue = By.xpath("//strong[contains(text(),'" + keyName + "')]/parent::li//span");
        return metaDataValue;
    }

    /**
     * Retrieves the text content of metadata elements associated with a given key.
     *
     * @param keyName The name of the metadata key to retrieve elements for.
     * @return A HashMap containing the key name as the key and a list of corresponding
     * element texts as the value.
     */

    public HashMap<String, List<String>> getMeatDataElementsText(String keyName) {

        visibilityOfElement(locatorForMetaDataKey(keyName));

        List<WebElement> metaDataVales = findAllElements(locatorForMetaDataValues(keyName));
        HashMap<String, List<String>> metaDataElements = new HashMap<>();
        List<String> elementTextList = new ArrayList<>();
        for (WebElement element : metaDataVales) {
            String elementText = element.getText();
            elementTextList.add(elementText);
        }
        metaDataElements.put(keyName, elementTextList);

        return metaDataElements;
    }

    /**
     * Checks if metadata elements associated with a given key are visible on the page.
     *
     * @param keyName The name of the metadata key to check visibility for.
     * @return True if all metadata elements are visible, false otherwise.
     */
    public Boolean checkIfMetaDataElementsAreVisible(String keyName) {
        return visibilityOfAllElements(locatorForMetaDataValues(keyName));

    }
}
