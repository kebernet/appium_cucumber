package cucumber.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import java.util.logging.Logger;

import cucumber.steps.impl.BaseStepsStrategy;
import cucumber.steps.impl.AndroidBaseSteps;
import cucumber.steps.impl.IOSBaseSteps;

public class CucumberModule extends AbstractModule {

    private static final Logger LOGGER = Logger.getLogger(CucumberModule.class.getCanonicalName());

    @Override
    protected void configure() {
        String platform = System.getenv("platform");
        LOGGER.info("Configuring run for platform: "+platform);

        switch(platform){
            case "android":
                bind(BaseStepsStrategy.class).to(AndroidBaseSteps.class).in(Singleton.class);
                break;
            case "ios":
                bind(BaseStepsStrategy.class).to(IOSBaseSteps.class).in(Singleton.class);
                break;
            default:
                throw new RuntimeException("Unknown platform environment variable: "+platform);
        }
    }
}
