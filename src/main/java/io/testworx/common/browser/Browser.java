package io.testworx.common.browser;

public abstract class Browser implements Open, Close, Navigate, Find, Click, Type {

    protected String driverProtocol;
    public String getDriverProtocol() {
        return driverProtocol;
    }
}
