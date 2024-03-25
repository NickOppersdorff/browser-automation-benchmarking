package io.testworx.tests

import io.testworx.common.metrics.MetricsRegistry
import io.testworx.drivers.playwright.PlaywrightBrowser
import io.testworx.drivers.selenium.SeleniumBrowser
import org.spockframework.runtime.model.parallel.ExecutionMode
import spock.lang.Execution
import spock.lang.RepeatUntilFailure
import spock.lang.Specification

import static java.lang.Thread.sleep

@Execution(ExecutionMode.CONCURRENT)
class BenchmarkTest extends Specification {

    @RepeatUntilFailure(maxAttempts = 100)
    def "Benchmark Test"() {
        expect:
        MetricsRegistry.getInstance().timer("benchmark.test", "framework", browser.getDriverProtocol()).record(() -> {
            browser.open("chrome")
            browser.navigateTo("https://automationteststore.com/index.php")
            browser.type(browser.find("input#filter_keyword"), "shoes")
            browser.click(browser.find("i.fa-search"))
            browser.click(browser.find("ul.productpagecart"))
            browser.click(browser.find("a#cart_checkout1"))
        })

        cleanup:
        browser.close()

        where:
        browser                 | _
        new SeleniumBrowser()   | _
        new PlaywrightBrowser() | _
    }

    def cleanupSpec() throws InterruptedException {
        sleep(20000) //hacky wait to allow Prometheus to finish scraping metrics
        MetricsRegistry.stopRegistry()
    }
}
