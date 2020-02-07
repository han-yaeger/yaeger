package nl.meron.yaeger.engine.entities.entity.shapebased;

import com.google.inject.Injector;
import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.entities.entity.Point;

/**
 * A {@link ShapeEntity} can be used to display a {@link Shape} and is the abstract superclass
 * for all {@link Entity} that are based on a {@link Shape}
 */
public abstract class ShapeEntity implements Entity {

    protected Point2D initialPosition;
    private boolean visible = true;

    /**
     * Instantiate a new {@link ShapeEntity} for the given {@link Point} and textDelegate.
     *
     * @param initialPosition the initial {@link Point} of this {@link ShapeEntity}
     */
    public ShapeEntity(final Point initialPosition) {
        this.initialPosition = initialPosition;
    }

    @Override
    public void remove() {
        if (getGameNode() != null) {
            getGameNode().setVisible(false);
            notifyRemove();
        }
    }


    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
        if (getGameNode() != null) {
            getGameNode().setVisible(visible);
        }
    }

    @Override
    public void init(Injector injector) {
        setVisible(visible);
        placeOnPosition(initialPosition.getX(), initialPosition.getY());
    }
}
