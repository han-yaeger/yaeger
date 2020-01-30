package nl.meron.yaeger.engine.entities.entity.shapebased;

import com.google.inject.Injector;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.entities.entity.Point;

/**
 * A {@link ShapeBasedEntity} can be used to display a {@link Shape} and is the abstract superclass
 * for all {@link Entity} that are based on a {@link Shape}
 */
public abstract class ShapeBasedEntity implements Entity {

    protected Shape shape;
    protected Point2D initialPosition;
    private boolean visible = true;

    /**
     * Instantiate a new {@link ShapeBasedEntity} for the given {@link Point} and textDelegate.
     *
     * @param initialPosition the initial {@link Point} of this {@link ShapeBasedEntity}
     */
    public ShapeBasedEntity(final Point initialPosition) {
        this(initialPosition, null);
    }

    /**
     * Instantiate a new {@link ShapeBasedEntity} for the given {@link Point} and textDelegate.
     *
     * @param initialPosition the initial {@link Point} of this {@link ShapeBasedEntity}
     * @param shape           the {@link Shape} to be used by this {@link Entity}
     */
    public ShapeBasedEntity(final Point initialPosition, final Shape shape) {
        this.initialPosition = initialPosition;
        this.shape = shape;
    }

    @Override
    public void remove() {
        if (getGameNode() != null) {
            getGameNode().setVisible(false);
            notifyRemove();
        }
    }

    @Override
    public Node getGameNode() {
        return shape;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
        if (shape != null) {
            shape.setVisible(visible);
        }
    }


    @Override
    public void init(Injector injector) {
        if (initialPosition != null) {
            placeOnPosition(initialPosition.getX(), initialPosition.getY());
        }

        setVisible(visible);
    }

    @Override
    public void placeOnPosition(double x, double y) {
        this.initialPosition = new Point2D(x, y);
        if (getGameNode() != null) {
            shape.setLayoutX(x);
            shape.setLayoutY(y);
        }
    }
}
