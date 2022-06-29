package com.github.hanyaeger.core;

import com.github.hanyaeger.api.scenes.YaegerScene;
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
     * Lifecycle method used to perform activation of this Game Object. By default, this method is empty, so it should
     * be implemented by the Game Object, if desired.
     */
    default void activate() {
    }

    /**
     * Return whether this {@link YaegerScene} has completed activation.
     *
     * @return a {@code boolean} value that states whether this {@link YaegerScene} has finished activation
     */
    default boolean isActivationComplete() {
        return false;
    }
}
