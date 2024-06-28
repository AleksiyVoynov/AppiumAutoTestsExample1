import configs.Config;
import configs.app.ChromeApp;
import configs.app.SafariApp;
import configs.devices.Device;
import configs.devices.simulators.Android;
import configs.devices.simulators.IOS;
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
     * private Device device = new IOS("iPhone", "15 Pro Max", "17.2", "B8124B25-68A4-4C5B-BC99-F9FFE9E7EFDB", "lesha voynov (Personal Team)", new SafariApp());
     */
    private Device device = new IOS("iPhone", "15 Pro Max", "17.2", "B8124B25-68A4-4C5B-BC99-F9FFE9E7EFDB", "lesha voynov (Personal Team)", new SafariApp());
    protected Config config;

    @BeforeClass
    @Step("setting up Appium driver")
    @Parameters({"os"})
    protected void setUp(@Optional("default") String os) throws Exception {
        if (!os.equals("default")) {
            if (os.equals("ios")) {
                device = new IOS("iPhone", "15 Pro Max", "17.2", "B8124B25-68A4-4C5B-BC99-F9FFE9E7EFDB", "lesha voynov (Personal Team)", new SafariApp());
            } else if (os.equals("android")) {
                device = new Android("PIXEL", "sdk_gphone64_x86_64", "13", "emulator-5554", new ChromeApp());
            } else {
                Assert.fail("unknown device platform");
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
    @Step("tearing down Appium driver")
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
}
