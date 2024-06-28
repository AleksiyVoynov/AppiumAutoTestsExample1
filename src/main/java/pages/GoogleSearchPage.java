package pages;

import configs.Config;
import configs.devices.simulators.Android;
import configs.devices.simulators.IOS;
import io.appium.java_client.AppiumBy;
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

    public GoogleSearchPage(Config config) {
        super(config);
    }

    @Step("make search {0}")
    public void searchFor(String text) {
        config.appiumDriver.findElement(search).sendKeys(text);
        if (config.device instanceof Android) {
            ((AndroidDriver) config.appiumDriver).pressKey(new KeyEvent(AndroidKey.ENTER));
        } else if (config.device instanceof IOS){
            nativeContext();
            config.appiumDriver.findElement(AppiumBy.iOSNsPredicateString("label == 'search'")).click();
            webViewContext();
        }
    }

    @Step("take search result")
    public boolean getSearchingResult(String text) {
        return new WebDriverWait(config.appiumDriver, Duration.ofSeconds(5), Duration.ofSeconds(1))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(results))
                .stream()
                .map(WebElement::getText)
                .anyMatch(elementText -> elementText.toLowerCase().contains(text.toLowerCase()));
    }
}
