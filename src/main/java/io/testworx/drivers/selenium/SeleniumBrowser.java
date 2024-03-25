package io.testworx.drivers.selenium;

import io.testworx.common.browser.Browser;
import io.testworx.common.metrics.MetricsRegistry;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.atomic.AtomicReference;

public class SeleniumBrowser extends Browser {
    WebDriver browser;
    String browserName;

    public SeleniumBrowser() {
        driverProtocol = "selenium";
    }

    @Override
    public WebElement find(String cssLocator) {
        AtomicReference<WebElement> element = new AtomicReference<>();
        MetricsRegistry.getInstance().timer("browser.find", "framework", driverProtocol, "selector", cssLocator).record(() ->
                element.set(browser.findElement(By.cssSelector(cssLocator)))
        );
        return element.get();
    }

    @Override
    public void open(String browserName) {
        this.browserName = browserName;
        MetricsRegistry.getInstance().timer("browser.open", "framework", driverProtocol, "browser", browserName).record(() -> {
            if (browserName.equals("chrome")) {
                switch(browserName) {
                    case "chrome":
                        browser = new ChromeDriver();
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid browser name");
                }
            }
        });
    }

    @Override
    public void close() {
        MetricsRegistry.getInstance().timer("browser.close", "framework", driverProtocol).record(() ->
                browser.quit()
        );
    }

    @Override
    public <T> void click(T element) {
        WebElement webElement = (WebElement) element;
        MetricsRegistry.getInstance().timer("browser.click", "framework", driverProtocol).record(() ->
                webElement.click()
        );
    }

    @Override
    public <T> void type(T element, String text) {
        WebElement webElement = (WebElement) element;
        MetricsRegistry.getInstance().timer("browser.type", "framework", driverProtocol).record(() ->
                webElement.sendKeys(text)
        );
    }

    @Override
    public void navigateTo(String url) {
        MetricsRegistry.getInstance().timer("browser.navigate", "framework", driverProtocol).record(() ->
                browser.get(url)
        );
    }
}
