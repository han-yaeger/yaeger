package nl.meron.yaeger.engine.entities.entity;

import nl.meron.yaeger.engine.scenes.SceneBorder;

/**
 * Implement this interface to be notified if the {@link Entity} touches
 * the boundary of the scene.
 */
public interface SceneBorderTouchingWatcher extends Bounded, SceneChild {

    /**
     * This method is being called when this {@link SceneBorderTouchingWatcher} touches a border of the {@link javafx.scene.Scene}.
     *
     * @param border The border the {@link javafx.scene.Scene} is touching
     */
    void notifyBoundaryTouching(final SceneBorder border);

    @UpdatableProvider
    default Updatable watchForBoundaryTouching() {
        return timestamp -> {
            var bounds = getBounds();
            if (bounds.getMinX() <= 0) {
                notifyBoundaryTouching(SceneBorder.LEFT);
            } else if (bounds.getMinY() <= 0) {
                notifyBoundaryTouching(SceneBorder.TOP);
            } else if (bounds.getMaxY() >= getSceneHeight()) {
                notifyBoundaryTouching(SceneBorder.BOTTOM);
            } else if (bounds.getMaxX() >= getSceneWidth()) {
                notifyBoundaryTouching(SceneBorder.RIGHT);
            }
        };
    }
}
