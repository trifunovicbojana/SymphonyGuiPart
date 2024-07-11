package stepDefinitions;

import PageObjects.AboutUsCompany;
import PageObjects.CurrentOpenings;
import PageObjects.Header;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import utils.Helpers;
import utils.Services;

public class PartI extends Helpers {

    Header header;
    Services services;
    AboutUsCompany aboutUsCompany;
    SoftAssert softAssert;
    CurrentOpenings currentOpenings;
    private static final Logger logger = LogManager.getLogger(PartI.class);

    public PartI() {

        this.header = new Header();
        this.services = new Services();
        this.aboutUsCompany = new AboutUsCompany();
        this.softAssert = new SoftAssert();
        this.currentOpenings = new CurrentOpenings();

    }

    @Given("User opens maximized browser and navigates to Homepage URL")
    public void UserOpensMaximizedBrowserAndNavigatesToURL() {
        logger.info("Opening maximized browser and navigating to URL");
        services.navigateToHomepage();
        logger.info("Browser with Homepage URL opened");
    }

    @Then("Checking if Homepage Url is opened")
    public void checkingHomepageUrlIsOpened() {
        logger.info("Checking if Homepage Url is opened");
        Assert.assertEquals(getCurrentUrl(), testingEnvironment, "Homepage URL is not opened");
        logger.info("Homepage URL opened");
    }

    @And("Checking Homepage logo is displayed")
    public void checkingHomepageIsDisplayed() {
        logger.info("Checking Homepage logo is displayed");
        Assert.assertTrue(header.isSymphonyLogoDisplayed(), "Homepage logo is not displayed");
        logger.info("Homepage logo is displayed");
    }

    @When("User navigates to the About Us {string} section")
    public void userNavigatesToAboutUsCompanySection(String linkText) {
        header.hoverAboutUsLink();
        header.clickOnLink(linkText);
    }

    @Then("User should see the information about company")
    public void userShouldSeeTheInformationAboutCompany() {
        softAssert.assertTrue(aboutUsCompany.checkIfMetaDataElementsAreVisible("HQ"));
        softAssert.assertTrue(aboutUsCompany.checkIfMetaDataElementsAreVisible("Founded"));
        softAssert.assertTrue(aboutUsCompany.checkIfMetaDataElementsAreVisible("Size"));
        softAssert.assertTrue(aboutUsCompany.checkIfMetaDataElementsAreVisible("Consulting Locations"));
        softAssert.assertTrue(aboutUsCompany.checkIfMetaDataElementsAreVisible("Engineering Hubs"));
        softAssert.assertTrue(aboutUsCompany.checkIfMetaDataElementsAreVisible("Clients"));
        softAssert.assertTrue(aboutUsCompany.checkIfMetaDataElementsAreVisible("Certifications"));
        softAssert.assertEquals(aboutUsCompany.getMeatDataElementsText("HQ"), loadPropertiesToHashMap("data", "HQ"), "HQ not Ok");
        softAssert.assertEquals(aboutUsCompany.getMeatDataElementsText("Founded"), loadPropertiesToHashMap("data", "Founded"), "Founded not Ok");
        softAssert.assertEquals(aboutUsCompany.getMeatDataElementsText("Size"), loadPropertiesToHashMap("data", "Size"), "Size not Ok");
        softAssert.assertEquals(aboutUsCompany.getMeatDataElementsText("Consulting Locations"), loadPropertiesToHashMap("data", "Consulting_Locations"), "ConsultingLocations not Ok");
        softAssert.assertEquals(aboutUsCompany.getMeatDataElementsText("Engineering Hubs"), loadPropertiesToHashMap("data", "Engineering_Hubs"), "EngineeringHubs not Ok");
        softAssert.assertEquals(aboutUsCompany.getMeatDataElementsText("Clients"), loadPropertiesToHashMap("data", "Clients"), "Clients not Ok");
        softAssert.assertEquals(aboutUsCompany.getMeatDataElementsText("Certifications"), loadPropertiesToHashMap("data", "Certifications"), "Certifications not Ok");
        softAssert.assertAll();

    }

    @And("Correct Url for About us is opened")
    public void correctUrForAboutUslIsOpened() {
        Assert.assertEquals(getCurrentUrl(), expectedUrl("aboutUsCompany"));
    }

    @When("User clicks on the {string} link")
    public void userClicksOnCareersLink(String linkText) {
        header.clickOnLink(linkText);
    }

    @Then("Counting and checking the number of open positions from all locations")
    public void countingAndCheckingTheNumberOfOpenPositionsFromAllLocations() {
        Assert.assertEquals(currentOpenings.countOpenPositions(), currentOpenings.CountOpenPositionsByLocations());

    }

    @Then("Collecting the data and creating txt document")
    public void collectingTheDataAndCreatingTxtDocument() {
        currentOpenings.getPositionTitleAndLocation(getSingleValueFromProperties(loadPropertiesToHashMap("data", "Open_Position_Text_Document_Name")));

    }

    @And("Checking if txt document is created")
    public void checkingIfTxtDocumentIsCreated() {
        Assert.assertTrue(isFileCreated(getSingleValueFromProperties(loadPropertiesToHashMap("data", "Open_Position_Text_Document_Name"))));
    }

}