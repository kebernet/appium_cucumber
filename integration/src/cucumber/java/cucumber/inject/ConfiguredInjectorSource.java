package cucumber.inject;

import com.google.inject.Guice;
import com.google.inject.Injector;

import cucumber.api.guice.CucumberModules;
import cucumber.runtime.java.guice.InjectorSource;

public class ConfiguredInjectorSource implements InjectorSource {
    @Override
    public Injector getInjector() {
        return Guice.createInjector(CucumberModules.SCENARIO, new CucumberModule());
    }
}
