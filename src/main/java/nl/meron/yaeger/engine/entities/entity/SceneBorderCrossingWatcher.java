package nl.meron.yaeger.engine.entities.entity;

import nl.meron.yaeger.engine.Updatable;
import nl.meron.yaeger.engine.annotations.UpdatableProvider;
import nl.meron.yaeger.engine.scenes.SceneBorder;

/**
 * Implement this interface to be notified if the {@link nl.meron.yaeger.engine.entities.entity.Entity} completely crosses
 * the boundary of the scene.
 */
public interface SceneBorderCrossingWatcher extends Bounded, SceneChild {

    /**
     * This method is being called when this {@link SceneBorderCrossingWatcher} crosses a border of the {@link javafx.scene.Scene}.
     *
     * @param border The border at which the {@link javafx.scene.Scene} is being crossed
     */
    void notifyBoundaryCrossing(final SceneBorder border);

    @UpdatableProvider
    default Updatable watchForBoundaryCrossing() {
        return timestamp -> {
            if (getBounds().getMaxX() <= 0) {
                notifyBoundaryCrossing(SceneBorder.LEFT);
            } else if (getBounds().getMaxY() <= 0) {
                notifyBoundaryCrossing(SceneBorder.TOP);
            } else if (getBounds().getMinY() >= getSceneHeight()) {
                notifyBoundaryCrossing(SceneBorder.BOTTOM);
            } else if (getBounds().getMinX() >= getSceneWidth()) {
                notifyBoundaryCrossing(SceneBorder.RIGHT);
            }
        };
    }
}
