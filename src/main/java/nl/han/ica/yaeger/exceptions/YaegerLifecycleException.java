package nl.han.ica.yaeger.exceptions;

/**
 * A YaegerLifecycleException is thrown when a method is called that breaks te standard lifecycles of Yaeger.
 */
public class YaegerLifecycleException extends RuntimeException {

    /**
     * Create a new YaegerLifecycleException with the given message.
     *
     * @param message The message why this exception was thrown
     */
    public YaegerLifecycleException(String message) {
        super(message);
    }
}
