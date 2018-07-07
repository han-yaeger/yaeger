package nl.han.ica.yaeger.exceptions;

/**
 * A YaegerResourceNotAvailableException is thrown when a resource is requested, but it is not available..
 */
public class YaegerResourceNotAvailableException extends RuntimeException {

    /**
     * Create a new YaegerResourceNotAvailableException for the given resource.
     *
     * @param resource The requested resource
     */
    public YaegerResourceNotAvailableException(String resource) {

        super("Resource " + resource + " can not be found. Make sure this file is placed in the resource/ folder.");
    }
}
