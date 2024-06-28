package configs.platformConfig.android;


import configs.AppiumConfig;
import configs.devices.Device;
import io.appium.java_client.android.AndroidDriver;

import java.net.URL;

public class AndroidConfig {
    public AndroidDriver android;

    public void initDriver(Device device) throws Exception {
        AppiumConfig appiumConfig = new AppiumConfig();
        device.capabilities.setCapability("appium:noReset", true);
        device.capabilities.setCapability("appium:appPackage", device.app.appPackage);
        device.capabilities.setCapability("appium:appActivity", device.app.appActivity);
        device.capabilities.setCapability("appium:ensureWebviewsHavePages", true);
        device.capabilities.setCapability("appium:enableWebviewDetailsCollection", true);
        device.capabilities.setCapability("appium:autoWebviewTimeout", 5000);
        device.capabilities.setCapability("appium:systemPort", appiumConfig.androidWDPort);
        device.capabilities.setCapability("appium:chromedriverPort", appiumConfig.androidChromePort);
        android = new AndroidDriver(new URL("http://" + appiumConfig.appiumAndroidIP + ":" + appiumConfig.appiumAndroidPort + "/"), device.capabilities);
    }
}