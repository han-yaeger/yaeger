package nl.meron.yaeger.engine.entities.entity;

import com.google.inject.Injector;
import javafx.geometry.Point2D;
import nl.meron.yaeger.engine.Timer;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link JavaFXEntity} can be used to display anything that is a child of a {@link javafx.scene.Node}.
 */
public abstract class JavaFXEntity implements Entity {

    protected Point2D initialPosition;
    private boolean visible = true;
    private List<Timer> timers = new ArrayList<>();

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
    public void setVisible(final boolean visible) {
        this.visible = visible;
        if (getGameNode() != null) {
            getGameNode().setVisible(visible);
        }
    }

    @Override
    public void init(final Injector injector) {
        setVisible(visible);
        placeOnLocation(initialPosition.getX(), initialPosition.getY());
    }

    @Override
    public List<Timer> getTimers() {
        return timers;
    }
}
