package cucumber.steps;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.impl.BaseStepsStrategy;

@Singleton
public class BaseSteps {
    private final BaseStepsStrategy strategy;

    @Inject
    public BaseSteps(BaseStepsStrategy baseSteps){
        this.strategy = baseSteps;
    }

    @Given("I have launched the application")
    public  void startApp() throws IOException{
        strategy.startApp();
    }

    @When("I click the \"(.*)\" button")
    public void clickByText(String text){
        strategy.clickByText(text);
    }

    @Then("the \"(.*)\" is gone")
    public void assertMissing(String text){
        strategy.assertMissing(text);
    }
}
