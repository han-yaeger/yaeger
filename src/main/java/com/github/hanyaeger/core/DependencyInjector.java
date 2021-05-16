package com.github.hanyaeger.core;

import com.google.inject.Injector;

/**
 * Implementing this interface states that this Object requires injection of dependencies.
 * It does so by exposing a {@link DependencyInjector#getInjector()} method.
 */
public interface DependencyInjector {

    /**
     * Return an {@link Injector} that can be used to inject dependencies into this {@link Object}.
     *
     * @return an instance of {@link Injector}
     */
    Injector getInjector();
}
