package com.github.hanyaeger.api.entities;

import com.github.hanyaeger.core.entities.Bounded;
import com.github.hanyaeger.core.entities.SceneChild;
import com.github.hanyaeger.core.Updatable;
import com.github.hanyaeger.core.annotations.UpdatableProvider;
import com.github.hanyaeger.core.entities.motion.Movable;
import com.github.hanyaeger.api.scenes.SceneBorder;
import com.github.hanyaeger.api.scenes.YaegerScene;

/**
 * Implement this interface to be notified if the {@link YaegerEntity} touches the boundary of the {@link YaegerScene}.
 * In that case, the method {@link SceneBorderTouchingWatcher#notifyBoundaryTouching(SceneBorder)}
 * will be called.
 */
public interface SceneBorderTouchingWatcher extends Movable, Bounded, SceneChild {

    /**
     * This method is being called when this {@link SceneBorderTouchingWatcher} touches a border of the {@link YaegerScene}.
     *
     * @param border the border of the {@link YaegerScene} the {@link YaegerEntity} is touching
     */
    void notifyBoundaryTouching(final SceneBorder border);

    @UpdatableProvider
    default Updatable watchForBoundaryTouching() {
        return timestamp -> {
            if (getBoundingBox().getMinX() <= 0) {
                handleTouch(SceneBorder.LEFT);
            } else if (getBoundingBox().getMinY() <= 0) {
                handleTouch(SceneBorder.TOP);
            } else if (getBoundingBox().getMaxY() >= getSceneHeight()) {
                handleTouch(SceneBorder.BOTTOM);
            } else if (getBoundingBox().getMaxX() >= getSceneWidth()) {
                handleTouch(SceneBorder.RIGHT);
            }
        };
    }

    private void handleTouch(final SceneBorder border) {
        notifyBoundaryTouching(border);
    }
}
