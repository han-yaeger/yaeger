package nl.meron.yaeger.engine;

import java.util.List;

/**
 * Implementing the {@link WithTimerList} interface guarantees that a {@link List} of
 * instances of {@link Timer} is available.
 */
public interface WithTimerList {

    /**
     * Return the {@link List} of {@link Timer} instances.
     *
     * @return the {@link List} of {@link Timer} instances
     */
    List<Timer> getTimers();
}
