package nl.meron.yaeger.engine.entities.entity.updatetasks;

import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.scenes.SceneBorder;

/**
 * Implement this interface to be notified if the {@link Entity} completely crosses
 * the boundary of the scene.
 */
public interface SceneBoundaryCrossingWatcher extends Entity, EntityUpdateTaskSupplier {

    /**
     * This method is being called when this {@link SceneBoundaryCrossingWatcher} crosses a boundary of the {@link javafx.scene.Scene}.
     *
     * @param border The border at which the {@link javafx.scene.Scene} is being crossed
     */
    void notifyBoundaryCrossing(SceneBorder border);

    default EntityUpdateTask getTask() {
        return () -> watch();
    }

    private void watch() {
        if (getRightSideXCoordinate() <= 0) {
            notifyBoundaryCrossing(SceneBorder.LEFT);
        } else if (getBottomYCoordinate() <= 0) {
            notifyBoundaryCrossing(SceneBorder.TOP);
        } else if (getTopYCoordinate() >= getSceneHeight()) {
            notifyBoundaryCrossing(SceneBorder.BOTTOM);
        } else if (getLeftSideXCoordinate() >= getSceneWidth()) {
            notifyBoundaryCrossing(SceneBorder.RIGHT);
        }
    }
}
