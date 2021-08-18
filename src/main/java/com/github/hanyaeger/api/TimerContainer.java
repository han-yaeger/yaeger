package com.github.hanyaeger.api;

import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.core.TimerListProvider;
import com.github.hanyaeger.core.Updatable;
import com.github.hanyaeger.core.annotations.OnActivation;
import com.github.hanyaeger.core.annotations.UpdatableProvider;
import com.github.hanyaeger.core.exceptions.YaegerEngineException;
import com.github.hanyaeger.api.scenes.YaegerScene;

/**
 * An {@code TimerContainer} enables a {@link YaegerScene} and {@link YaegerEntity} to use instances of
 * {@link Timer}.
 * When implementing this interface, the {@link #setupTimers()} method needs to be implemented. The body
 * of {@link #setupTimers()} should be used to add instances of {@link Timer}, using the method
 * {@link #addTimer(Timer)}. These timers will then be registered and added to the GWU.
 * <p>
 * A {@link Timer} that is instantiated, but not added in this way, will not work.
 */
public interface TimerContainer extends TimerListProvider {

    /**
     * Use this method to add any {@link Timer} that is required by the {@link YaegerScene}
     * or {@link YaegerEntity} in which it is instantiated.
     *
     * @param timer the {@link Timer} that needs to be added
     */
    default void addTimer(final Timer timer) {
        if (getTimers() != null) {
            getTimers().add(timer);
        } else {
            throw new YaegerEngineException("getTimers() returns null, please return an instance of ArrayList<>");
        }
    }

    /**
     * Annotated with {@link OnActivation}, this method will be called during activation. First it will clear
     * any timers from a previous activation, after which it will call {@link #setupTimers()}.
     */
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

    /**
     * Return an {@link Updatable} that, when called, call all the timers that were
     * added to this {@code TimerContainer}.
     *
     * @return an {@link Updatable} that delegates the {@link Updatable#update(long)} to
     * all timers
     */
    @UpdatableProvider
    default Updatable callTimers() {
        return timestamp -> {
            if (getTimers() != null && !getTimers().isEmpty()) {
                // remove all timers that have been marked as garbage
                getTimers().removeIf(Timer::isGarbage);
                // call handle on all timers
                getTimers().forEach(timer -> timer.handle(timestamp));
            }
        };
    }
}
