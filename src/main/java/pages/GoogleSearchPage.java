package pages;

import configs.devices.simulators.Android;
import configs.devices.simulators.IOS;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GoogleSearchPage extends BasePage {
    private final By search = By.cssSelector("textarea[name='q']");
    private final By results = By.cssSelector("#rso [role='text'], #rso [role='link']");

    public GoogleSearchPage(AppiumDriver appiumDriver) {
        super(appiumDriver);
    }

    @Step("make search {0}")
    public void searchFor(String text) {
        appiumDriver.findElement(search).sendKeys(text);
        if (device instanceof Android) {
            ((AndroidDriver) appiumDriver).pressKey(new KeyEvent(AndroidKey.ENTER));
        } else if (device instanceof IOS){
            nativeContext();
            appiumDriver.findElement(AppiumBy.iOSNsPredicateString("label == 'search'")).click();
            webViewContext();
        }
    }

    @Step("take search result")
    public boolean getSearchingResult(String text) {
        return new WebDriverWait(appiumDriver, Duration.ofSeconds(5), Duration.ofSeconds(1))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(results))
                .stream()
                .map(WebElement::getText)
                .anyMatch(elementText -> elementText.toLowerCase().contains(text.toLowerCase()));
    }
}
