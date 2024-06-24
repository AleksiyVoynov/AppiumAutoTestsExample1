package configs.app;

import java.util.List;

public class SafariApp extends App {
    public String webViewContext;

    public SafariApp() {
        this.name = "Safari";
        this.bundleId = "com.apple.mobilesafari";
    }

    public SafariApp(List<String> list) {
        this.webViewContext = list.stream()
                .filter(s -> !s.contains("NATIVE") && !s.contains("WEBVIEW"))
                .findFirst()
                .orElse(list.get(list.size() - 1));
    }
}
