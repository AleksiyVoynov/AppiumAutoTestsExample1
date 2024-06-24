package configs.app;

import java.util.List;

public class ChromeApp extends App {
    public String webViewContext;

    public ChromeApp() {
        this.name = "Chrome";
        this.appPackage = "com.android.chrome";
        this.appActivity = "com.google.android.apps.chrome.Main";
    }

    public ChromeApp(List<String> list) {
        this.webViewContext = list.stream()
                .filter(s -> s.contains("chrome"))
                .findFirst()
                .orElse(list.get(list.size() - 1));
    }
}
