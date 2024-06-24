package configs.platformConfig.android;


import configs.AppiumConfig;
import configs.Config;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class AndroidConfig extends Config {
    public AndroidDriver android;

    public void initDriver(DesiredCapabilities capabilities) throws Exception {
        AppiumConfig appiumConfig = new AppiumConfig();
        capabilities.setCapability("appium:noReset", true);
        capabilities.setCapability("appium:appPackage", device.app.appPackage);
        capabilities.setCapability("appium:appActivity", device.app.appActivity);
        capabilities.setCapability("appium:ensureWebviewsHavePages", true);
        capabilities.setCapability("appium:enableWebviewDetailsCollection", true);
        capabilities.setCapability("appium:autoWebviewTimeout", 5000);
        capabilities.setCapability("appium:systemPort", appiumConfig.androidWDPort);
        capabilities.setCapability("appium:chromedriverPort", appiumConfig.androidChromePort);
        android = new AndroidDriver(new URL("http://" + appiumConfig.appiumAndroidIP + ":" + appiumConfig.appiumAndroidPort + "/"), capabilities);
    }
}