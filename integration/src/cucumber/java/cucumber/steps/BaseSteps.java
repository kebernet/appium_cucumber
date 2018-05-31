package cucumber.steps;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.hooks.Recorder;
import cucumber.steps.impl.BaseStepsStrategy;

import static org.junit.Assert.assertTrue;

@Singleton
public class BaseSteps {
    private final BaseStepsStrategy strategy;

    @Inject
    public BaseSteps(BaseStepsStrategy baseSteps){
        this.strategy = baseSteps;
    }

    @Given("I have launched the application")
    public  void startApp() throws Exception {
        doStep(()->strategy.startApp());
    }

    @When("I click the \"(.*)\" button")
    public void clickByText(String text) throws Exception {
        doStep(()->strategy.clickByText(text));
    }

    @Then("the \"(.*)\" is gone")
    public void assertMissing(String text) throws Exception {
        doStep(()->{
            strategy.assertMissing(text);
            assertTrue("The thing failed", false);

        });
    }


    private void beforeStep() {
        Recorder.record(strategy.getScreenshotAsFile());
    }

    @SuppressWarnings("unchecked")
    private void afterStep() {
        Recorder.log(strategy.getLogEntries());
        Recorder.record(strategy.getScreenshotAsFile());
    }


    private void doStep(ThrowRunnable runnable) throws Exception {
        beforeStep();
        try {
            runnable.run();
        } finally {
            afterStep();
        }
    }

    private interface ThrowRunnable {
        void run() throws Exception;
    }
}
