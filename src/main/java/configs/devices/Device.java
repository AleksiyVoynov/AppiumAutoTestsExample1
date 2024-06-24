package configs.devices;

import configs.app.App;
import org.openqa.selenium.remote.DesiredCapabilities;

public abstract class Device {
    public App app;
    public String os;
    public String origin;
    public String device;
    public String model;
    public String name;
    public String platformVersion;
    public String uDID;
    public DesiredCapabilities capabilities;

    @Override
    public String toString() {
        return "Device {" +
                "app=" + app +
                ", os='" + os + '\'' +
                ", origin='" + origin + '\'' +
                ", device='" + device + '\'' +
                ", model='" + model + '\'' +
                ", name='" + name + '\'' +
                ", platformVersion='" + platformVersion + '\'' +
                '}';
    }
}
