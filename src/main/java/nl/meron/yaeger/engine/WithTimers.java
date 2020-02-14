package nl.meron.yaeger.engine;

import nl.meron.yaeger.engine.annotations.Initializer;
import nl.meron.yaeger.engine.entities.entity.Updatable;
import nl.meron.yaeger.engine.annotations.UpdatableProvider;

/**
 * Implementing this interface exposes the {@link #registerTimer(Timer)} method. A {@link Timer} that is
 * instantiated, but not registered, will not work.
 */
public interface WithTimers extends Timeable {

    /**
     * Use this method to register any {@link Timer} that is required by the {@link nl.meron.yaeger.engine.scenes.YaegerScene}
     * or {@link nl.meron.yaeger.engine.entities.entity.Entity} in which it is instantiated.
     */
    default void registerTimer(Timer timer) {
        getTimers().add(timer);
    }

    @Initializer
    default void init() {
        registerTimers();
    }

    /**
     * Only instances of {@link Timer} that are registered with the method {@link #registerTimer(Timer)}
     * within this method are registered and each animation update.
     */
    void registerTimers();

    @UpdatableProvider
    default Updatable callTimers() {
        return timestamp -> {
            getTimers().forEach(timer -> timer.handle(timestamp));
        };
    }
}
