package cucumber.steps.impl;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import static org.junit.Assert.assertTrue;

public class AndroidBaseSteps extends BaseStepsStrategy<AndroidDriver> {

    public AndroidBaseSteps() throws MalformedURLException {
        super();
    }

    @Override
    protected AndroidDriver createDriver() throws MalformedURLException {
        File app = new File(System.getenv("apk"));
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName","Android Emulator");
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", "net.kebernet.appium_cucumber");
        capabilities.setCapability("appActivity", ".MainActivity");
        return new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
    }

    @Override
    public void startApp() throws IOException {
        getDriver().resetApp();
    }

    @Override
    public void clickByText(String text) {
        getDriver().findElementByAndroidUIAutomator("new UiSelector().textContains(\""+text+"\")")
                .click();
    }

    @Override
    public void assertMissing(String text) {
        MobileElement element = null;
        try {
            element = (MobileElement) getDriver().findElementByAndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")");
        } catch(NoSuchElementException e){
            //expected exception;
        }
        assertTrue(element == null);
    }
}
