package com.github.hanyaeger.core.exceptions;

/**
 * A {@code YaegerResourceNotAvailableException} is thrown when a {@code Resource} is requested, but not available on
 * the classpath.
 */
public class ResourceNotAvailableException extends RuntimeException {

    /**
     * The error message to be shown when this exception occurs.
     */
    static final String MESSAGE = "Resource %s can not be found. Ensure that it is placed in resources/ folder. If it has" +
            " been placed in a sub-folder of the resource folder, ensure that this folder has been opened through the module-descriptor";

    /**
     * Instantiate a new {@code YaegerResourceNotAvailableException} for the given {@code resource}.
     *
     * @param resource the resource that is requested
     */
    public ResourceNotAvailableException(final String resource) {

        super(String.format(MESSAGE, resource));
    }
}
