package configs;

import configs.devices.Device;
import io.appium.java_client.AppiumDriver;

public class Config {
    public Device device;

    public AppiumDriver appiumDriver;

    public Config(AppiumDriver appiumDriver, Device device) {
        this.appiumDriver = appiumDriver;
        this.device = device;
    }
}