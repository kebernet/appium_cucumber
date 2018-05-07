package cucumber.steps.impl;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import static org.junit.Assert.assertNull;

public class IOSBaseSteps extends BaseStepsStrategy<IOSDriver<MobileElement>> {


    public IOSBaseSteps() throws MalformedURLException {
        super();
    }

    @Override
    protected IOSDriver<MobileElement> createDriver() throws MalformedURLException {

        File app = new File(System.getenv("app"));

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.3");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

        return new IOSDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @Override
    public void startApp() throws IOException {
        getDriver().resetApp();
    }

    @Override
    public void clickByText(String text) {
        MobileElement element = getDriver().findElementByXPath("//*[contains(@label, '" + text + "')]");
        element.click();
    }

    @Override
    public void assertMissing(String text) {
        MobileElement element = null;
        try {
            element = getDriver().findElementByXPath("//*[contains(@label, '" + text + "')]");
        } catch(NoSuchElementException e){
            //expected execption;
        }
        assertNull(element);

    }
}
