package nl.han.yaeger.engine;

import com.google.inject.Injector;

/**
 * Implementing this interface states that this Object requires injection of dependencies.
 * It does so by exposing a {@link DependencyInjector#getInjector()} method.
 */
public interface DependencyInjector {

    /**
     * Return an {@link Injector} that can be used to inject dependencies into this {@link Object}.
     *
     * @return An instance of {@link Injector}.
     */
    Injector getInjector();
}
