# Requirements:
* MacOS Sonoma and above
* Java 17
* installed Appium 2.0 and above
* installed xcuitest driver
* installed uiautomator2 driver
* For Android, you should have the virtual device from Android Studio
* For iOS, you should have a simulator from Xcode and setup WebDriverAgent to simulator


# Guide:
## Start the Appium server for Android and/or iOS.
    * For Android:
      appium -a 127.0.0.1 -p 4732 --session-override --log-timestamp --local-timezone --allow-insecure chromedriver_autodownload

    * For iOS:
      appium -a 0.0.0.0 -p 4723 --session-override --relaxed-security --driver-xcuitest-webdriveragent-port 8100 --log-timestamp --local-timezone
      
## Put your device setting instead mine:
* you can find it here src/test/java/BaseTest.java ' private Device device = new Device(your settings)' 
* or put yor settings to 'src/test/java/tests.xml'

## To run tests and generate Allure report:
* open terminal
* cd (path to /AppiumAutotestsTestNg)
* run `gradle clean`
* run `gradle test`

## You can run auto test in parallel for Android and IOS
* open terminal
* cd (path to /AppiumAutoTestsExample1)
* run `gradle clean`
* run `gradle parallelTest`

## To see a report:
1. run `allure serve build/allure-results `
