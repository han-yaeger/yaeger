package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.Activatable;
import com.github.hanyaeger.api.engine.Initializable;
import com.github.hanyaeger.api.engine.Timer;
import com.github.hanyaeger.api.engine.TimerListProvider;
import com.github.hanyaeger.api.engine.entities.EntityCollection;
import com.google.inject.Injector;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link YaegerEntity} is the base class for all things that can be drawn on a
 * {@link com.github.hanyaeger.api.engine.scenes.YaegerScene}.
 */
public abstract class YaegerEntity implements Initializable, Activatable, TimerListProvider, Bounded, Removeable, Placeable, SceneChild, NodeProvider, Rotatable {

    protected double x;
    protected double y;
    private boolean visible = true;
    private double opacity = 1;
    private Cursor cursor = Cursor.DEFAULT;
    private List<Timer> timers = new ArrayList<>();
    private AnchorPoint anchorPoint;

    /**
     * Instantiate a new {@link YaegerEntity} for the given {@link Location}.
     *
     * @param initialPosition the initial {@link Location} of this {@link YaegerEntity}
     */
    public YaegerEntity(final Location initialPosition) {
        this.x = initialPosition.getX();
        this.y = initialPosition.getY();
        this.anchorPoint = AnchorPoint.TOP_LEFT;
    }

    /**
     * Set the cursor to be shown. This cursor will be applied to the whole
     * {@link com.github.hanyaeger.api.engine.scenes.YaegerScene}.
     *
     * @param cursor The {@link Cursor} to be shown.
     */
    public void setCursor(final Cursor cursor) {
        getGameNode().ifPresentOrElse(node -> {
            node.getScene().setCursor(cursor);
        }, () -> this.cursor = cursor);
    }

    /**
     * Change the visibility of this {@link YaegerEntity}. The value can be either {@code true}
     * or {@code false}. Use the method {@link #setOpacity(double)} to set the transparency of
     * the {@link YaegerEntity}.
     *
     * @param visible A {@code boolean} repesenting the visibility if the {@link YaegerEntity}.
     */
    public void setVisible(final boolean visible) {
        getGameNode().ifPresentOrElse(node -> {
            node.setVisible(visible);
        }, () -> this.visible = visible);
    }

    /**
     * Specifies how opaque (that is, solid) the {@link YaegerEntity} appears. An Entity
     * with 0% opacity is fully translucent. That is, while it is still visible and rendered,
     * you generally won't be able to see it.
     * <p>
     * Opacity is specified as a value between 0 and 1. Values less than 0 are
     * treated as 0, values greater than 1 are treated as 1.
     *
     * @param opacity A {@code double} between 0 and 1.
     */
    public void setOpacity(final double opacity) {
        getGameNode().ifPresentOrElse(node -> {
            node.setOpacity(opacity);
        }, () -> this.opacity = opacity);
    }

    /**
     * Calculates the distance to a given {@link YaegerEntity}. This distance
     * is based on the {@link AnchorPoint} of the Entities and not on its nearest point.
     *
     * @param entity The {@link YaegerEntity} to which the distance should be calculated.
     * @return The distance as a {@code double}.
     */
    public double distanceTo(final YaegerEntity entity) {
        return distanceTo(new Location(entity.getOriginX(), entity.getOriginY()));
    }

    /**
     * Calculates the distance to a given {@link Location}.
     *
     * @param location The {@link Location} to which the distance should be calculated.
     * @return The distance as a {@code double}.
     */
    public double distanceTo(final Location location) {
        var thisLocation = new Point2D(getOriginX(), getOriginY());
        return thisLocation.distance(location);
    }

    public void addToEntityCollection(EntityCollection collection) {
        collection.addStaticEntity(this);
    }

    protected void applyTranslationsForAnchorPoint(Node node, AnchorPoint anchorPoint) {
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
            setReferenceX(x);
            setReferenceY(y);
            applyTranslationsForAnchorPoint(node, anchorPoint);
        });
    }
}
