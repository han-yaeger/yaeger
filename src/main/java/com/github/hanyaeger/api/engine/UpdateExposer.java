package com.github.hanyaeger.api.engine;

import com.github.hanyaeger.api.engine.annotations.UpdatableProvider;

/**
 * The interface {@link UpdateExposer} can be applied to any dynamic {@link com.github.hanyaeger.api.engine.entities.entity.YaegerEntity}
 * or {@link com.github.hanyaeger.api.engine.scenes.YaegerScene}. By default, both of those hide their
 * {@link Updatable#update(long)} method.
 * <p>
 * When implementing {@link UpdateExposer}, the {@link Updatable#update(long)} becomes exposed.
 * <p>
 * Note that this should not be used for timing events. In such cases a {@link Timer} should be used.
 * <p>
 * The {@link #exposedUpdate()} will be called after the internal {@link Updatable#update(long)} has been
 * called.
 */
public interface UpdateExposer {

    @UpdatableProvider
    default Updatable exposedUpdate() {
        return timestamp -> {
            explicitUpdate(timestamp);
        };
    }

    /**
     * An explicit update that can be used from either a {@link com.github.hanyaeger.api.engine.entities.entity.YaegerEntity}
     * or a {@link com.github.hanyaeger.api.engine.scenes.YaegerScene}.
     *
     * @param timestamp The timestamp of this update.
     */
    void explicitUpdate(final long timestamp);
}
