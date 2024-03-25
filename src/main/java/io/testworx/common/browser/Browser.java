package io.testworx.common.browser;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

public abstract class Browser implements Open, Close, Navigate, Find, Click, Type {

    protected String driverProtocol;
    public String getDriverProtocol() {
        return driverProtocol;
    }
}
