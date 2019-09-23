package nl.meron.yaeger.engine;

/**
 * Implementing {@code Configurable} denotes the Object can, and usually must, be configured before it can be used.
 */
@FunctionalInterface
public interface Configurable {

    /**
     * Lifecycle method used to perform configuration.
     */
    void configure();
}
