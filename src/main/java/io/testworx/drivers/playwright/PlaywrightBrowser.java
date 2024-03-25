package io.testworx.drivers.playwright;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.testworx.common.browser.Browser;
import io.testworx.common.metrics.MetricsRegistry;


import java.util.concurrent.atomic.AtomicReference;

public class PlaywrightBrowser extends Browser {
    Playwright playwright;
    Page page;
    com.microsoft.playwright.Browser browser;
    String browserName;

    public PlaywrightBrowser() {
        playwright = Playwright.create();
        driverProtocol = "playwright";
    }

    @Override
    public void open(String browserName) {
        this.browserName = browserName;
        MetricsRegistry.getInstance().timer("browser.open", "framework", driverProtocol, "browser", browserName).record(() -> {
            switch(browserName) {
                case "chrome":
                    browser = playwright.chromium().launch();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid browser name");
            }
        });
    }

    @Override
    public void close() {
        MetricsRegistry.getInstance().timer("browser.close", "framework", driverProtocol).record(() ->
                browser.close()
        );
    }

    @Override
    public Locator find(String cssLocator) {
        AtomicReference<Locator> element = new AtomicReference<>();
        MetricsRegistry.getInstance().timer("browser.find", "framework", driverProtocol, "selector", cssLocator).record(() ->
                element.set(page.locator(cssLocator))
        );
        return element.get();
    }

    @Override
    public <T> void click(T element) {
        Locator webElement = (Locator) element;
        MetricsRegistry.getInstance().timer("browser.click", "framework", driverProtocol).record(() ->
                webElement.click()
        );
    }

    @Override
    public <T> void type(T element, String text) {
        Locator webElement = (Locator) element;
        MetricsRegistry.getInstance().timer("browser.type", "framework", driverProtocol).record(() ->
                webElement.fill(text)
        );
    }

    @Override
    public void navigateTo(String url) {
        page = browser.newPage();
        MetricsRegistry.getInstance().timer("browser.navigate", "framework", driverProtocol).record(() ->
                page.navigate(url)
        );
    }
}
