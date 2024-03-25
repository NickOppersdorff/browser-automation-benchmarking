package io.testworx.common.browser;

public class BrowserManager {

    protected static ThreadLocal<Browser> threadDriver = new ThreadLocal<>();
    protected static ThreadLocal<String> threadSessionId = new ThreadLocal<>();


    public static Browser getBrowser() {
        return threadDriver.get();
    }

    public static void quitBrowser() {
        threadDriver.get().close();
        threadDriver.remove();
    }
}
