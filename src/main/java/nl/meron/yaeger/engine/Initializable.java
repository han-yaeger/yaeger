package nl.meron.yaeger.engine;

import com.google.inject.Injector;

/**
 * Stating an {@code Object} is {@link Initializable} exposes the {@code init} lifecycle hook.
 */
public interface Initializable {

    /**
     * @param injector the {@link Injector} used for Dependency Injection
     */
    void init(Injector injector);
}
