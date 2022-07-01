package com.github.hanyaeger.core;

import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.google.inject.Injector;

/**
 * Stating an {@code Object} is {@code Initializable} exposes the {@link Initializable#init(Injector)} lifecycle hook.
 */
public interface Initializable {

    /**
     * @param injector the {@link Injector} used for Dependency Injection
     */
    void init(final Injector injector);

    /**
     * A default method to be used as a lifecycle hook to be called before a {@link YaegerEntity}
     * is initialized and added to the {@link YaegerScene}.
     * <p>
     * By default, this method is empty.
     */
    default void beforeInitialize() {
    }
}
