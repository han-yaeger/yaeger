package nl.meron.yaeger.engine.entities.entity.updatetasks;

import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.scenes.SceneBorder;

/**
 * Implement this interface to be notified if the {@link Entity} touches
 * the boundary of the scene.
 */
public interface SceneBoundaryTouchingWatcher extends EntityUpdateTaskSupplier {

    /**
     * This method is being called when this {@link SceneBoundaryCrossingWatcher} crosses a boundary of the {@link javafx.scene.Scene}.
     *
     * @param border The border at which the {@link javafx.scene.Scene} is being crossed
     */
    void notifyBoundaryTouching(SceneBorder border);

    default EntityUpdateTask getTask() {
        return (entity) -> watch(entity);
    }

    private void watch(Entity entity) {
        var x = entity.getPosition().getX();
        var y = entity.getPosition().getY();
        var width = entity.getBounds().getWidth();
        var height = entity.getBounds().getHeight();
        var rightSideXCoordinate = x + width;
        var bottomYCoordinate = y + height;
        var screenBottom = entity.getSceneHeight();
        var screenRight = entity.getSceneWidth();

        if (x <= 0) {
            notifyBoundaryTouching(SceneBorder.LEFT);
        } else if (y <= 0) {
            notifyBoundaryTouching(SceneBorder.TOP);
        } else if (bottomYCoordinate >= screenBottom) {
            notifyBoundaryTouching(SceneBorder.BOTTOM);
        } else if (rightSideXCoordinate >= screenRight) {
            notifyBoundaryTouching(SceneBorder.RIGHT);
        }
    }
}
