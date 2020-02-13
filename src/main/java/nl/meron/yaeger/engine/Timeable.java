package nl.meron.yaeger.engine;

import java.util.List;

public interface Timeable {

    /**
     * Return the {@link List} of {@link Timer} instances.
     *
     * @return the {@link List} of {@link Timer} instances
     */
    List<Timer> getTimers();
}
