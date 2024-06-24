package configs.platformConfig.ios;

import configs.AppiumConfig;
import configs.Config;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class IOSConfig extends Config {
    public IOSDriver ios;

    public void initDriver(DesiredCapabilities capabilities) throws MalformedURLException {
        AppiumConfig appiumConfig = new AppiumConfig();
        capabilities.setCapability("appium:automationName", "XCUITest");
        capabilities.setCapability("appium:bundleId", device.app.bundleId);
        capabilities.setCapability("appium:newCommandTimeout", 0);
        capabilities.setCapability("appium:clearSystemFiles", true);
        capabilities.setCapability("appium:skipLogCapture", true);
        capabilities.setCapability("appium:shouldUseSingletonTestManager", false);
        capabilities.setCapability("appium:safariIgnoreWebHostnames", "about:blank");
        capabilities.setCapability("appium:wdaEventloopIdleDelay", 3);
        capabilities.setCapability("appium:usePrebuiltWDA", true);
        capabilities.setCapability("appium:noReset", true);
        capabilities.setCapability("appium:fullReset", false);
        capabilities.setCapability("appium:webviewConnectTimeout", 5000);

        capabilities.setCapability("appium:wdaLocalPort", appiumConfig.IOSWDPort);
        capabilities.setCapability("appium:derivedDataPath", findWebDriverAgentPath());
        this.ios = new IOSDriver(new URL("http://" + appiumConfig.appiumIOSIP + ":" + appiumConfig.appiumIOSPort + "/"), capabilities);
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
