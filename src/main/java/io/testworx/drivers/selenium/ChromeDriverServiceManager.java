package io.testworx.drivers.selenium;

import io.testworx.common.metrics.MetricsRegistry;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.IOException;

public class ChromeDriverServiceManager {

    private static final ChromeDriverServiceManager instance = new ChromeDriverServiceManager();

    ChromeDriverService service;

    private ChromeDriverServiceManager() {
        MetricsRegistry.getInstance().timer("chromeDriverService.init", "framework", "selenium").record(() -> {
            service = new ChromeDriverService.Builder()
                    .usingAnyFreePort()
                    .build();
        });

    }

    public static ChromeDriverServiceManager getInstance() {
        return instance;
    }

    public void startService() {
        MetricsRegistry.getInstance().timer("chromeDriverService.start", "framework", "selenium").record(() -> {
            try {
                service.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void stopService() {
        MetricsRegistry.getInstance().timer("chromeDriverService.stop", "framework", "selenium").record(() -> {
            service.stop();
        });
    }
}
