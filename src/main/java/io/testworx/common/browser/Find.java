package io.testworx.common.browser;

public interface Find {
    //implement a method called find that returns a generic value
    <T> T find(String cssLocator);

}
