package configs.devices;

import configs.app.App;
import configs.devices.Device;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Android extends Device {

    public Android(String deviceName, String model, String version, String uDID, App app) {
        this.app = app;
        this.os = "android";
        this.origin = "simulator";
        this.device = deviceName;

        this.model = model;
        this.name =  this.device + " " + model;
        this.platformVersion = version;
        this.uDID = uDID;

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("appium:automationName", "UiAutomator2");
        cap.setCapability("appium:platformVersion", platformVersion);
        cap.setCapability("appium:udid", uDID);
        cap.setCapability("appium:deviceName", name);
        this.capabilities = cap;
    }
}
