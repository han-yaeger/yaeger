package nl.meron.yaeger.engine.entities.entity.updatetasks;

import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.scenes.SceneBorder;

/**
 * Implement this interface to be notified if the {@link Entity} completely crosses
 * the boundary of the scene.
 */
public interface SceneBoundaryCrossingWatcher extends EntityUpdateTaskSupplier {

    /**
     * This method is being called when this {@link SceneBoundaryCrossingWatcher} crosses a boundary of the {@link javafx.scene.Scene}.
     *
     * @param border The border at which the {@link javafx.scene.Scene} is being crossed
     */
    void notifyBoundaryCrossing(SceneBorder border);

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

        if (rightSideXCoordinate <= 0) {
            notifyBoundaryCrossing(SceneBorder.LEFT);
        } else if (bottomYCoordinate <= 0) {
            notifyBoundaryCrossing(SceneBorder.TOP);
        } else if (y >= screenBottom) {
            notifyBoundaryCrossing(SceneBorder.BOTTOM);
        } else if (x >= screenRight) {
            notifyBoundaryCrossing(SceneBorder.RIGHT);
        }
    }
}
