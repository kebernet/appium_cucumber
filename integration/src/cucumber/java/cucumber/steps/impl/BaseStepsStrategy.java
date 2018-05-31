package cucumber.steps.impl;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;


public abstract class BaseStepsStrategy<T extends WebDriver> {

    private T driver;

    BaseStepsStrategy() throws MalformedURLException {
        this.driver = createDriver();
    }

    protected abstract T createDriver() throws MalformedURLException;

    T getDriver(){
        return this.driver;
    }

    public abstract void startApp() throws IOException;

    public abstract void clickByText(String text);

    public abstract void assertMissing(String text);

    public abstract List<LogEntry> getLogEntries();

    public abstract File getScreenshotAsFile();
}
