package nl.han.yaeger.engine;

import com.google.inject.Injector;

/**
 * Implementing {@link Activatable} denotes the Object can, and usually must, be activated before it can be used.
 * The {@link Activatable} interface has tight connections to the {@link Initializable} interface,  with
 * the main difference that {@link Initializable#init(Injector)} requires an {@link Injector} as its parameter and
 * should thus be used to bring an Object into a valid state. A {@link Activatable} can then be used to
 * set the behaviour of such an Object.
 */
public interface Activatable {

    /**
     * Lifecycle method used to perform activation of this Game Object.
     */
    void activate();
}
