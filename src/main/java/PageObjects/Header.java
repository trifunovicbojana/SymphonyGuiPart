package PageObjects;

import org.openqa.selenium.By;
import utils.Helpers;

public class Header extends Helpers {
    protected By aboutUsLink = By.linkText("About Us");
    protected By symphonyLogo = By.cssSelector("[aria-label='Symphony logo']");

    public void clickOnLink(String accessText ) {
    clickOnLocatorOnceItBecomesInteractable( getelementbytype("linkText",accessText));
    }
    public void hoverAboutUsLink() {
        hoverElement(aboutUsLink);
    }

    public Boolean isSymphonyLogoDisplayed() {
        return visibilityOfElement(symphonyLogo);
    }


}
