package nl.meron.yaeger.engine.entities.entity;

import com.google.inject.Injector;
import javafx.scene.Cursor;
import javafx.scene.Node;
import nl.meron.yaeger.engine.Activatable;
import nl.meron.yaeger.engine.Initializable;
import nl.meron.yaeger.engine.Timer;
import nl.meron.yaeger.engine.TimerListProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link YaegerEntity} can be used to display anything that is a child of a {@link javafx.scene.Node}.
 */
public abstract class YaegerEntity implements Initializable, Activatable, TimerListProvider, Bounded, Removeable, Placeable, SceneChild, NodeProvider, Rotatable {

    protected double initialX;
    protected double initialY;
    private boolean visible = true;
    private double opacity = 1;
    private Cursor cursor = Cursor.DEFAULT;
    private List<Timer> timers = new ArrayList<>();
    private AnchorPoint anchorPoint;

    /**
     * Instantiate a new {@link YaegerEntity} for the given {@link Location} and textDelegate.
     *
     * @param initialPosition the initial {@link Location} of this {@link YaegerEntity}
     */
    public YaegerEntity(final Location initialPosition) {
        this.initialX = initialPosition.getX();
        this.initialY = initialPosition.getY();
        this.anchorPoint = AnchorPoint.TOP_LEFT;
    }

    @Override
    public void init(final Injector injector) {
        setVisible(visible);
        setOpacity(opacity);

    }

    @Override
    public void activate() {
        setCursor(cursor);
    }

    @Override
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

    public void setCursor(final Cursor cursor) {
        getGameNode().ifPresentOrElse(node -> {
            node.getScene().setCursor(cursor);
        }, () -> this.cursor = cursor);
    }

    public void setVisible(final boolean visible) {
        getGameNode().ifPresentOrElse(node -> {
            node.setVisible(visible);
        }, () -> this.visible = visible);
    }

    public void setOpacity(final double opacity) {
        getGameNode().ifPresentOrElse(node -> {
            node.setOpacity(opacity);
        }, () -> this.opacity = opacity);
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
            case TOP_LEFT:
                node.setTranslateX(0);
                node.setTranslateY(0);
                break;
            case TOP_CENTER:
                node.setTranslateX(-getNonTransformedBounds().getWidth() / 2);
                break;
            case TOP_RIGHT:
                node.setTranslateX(-getNonTransformedBounds().getWidth());
                break;
            case CENTER_LEFT:
                node.setTranslateY(-getNonTransformedBounds().getHeight() / 2);
                break;
            case CENTER_CENTER:
                node.setTranslateX(-getNonTransformedBounds().getWidth() / 2);
                node.setTranslateY(-getNonTransformedBounds().getHeight() / 2);
                break;
            case CENTER_RIGHT:
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
            default:
                break;
        }
    }
}
