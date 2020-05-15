package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.annotations.UpdatableProvider;
import com.github.hanyaeger.api.engine.scenes.SceneBorder;
import com.github.hanyaeger.api.engine.scenes.YaegerScene;

/**
 * Implement this interface to be notified if the {@link YaegerEntity} crosses the boundary of the {@link YaegerScene}.
 * In that case, the method {@link SceneBorderCrossingWatcher#notifyBoundaryCrossing(SceneBorder)}
 * will be called.
 */
public interface SceneBorderCrossingWatcher extends Bounded, SceneChild {

    /**
     * This method is being called when this {@link SceneBorderCrossingWatcher} crosses a border of the {@link YaegerScene}.
     *
     * @param border The border of the {@link YaegerScene} the {@link YaegerEntity} is crossing.
     */
    void notifyBoundaryCrossing(final SceneBorder border);

    @UpdatableProvider
    default Updatable watchForBoundaryCrossing() {
        return timestamp -> {
            if (getTransformedBounds().getMaxX() <= 0) {
                notifyBoundaryCrossing(SceneBorder.LEFT);
            } else if (getTransformedBounds().getMaxY() <= 0) {
                notifyBoundaryCrossing(SceneBorder.TOP);
            } else if (getTransformedBounds().getMinY() >= getSceneHeight()) {
                notifyBoundaryCrossing(SceneBorder.BOTTOM);
            } else if (getTransformedBounds().getMinX() >= getSceneWidth()) {
                notifyBoundaryCrossing(SceneBorder.RIGHT);
            }
        };
    }
}
