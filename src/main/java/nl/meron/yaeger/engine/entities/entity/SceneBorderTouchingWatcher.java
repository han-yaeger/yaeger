package nl.meron.yaeger.engine.entities.entity;

import nl.meron.yaeger.engine.Updatable;
import nl.meron.yaeger.engine.annotations.UpdatableProvider;
import nl.meron.yaeger.engine.scenes.SceneBorder;

/**
 * Implement this interface to be notified if the {@link Entity} touches the boundary of the {@link nl.meron.yaeger.engine.scenes.YaegerScene}.
 * In that case, the method {@link SceneBorderTouchingWatcher#notifyBoundaryTouching(SceneBorder)}
 * will be called.
 */
public interface SceneBorderTouchingWatcher extends Bounded, SceneChild {

    /**
     * This method is being called when this {@link SceneBorderTouchingWatcher} touches a border of the {@link nl.meron.yaeger.engine.scenes.YaegerScene}.
     *
     * @param border The border of the {@link nl.meron.yaeger.engine.scenes.YaegerScene} the {@link Entity} is touching.
     */
    void notifyBoundaryTouching(final SceneBorder border);

    @UpdatableProvider
    default Updatable watchForBoundaryTouching() {
        return timestamp -> {
            if (getTransformedBounds().getMinX() <= 0) {
                notifyBoundaryTouching(SceneBorder.LEFT);
            } else if (getTransformedBounds().getMinY() <= 0) {
                notifyBoundaryTouching(SceneBorder.TOP);
            } else if (getTransformedBounds().getMaxY() >= getSceneHeight()) {
                notifyBoundaryTouching(SceneBorder.BOTTOM);
            } else if (getTransformedBounds().getMaxX() >= getSceneWidth()) {
                notifyBoundaryTouching(SceneBorder.RIGHT);
            }
        };
    }
}
