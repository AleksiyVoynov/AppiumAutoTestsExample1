package configs;

import configs.app.ChromeApp;
import configs.devices.Device;
import configs.devices.simulators.Android;

public class Config {
    /**
     * Example for Android:
     * * public static Device device = new Android("PIXEL", "sdk_gphone64_x86_64", "13", "emulator-5554", new ChromeApp());
     * or
     * Example for IOS:
     * public static Device device = new IOS("iPhone", "15 Pro Max", "17.2", "B8124B25-68A4-4C5B-BC99-F9FFE9E7EFDB", "lesha voynov (Personal Team)", new SafariApp());
     */
    public static Device device = new Android("PIXEL", "sdk_gphone64_x86_64", "13", "emulator-5554", new ChromeApp());

}