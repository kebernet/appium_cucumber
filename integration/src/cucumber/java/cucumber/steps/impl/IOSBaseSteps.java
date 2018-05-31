package cucumber.steps.impl;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Stream;

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

    @Override
    public List<LogEntry> getLogEntries() {
        List<LogEntry> allEntries = new ArrayList<>();
        getDriver().manage().logs().getAvailableLogTypes()
                .stream()
                .filter(Objects::nonNull)
                .flatMap(s -> {
                    try {
                        return getDriver().manage().logs().get(s).filter(Level.ALL).stream();
                    } catch (Exception e) {
                        return Stream.empty();
                    }
                })
                .filter(Objects::nonNull)
                .forEach(allEntries::add);
        allEntries.sort((o1, o2) -> Long.compare(o2.getTimestamp(), o1.getTimestamp()));
        return allEntries;
    }

    public File getScreenshotAsFile() {
        return getDriver().getScreenshotAs(OutputType.FILE);
    }
}
