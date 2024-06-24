import configs.Config;
import configs.devices.simulators.Android;
import configs.devices.simulators.IOS;
import configs.platformConfig.android.AndroidConfig;
import configs.platformConfig.ios.IOSConfig;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;


public class BaseTest extends Config implements IHookable {
    protected static AppiumDriver appiumDriver;

    @BeforeClass
    @Step("setting up Appium driver")
    protected void setUp() throws Exception {
        if (device instanceof Android) {
            AndroidConfig androidConfig = new AndroidConfig();
            androidConfig.initDriver(Config.device.capabilities);
            appiumDriver = androidConfig.android;
        } else if (device instanceof IOS) {
            IOSConfig iosConfig = new IOSConfig();
            iosConfig.initDriver(Config.device.capabilities);
            appiumDriver = iosConfig.ios;
        } else {
            Assert.fail("unknown device platform");
        }
        Allure.addAttachment("Device Info", device.toString());
        appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
    }

    @AfterClass
    @Step("tearing down Appium driver")
    protected void tearDown() {
        appiumDriver.quit();
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
        return appiumDriver.getScreenshotAs(OutputType.BYTES);
    }
}
