package io.testworx.common.browser;

/**
 * Click interface that enables us to standardise behaviour between different browser automation tools.

 */
public interface Click {

    <T> void click(T element);
}
