package com.github.hanyaeger.api.engine.exceptions;

/**
 * A {@code YaegerResourceNotAvailableException} is thrown when a {@code Resource} is requested, but not available on
 * the classpath.
 */
public class ResourceNotAvailableException extends RuntimeException {

    /**
     * Instantiate a new {@code YaegerResourceNotAvailableException} for the given {@code resource}.
     *
     * @param resource the resource that is requested
     */
    public ResourceNotAvailableException(final String resource) {

        super("Resource " + resource + " can not be found. Ensure that it is placed in resources/ folder.");
    }
}
