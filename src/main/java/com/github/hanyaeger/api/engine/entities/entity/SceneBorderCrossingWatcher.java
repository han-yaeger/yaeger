package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.annotations.UpdatableProvider;
import com.github.hanyaeger.api.engine.entities.entity.motion.Moveable;
import com.github.hanyaeger.api.engine.scenes.SceneBorder;
import com.github.hanyaeger.api.engine.scenes.YaegerScene;

/**
 * Implement this interface to be notified if the {@link YaegerEntity} crosses the boundary of the {@link YaegerScene}.
 * In that case, the method {@link SceneBorderCrossingWatcher#notifyBoundaryCrossing(SceneBorder)}
 * will be called.
 */
public interface SceneBorderCrossingWatcher extends Bounded, SceneChild, Moveable {

    /**
     * This method is being called when this {@link SceneBorderCrossingWatcher} crosses a border of the {@link YaegerScene}.
     *
     * @param border the border of the {@link YaegerScene} the {@link YaegerEntity} is crossing
     */
    void notifyBoundaryCrossing(final SceneBorder border);

    @UpdatableProvider
    default Updatable watchForBoundaryCrossing() {
        return timestamp -> {
            if (getBoundingBox().getMaxX() <= 0) {
                handleCrossing(SceneBorder.LEFT);
            } else if (getBoundingBox().getMaxY() <= 0) {
                handleCrossing(SceneBorder.TOP);
            } else if (getBoundingBox().getMinY() >= getSceneHeight()) {
                handleCrossing(SceneBorder.BOTTOM);
            } else if (getBoundingBox().getMinX() >= getSceneWidth()) {
                handleCrossing(SceneBorder.RIGHT);
            }
        };
    }

    private void handleCrossing(SceneBorder border) {
        notifyBoundaryCrossing(border);
    }
}
