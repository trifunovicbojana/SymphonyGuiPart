package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.DriverManager;


public class Hooks {
    private static Logger logger = LogManager.getLogger("Test initialization");

    @Before
    public static void setup(Scenario scenario) {

        System.setProperty("log4j.configurationFile", "src/main/resources/log4j2.xml");
        logger.info(scenario.getName() + " STARTED!");
    }
    @AfterStep
    public void afterFailedStep(Scenario scenario) {
        if (scenario.isFailed()) {
            logger = LogManager.getLogger("Test termination");
            logger.info(scenario.getName() + " FAILED!");
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (!scenario.isFailed()) {
            logger = LogManager.getLogger("Test termination");
            logger.info(scenario.getId() + "_" + scenario.getName() + " PASSED!");
        }
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
