package pages;

import com.google.common.collect.ImmutableMap;
import configs.Config;
import configs.app.ChromeApp;
import configs.app.SafariApp;
import configs.devices.simulators.Android;
import configs.devices.simulators.IOS;
import io.appium.java_client.NoSuchContextException;
import io.appium.java_client.remote.SupportsContextSwitching;
import io.qameta.allure.Step;
import org.openqa.selenium.remote.DriverCommand;

import java.util.ArrayList;
import java.util.List;

public class BasePage {
    protected Config config;

    public BasePage(Config config) {
        this.config = config;
    }

    public void webViewContext() {
        if (config.device instanceof Android) {
            ((SupportsContextSwitching) config.appiumDriver).context(new ChromeApp(getContext()).webViewContext);
        } else if (config.device instanceof IOS) {
            ((SupportsContextSwitching) config.appiumDriver).context(new SafariApp(getContext()).webViewContext);
        }
    }

    public void nativeContext() {
        if (config.device instanceof Android) {
            config.appiumDriver.execute(DriverCommand.SWITCH_TO_CONTEXT, ImmutableMap.of("name", "NATIVE_APP"));
        } else if (config.device instanceof IOS){
            ((SupportsContextSwitching) config.appiumDriver).context("NATIVE_APP");
        }
    }

    private List<String> getContext() {
        ArrayList<String> s = new ArrayList<>(((SupportsContextSwitching) config.appiumDriver).getContextHandles());
        //s.forEach(System.out::println);
        return new ArrayList<>(((SupportsContextSwitching) config.appiumDriver).getContextHandles());
    }

    @Step("go to url")
    public void navigateTo(String url) {
        try {
            webViewContext();
            config.appiumDriver.get(url);
        } catch (NoSuchContextException ignored) {
            nativeContext();
            config.appiumDriver.get(url);
            webViewContext();
        }
    }
}
