package configs.platformConfig.ios;

import configs.AppiumConfig;
import configs.devices.Device;
import io.appium.java_client.ios.IOSDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class IOSConfig {
    public IOSDriver ios;

    public void initDriver(Device device) throws MalformedURLException {
        AppiumConfig appiumConfig = new AppiumConfig();
        device.capabilities.setCapability("appium:automationName", "XCUITest");
        device.capabilities.setCapability("appium:bundleId", device.app.bundleId);
        device.capabilities.setCapability("appium:newCommandTimeout", 0);
        device.capabilities.setCapability("appium:clearSystemFiles", true);
        device.capabilities.setCapability("appium:skipLogCapture", true);
        device.capabilities.setCapability("appium:shouldUseSingletonTestManager", false);
        device.capabilities.setCapability("appium:safariIgnoreWebHostnames", "about:blank");
        device.capabilities.setCapability("appium:wdaEventloopIdleDelay", 3);
        device.capabilities.setCapability("appium:usePrebuiltWDA", true);
        device.capabilities.setCapability("appium:noReset", true);
        device.capabilities.setCapability("appium:fullReset", false);
        device.capabilities.setCapability("appium:webviewConnectTimeout", 5000);

        device.capabilities.setCapability("appium:wdaLocalPort", appiumConfig.IOSWDPort);
        device.capabilities.setCapability("appium:derivedDataPath", findWebDriverAgentPath());
        this.ios = new IOSDriver(new URL("http://" + appiumConfig.appiumIOSIP + ":" + appiumConfig.appiumIOSPort + "/"), device.capabilities);
    }

    private String findWebDriverAgentPath() {
        StringBuilder output = new StringBuilder();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", "find /Users/*/Library/Developer/Xcode/DerivedData -type d -name 'WebDriverAgent-*'");
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                System.err.println("An error occurred while executing the command.");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return output.toString().trim();
    }
}
