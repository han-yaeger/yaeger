package com.github.hanyaeger.core;

/**
 * An {@link Updatable} that delegates the {@link Updatable#update(long)} to an {@link Updater}.
 */
public interface UpdateDelegator extends Updatable {

    /**
     * Get the {@link Updater} to which the {@code update} should be delegated.
     * <p>
     * Note: This method is part of the internal API, and should not be used when implementing a Yaeger game.
     *
     * @return an instance of {@link Updater}
     */
    Updater getUpdater();
}
