package nl.meron.yaeger.engine.entities.entity;

import com.google.inject.Injector;
import javafx.geometry.Point2D;

/**
 * A {@link JavaFXEntity} can be used to display anything that is a child of a {@link javafx.scene.Node}.
 */
public abstract class JavaFXEntity implements Entity {

    protected Point2D initialPosition;
    private boolean visible = true;

    /**
     * Instantiate a new {@link JavaFXEntity} for the given {@link Location} and textDelegate.
     *
     * @param initialPosition the initial {@link Location} of this {@link JavaFXEntity}
     */
    public JavaFXEntity(final Location initialPosition) {
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
