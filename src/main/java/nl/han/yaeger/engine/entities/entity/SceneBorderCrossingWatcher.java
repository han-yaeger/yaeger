package nl.han.yaeger.engine.entities.entity;

import nl.han.yaeger.engine.Updatable;
import nl.han.yaeger.engine.annotations.UpdatableProvider;
import nl.han.yaeger.engine.scenes.SceneBorder;

/**
 * Implement this interface to be notified if the {@link Entity} crosses the boundary of the {@link nl.han.yaeger.engine.scenes.YaegerScene}.
 * In that case, the method {@link SceneBorderCrossingWatcher#notifyBoundaryCrossing(SceneBorder)}
 * will be called.
 */
public interface SceneBorderCrossingWatcher extends Bounded, SceneChild {

    /**
     * This method is being called when this {@link SceneBorderCrossingWatcher} crosses a border of the {@link nl.han.yaeger.engine.scenes.YaegerScene}.
     *
     * @param border The border of the {@link nl.han.yaeger.engine.scenes.YaegerScene} the {@link Entity} is crossing.
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
