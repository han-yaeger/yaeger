package com.github.hanyaeger.core.exceptions;

/**
 * A {@link YaegerLifecycleException} indicates that a method is called that does not abide the lifecycle
 * of Yaeger.
 */
public class YaegerLifecycleException extends RuntimeException {

    /**
     * Create a new {@link YaegerLifecycleException} with the given message.
     *
     * @param message the message that should be shown when the exception is thrown
     */
    public YaegerLifecycleException(final String message) {
        super(message);
    }
}
