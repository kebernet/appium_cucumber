package cucumber.pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MainActivityPage {


    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/Fakecontent\")")
    public MobileElement clickMeButton;
}
