package com.github.hanyaeger.api.engine;

import com.google.inject.Injector;

/**
 * Stating an {@code Object} is {@link Initializable} exposes the {@code init} lifecycle hook.
 */
public interface Initializable {

    /**
     * @param injector the {@link Injector} used for Dependency Injection
     */
    void init(final Injector injector);

    /**
     * A default method to be used as a lifecycle hook to be called before a {@link com.github.hanyaeger.api.engine.entities.entity.YaegerEntity}
     * is initialized and added to the {@link com.github.hanyaeger.api.engine.scenes.YaegerScene}.
     * <p>
     * By default this method is empty.
     */
    default void beforeInitialize() {
    }
}
