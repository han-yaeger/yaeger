package com.github.hanyaeger.api;

import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.github.hanyaeger.core.Updatable;
import com.github.hanyaeger.core.annotations.UpdatableProvider;

/**
 * An {@link UpdateExposer} can be applied to any dynamic {@link YaegerEntity}
 * or {@link YaegerScene} and makes it possible to explicitly use the GWU.
 * <p>
 * By default, both of those hide their GWU (or {@link Updatable#update(long)} method).
 * When implementing {@link UpdateExposer}, the GWU becomes exposed through the method {@link #explicitUpdate(long)}.
 * <p>
 * Note that this should not be used for timing events. In such cases a {@link Timer} should be used.
 * <p>
 * Note that the {@link #exposedUpdate()} will be called after the internal {@link Updatable#update(long)} has been
 * called.
 */
public interface UpdateExposer {

    /**
     * Delegates the {@link Updatable#update(long)} to the {@link UpdateExposer#explicitUpdate(long)}.
     *
     * @return an {@link Updatable}
     */
    @UpdatableProvider
    default Updatable exposedUpdate() {
        return this::explicitUpdate;
    }

    /**
     * An explicit update that can be used from either a {@link YaegerEntity}
     * or a {@link YaegerScene}.
     *
     * @param timestamp the timestamp of this update
     */
    void explicitUpdate(final long timestamp);
}
