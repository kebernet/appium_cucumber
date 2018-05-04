package cucumber.steps;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import static org.junit.Assert.assertTrue;


public class BaseSteps {
    protected AndroidDriver<MobileElement> driver;

    @Given("I have launched the application")
    public void startApp() throws IOException {
        File app = new File(System.getenv("apk"));
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName","Android Emulator");
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", "net.kebernet.appium_cucumber");
        capabilities.setCapability("appActivity", ".MainActivity");
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);

        driver.resetApp();

    }

    @When("I click the \"(.*)\" button")
    public void clickByText(String text){
        driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\""+text+"\")")
                .click();
    }

    @Then("the \"(.*)\" is gone")
    public void assertMissing(String text){
        MobileElement element = null;
        try {
            element = driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")");
        } catch(NoSuchElementException e){
            //expected exception;
        }

        assertTrue(element == null);
    }
}
