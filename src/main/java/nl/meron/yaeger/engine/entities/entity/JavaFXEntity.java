package nl.meron.yaeger.engine.entities.entity;

import com.google.inject.Injector;
import javafx.scene.Node;
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
    private AnchorPoint anchorPoint;

    /**
     * Instantiate a new {@link JavaFXEntity} for the given {@link Location} and textDelegate.
     *
     * @param initialPosition the initial {@link Location} of this {@link JavaFXEntity}
     */
    public JavaFXEntity(final Location initialPosition) {
        this.initialX = initialPosition.getX();
        this.initialY = initialPosition.getY();
        this.anchorPoint = AnchorPoint.TOP_LEFT;
    }

    /**
     * Set the {@link AnchorPoint} of this {@link Entity}. The {@link AnchorPoint} will be used
     * to set the given x, y-coordinate. By default an {@link Entity} will use the top-left as
     * its anchorpoint.
     *
     * @param anchorPoint The {@link AnchorPoint} of this {@link Entity}.
     */
    public void setAnchorPoint(AnchorPoint anchorPoint) {
        getGameNode().ifPresentOrElse(node -> {
                    applyTranslationsForAnchorPoint(node, anchorPoint);
                }, () ->
                        this.anchorPoint = anchorPoint
        );
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

        getGameNode().ifPresentOrElse(node -> {
            node.setVisible(visible);
        }, () -> this.visible = visible);
    }

    @Override
    public void init(final Injector injector) {
        setVisible(visible);
    }

    @Override
    public List<Timer> getTimers() {
        return timers;
    }

    @Override
    public AnchorPoint getAnchorPoint() {
        return anchorPoint;
    }

    @Override
    public void placeOnScene() {
        getGameNode().ifPresent(node -> {
            setOriginX(initialX);
            setOriginY(initialY);
            applyTranslationsForAnchorPoint(node, anchorPoint);
        });
    }

    private void applyTranslationsForAnchorPoint(Node node, AnchorPoint anchorPoint) {
        switch (anchorPoint) {
            case TOP_CENTER:
                node.setTranslateX(-getNonTransformedBounds().getWidth() / 2);
                break;
            case TOP_RIGHT:
                node.setTranslateX(-getNonTransformedBounds().getWidth());
                break;
            case LEFT_CENTER:
                node.setTranslateY(-getNonTransformedBounds().getHeight() / 2);
                break;
            case CENTER_CENTER:
                node.setTranslateX(-getNonTransformedBounds().getWidth() / 2);
                node.setTranslateY(-getNonTransformedBounds().getHeight() / 2);
                break;
            case RIGHT_CENTER:
                node.setTranslateX(-getNonTransformedBounds().getWidth());
                node.setTranslateY(-getNonTransformedBounds().getHeight() / 2);
                break;
            case BOTTOM_LEFT:
                node.setTranslateY(-getNonTransformedBounds().getHeight());
                break;
            case BOTTOM_CENTER:
                node.setTranslateX(-getNonTransformedBounds().getWidth() / 2);
                node.setTranslateY(-getNonTransformedBounds().getHeight());
                break;
            case BOTTOM_RIGHT:
                node.setTranslateX(-getNonTransformedBounds().getWidth());
                node.setTranslateY(-getNonTransformedBounds().getHeight());
                break;
            case TOP_LEFT:
            default:
                break;
        }
    }
}
