package nl.meron.yaeger.engine.entities.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link Updater} contains a {@link List} of {@link Updatable} to which the {@code update} method
 * will be delegated.
 */
public class Updater implements Updatable {

    private List<Updatable> updatables = new ArrayList<>();

    /**
     * Add an {@link Updatable} to this {@link Updater} as the first element and thus be executed
     * first during an {@code update}.
     *
     * @param updatable the {@link Updatable} to be added
     */
    public void addAsFirstUpdatable(Updatable updatable) {
        updatables.add(0, updatable);
    }

    /**
     * Add an {@link Updatable} to this {@link Updater}. The {@link Updatable} will
     * be added to a {@link List}, which will be iterated on each {@code update}.
     *
     * @param updatable the {@link Updatable} to be added
     */
    public void addUpdatable(Updatable updatable) {
        updatables.add(updatable);
    }

    @Override
    public void update(long timestamp) {
        updatables.forEach(updatable -> updatable.update(timestamp));
    }
}
