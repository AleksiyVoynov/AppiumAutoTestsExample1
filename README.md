# Requirements:
* MacOS Sonoma and above
* Java 17
* installed Appium 2.0 and above
* installed xcuitest driver
* installed uiautomator2 driver
* For Android, you should have the virtual device from Android Studio
* For iOS, you should have a simulator from Xcode and setup WebDriverAgent to simulator


# Guide:
1. Start the Appium server for Android or iOS.

    * For Android:
      ```sh
      appium -a 127.0.0.1 -p 4732 --session-override --log-timestamp --local-timezone --allow-insecure chromedriver_autodownload
      ```

    * For iOS:
      ```sh
      appium -a 0.0.0.0 -p 4723 --session-override --relaxed-security --driver-xcuitest-webdriveragent-port 8100 --log-timestamp --local-timezone
      ```

2. Put your device settings here:
   ```plaintext
   src/main/java/configs/Config.java

3. Then run 'GoogleSearchTest'
   ```plaintext
   src/test/java/GoogleSearchTest.java
   
4. After run, you can generate report put to console next line:
   ```plaintext
   allure serve build/allure-results
