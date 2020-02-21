package nl.meron.yaeger.engine.entities.entity;

import com.google.inject.Injector;
import nl.meron.yaeger.engine.Timer;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link JavaFXEntity} can be used to display anything that is a child of a {@link javafx.scene.Node}.
 */
public abstract class JavaFXEntity implements Entity {

    protected double initialX;
    protected double initialY;
    private boolean visible = true;
    private List<Timer> timers = new ArrayList<>();

    /**
     * Instantiate a new {@link JavaFXEntity} for the given {@link Location} and textDelegate.
     *
     * @param initialPosition the initial {@link Location} of this {@link JavaFXEntity}
     */
    public JavaFXEntity(final Location initialPosition) {

        this.initialX = initialPosition.getX();
        this.initialY = initialPosition.getY();
    }

    private void placeOnLocation(final double x, final double y) {
        if (getGameNode() == null) {
            initialX = x;
            initialY = y;
        } else {
            setX(x);
            setY(y);
        }
    }

    @Override
    public void remove() {
        getGameNode().ifPresent(node -> {
            node.setVisible(false);
            notifyRemove();
        });
    }

    @Override
    public void setVisible(final boolean visible) {
        this.visible = visible;
        getGameNode().ifPresent(node -> {
            node.setVisible(visible);
        });
    }

    @Override
    public void init(final Injector injector) {
        setVisible(visible);
        placeOnLocation(initialX, initialY);
    }

    @Override
    public List<Timer> getTimers() {
        return timers;
    }
}
