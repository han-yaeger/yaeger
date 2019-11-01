package nl.meron.yaeger.engine.entities.entity;

/**
 * An {@link Updatable} that delegates the {@code update} to an {@link Updater}.
 */
public interface UpdateDelegator extends Updatable {

    /**
     * Get the {@link Updater} to which the {@code update} should be delegated.
     *
     * @return an instance of {@link Updater}
     */
    Updater getUpdater();
}
