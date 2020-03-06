package nl.meron.yaeger.engine;

import com.google.inject.Injector;

/**
 * Implementing {@link Configurable} denotes the Object can, and usually must, be configured before it can be used.
 * The {@link Configurable} interface has tight connections to the {@link Initializable} interface,  with
 * the main difference that {@link Initializable#init(Injector)} requires an {@link Injector} as its parameter and
 * should thus be used to bring an Object into a valid state. A {@link Configurable} can then be used to
 * set the behaviour of such an Object.
 */
public interface Configurable {

    /**
     * Lifecycle method used to perform configuration.
     */
    void configure();
}
