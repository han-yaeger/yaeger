package nl.meron.yaeger.engine.entities.entity.sprites.delegates;

import javafx.scene.image.ImageView;
import nl.meron.yaeger.engine.entities.entity.SceneBoundaryCrossingWatcher;
import nl.meron.yaeger.engine.scenes.SceneBorder;

/**
 * A {@code SceneBoundaryCrossingDelegate} holds all responsibility for checking if the {@link javafx.scene.Scene}
 * boundary has been crossed.
 */
public class SceneBoundaryCrossingDelegate {

    private SceneBoundaryCrossingWatcher boundaryCrosser;

    /**
     * Instantiate a new {@code SceneBoundaryCrossingDelegate}
     *
     * @param boundaryCrosser the {@link SceneBoundaryCrossingWatcher} that should be checkeds
     */
    public SceneBoundaryCrossingDelegate(SceneBoundaryCrossingWatcher boundaryCrosser) {
        this.boundaryCrosser = boundaryCrosser;

    }

    /**
     * Check, for the given {@link ImageView} if the boundary of the {@link javafx.scene.Scene} has been
     * crossed.
     *
     * @param imageView the {@link ImageView} that should be checked
     */
    public void checkSceneBoundary(ImageView imageView) {
        var x = imageView.getX();
        var y = imageView.getY();
        var width = imageView.getLayoutBounds().getWidth();
        var height = imageView.getLayoutBounds().getHeight();
        var rightSideXCoordinate = x + width;
        var bottomYCoordinate = y + height;
        var screenBottom = imageView.getScene().getHeight();
        var screenRight = imageView.getScene().getWidth();

        if (rightSideXCoordinate <= 0) {
            boundaryCrosser.notifyBoundaryCrossing(SceneBorder.LEFT);
        } else if (bottomYCoordinate <= 0) {
            boundaryCrosser.notifyBoundaryCrossing(SceneBorder.TOP);
        } else if (y >= screenBottom) {
            boundaryCrosser.notifyBoundaryCrossing(SceneBorder.BOTTOM);
        } else if (x >= screenRight) {
            boundaryCrosser.notifyBoundaryCrossing(SceneBorder.RIGHT);
        }
    }
}
