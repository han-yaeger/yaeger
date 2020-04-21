package nl.han.yaeger.engine;

import nl.han.yaeger.engine.annotations.OnActivation;
import nl.han.yaeger.engine.annotations.UpdatableProvider;
import nl.han.yaeger.engine.exceptions.YaegerEngineException;

/**
 * When implementing this interface, the {@link #setupTimers()} method needs to be implemented.
 * This interface can be used with both a {@link nl.han.yaeger.engine.scenes.YaegerScene} and en
 * {@link nl.han.yaeger.engine.entities.entity.Entity} and ensures that the method {@link #setupTimers()}
 * is being called during initialization of such an object.
 * <p>
 * THe body of {@link #setupTimers()} should be used to add instances of {@link Timer}, using the exposed
 * method  {@link #addTimer(Timer)}. These timers will then be registered and added to the Game-loop.
 * <p>
 * A {@link Timer} that is instantiated, but not added in this way, will not work.
 */
public interface WithTimers extends TimerListProvider {

    /**
     * Use this method to add any {@link Timer} that is required by the {@link nl.han.yaeger.engine.scenes.YaegerScene}
     * or {@link nl.han.yaeger.engine.entities.entity.Entity} in which it is instantiated.
     *
     * @param timer The {@link Timer} that needs to be added.
     */
    default void addTimer(Timer timer) {
        if (getTimers() != null) {
            getTimers().add(timer);
        } else {
            throw new YaegerEngineException("getTimers() returns null, please return an instance of ArrayList<>");
        }
    }

    @OnActivation
    default void initTimers() {
        getTimers().clear();
        setupTimers();
    }

    /**
     * Only instances of {@link Timer} that are registered with the method {@link #addTimer(Timer)}
     * within this method are registered and will receive an animation update.
     */
    void setupTimers();

    @UpdatableProvider
    default Updatable callTimers() {
        return timestamp -> {
            if (getTimers() != null && !getTimers().isEmpty()) {
                getTimers().forEach(timer -> timer.handle(timestamp));
            }
        };
    }
}
