import configs.Config;
import configs.app.App;
import configs.app.ChromeApp;
import configs.app.SafariApp;
import configs.devices.Device;
import configs.devices.Android;
import configs.devices.IOS;
import configs.platformConfig.android.AndroidConfig;
import configs.platformConfig.ios.IOSConfig;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;

public class BaseTest implements IHookable {
    /**
     * Example for Android:
     * * private Device device = new Android("PIXEL", "sdk_gphone64_x86_64", "13", "emulator-5554", new ChromeApp());
     * or
     * Example for IOS:
     * private Device device = new IOS("iPhone", "15 Pro Max", "17.2", "B8124B25-68A4-4C5B-BC99-F9FFE9E7EFDB", new SafariApp(), "lesha voynov (Personal Team)");
     * <p>
     * credential for real device:
     * private Device device = new Android("SAMSUNG", "SM-T875", "13", "R52R301FPHA", new ChromeApp());
     * private Device device = new IOS("iPad", "Pro (12.9-inch) (3rd generation)", "17.2", "00008027-001E68A101E3002E", new SafariApp(), "lesha voynov (Personal Team)");
     */
    private Device device = new IOS("iPhone", "15 Pro Max", "17.2", "B8124B25-68A4-4C5B-BC99-F9FFE9E7EFDB", new SafariApp(), "lesha voynov (Personal Team)");

    protected Config config;

    @BeforeClass
    @Step("setting up appium driver")
    @Parameters({"deviceName", "model", "version", "uDID", "app", "xcodeOrgId"})
    protected void setUp(@Optional("default") String deviceName,
                         @Optional("default") String model,
                         @Optional("default") String version,
                         @Optional("default") String uDID,
                         @Optional("default") String app,
                         @Optional("default") String xcodeOrgId) throws Exception {
        if (!deviceName.equals("default")) {
            if (!xcodeOrgId.equals("default")) {
                device = new IOS(deviceName, model, version, uDID, getApp(app), xcodeOrgId);
            } else {
                device = new Android(deviceName, model, version, uDID, getApp(app));
            }
        }

        if (device instanceof Android) {
            AndroidConfig androidConfig = new AndroidConfig();
            androidConfig.initDriver(device);
            config = new Config(androidConfig.android, device);
        } else if (device instanceof IOS) {
            IOSConfig iosConfig = new IOSConfig();
            iosConfig.initDriver(device);
            config = new Config(iosConfig.ios, device);
        } else {
            Assert.fail("unknown device platform");
        }
        config.appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
    }

    @AfterClass
    @Step("tearing down appium driver")
    protected void tearDown() {
        config.appiumDriver.quit();
    }

    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        callBack.runTestMethod(testResult);
        if (testResult.getThrowable() != null) {
            try {
                takeScreenShot(testResult.getMethod().getMethodName());
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Attachment(value = "Failure in method {0}", type = "image/png")
    private byte[] takeScreenShot(String ignoredMethodName) {
        return config.appiumDriver.getScreenshotAs(OutputType.BYTES);
    }

    private App getApp(String name) {
        return switch (name) {
            case "chrome" -> new ChromeApp();
            case "safari" -> new SafariApp();
            default -> throw new IllegalArgumentException("Invalid app: " + name);
        };
    }
}
