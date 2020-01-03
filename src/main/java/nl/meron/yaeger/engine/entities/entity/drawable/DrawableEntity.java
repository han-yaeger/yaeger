package nl.meron.yaeger.engine.entities.entity.drawable;

import com.google.inject.Injector;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.scenes.YaegerScene;

/**
 * A {@link DrawableEntity} can be used to display a freely drawable figure in a {@link YaegerScene}.
 */
public class DrawableEntity implements Entity {

    private Shape shape;
    private Point2D initialPosition;

    /**
     * Instantiate a new {@link DrawableEntity} for the given {@link Point} and textDelegate.
     *
     * @param initialPosition the initial {@link Point} of this {@link DrawableEntity}
     * @param shape           the {@link Shape} to be used by this {@link Entity}
     */
    public DrawableEntity(final Point initialPosition, final Shape shape) {
        this.initialPosition = initialPosition;
        this.shape = shape;
    }

    @Override
    public void remove() {
        shape.setVisible(false);
        notifyRemove();
    }

    @Override
    public Node getGameNode() {
        return shape;
    }

    @Override
    public void setVisible(boolean visible) {

    }

    @Override
    public void init(Injector injector) {

    }

    @Override
    public void placeOnPosition(double x, double y) {
        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }
}
