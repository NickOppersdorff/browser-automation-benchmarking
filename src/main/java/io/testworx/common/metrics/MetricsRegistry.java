package io.testworx.common.metrics;

import com.sun.net.httpserver.HttpServer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.Instant;

public class MetricsRegistry {
    //create a singleton instance of the MetricsRegistry
    private static final MetricsRegistry instance = new MetricsRegistry();
    private final PrometheusMeterRegistry registry;
    private HttpServer server;
    //private constructor to prevent instantiation
    private MetricsRegistry() {
         registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
        registry.config().commonTags("testRun", Instant.now().toString());
        startRegistry(registry);
    }

    private void startRegistry(PrometheusMeterRegistry prometheusRegistry) {
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/metrics", httpExchange -> {
                System.out.println("Received request for metrics");
                String response = prometheusRegistry.scrape();
                httpExchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    System.out.println("Sending response");
                    System.out.println(response);
                    os.write(response.getBytes());
                }
            });

            new Thread(server::start).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        try {
////            DefaultExports.initialize();
////            HTTPServer server = HTTPServer.builder()
////                    .port(8080).registry(registry)
////                    .buildAndStart();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    //return the singleton instance
    public static MeterRegistry getInstance() {
        return instance.registry;
    }

    public static void stopRegistry() {
        instance.server.stop(0);
    }
}
