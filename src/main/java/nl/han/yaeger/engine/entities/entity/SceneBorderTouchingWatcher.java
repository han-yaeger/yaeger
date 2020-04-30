package nl.han.yaeger.engine.entities.entity;

import nl.han.yaeger.engine.Updatable;
import nl.han.yaeger.engine.annotations.UpdatableProvider;
import nl.han.yaeger.engine.scenes.SceneBorder;

/**
 * Implement this interface to be notified if the {@link YaegerEntity} touches the boundary of the {@link nl.han.yaeger.engine.scenes.YaegerScene}.
 * In that case, the method {@link SceneBorderTouchingWatcher#notifyBoundaryTouching(SceneBorder)}
 * will be called.
 */
public interface SceneBorderTouchingWatcher extends Bounded, SceneChild {

    /**
     * This method is being called when this {@link SceneBorderTouchingWatcher} touches a border of the {@link nl.han.yaeger.engine.scenes.YaegerScene}.
     *
     * @param border The border of the {@link nl.han.yaeger.engine.scenes.YaegerScene} the {@link YaegerEntity} is touching.
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
