package com.github.hanyaeger.core;

import com.github.hanyaeger.api.Timer;

import java.util.List;

/**
 * Implementing the {@link TimerListProvider} interface guarantees that a {@link List} of
 * instances of {@link Timer} is available.
 */
public interface TimerListProvider {

    /**
     * Return the {@link List} of {@link Timer} instances.
     *
     * @return the {@link List} of {@link Timer} instances
     */
    List<Timer> getTimers();
}
