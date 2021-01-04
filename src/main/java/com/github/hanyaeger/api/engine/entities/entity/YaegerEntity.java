package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.Initializable;
import com.github.hanyaeger.api.engine.Timer;
import com.github.hanyaeger.api.engine.TimerListProvider;
import com.github.hanyaeger.api.engine.entities.EntityCollection;
import com.github.hanyaeger.api.engine.entities.EntityProcessor;
import com.github.hanyaeger.api.engine.entities.entity.motion.Rotatable;
import com.github.hanyaeger.api.engine.entities.entity.motion.RotationBuffer;
import com.google.inject.Injector;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A {@link YaegerEntity} is the base class for all things that can be drawn on a
 * {@link com.github.hanyaeger.api.engine.scenes.YaegerScene}.
 */
public abstract class YaegerEntity implements Initializable, TimerListProvider, Bounded, Removeable, Placeable, SceneChild, GameNode, Rotatable, EventInitiator {

    static final boolean DEFAULT_VISIBILITY = true;
    static final double DEFAULT_OPACITY = 1;

    private Coordinate2D anchorLocation;
    private AnchorPoint anchorPoint;

    private boolean visible = DEFAULT_VISIBILITY;
    private double opacity = DEFAULT_OPACITY;

    private Optional<Cursor> cursor = Optional.empty();
    private final List<Timer> timers = new ArrayList<>();

    private final RotationBuffer rotationBuffer;

    /**
     * Create a new {@link YaegerEntity} on the given {@link Coordinate2D}.
     *
     * @param initialLocation the initial {@link Coordinate2D} of this {@link YaegerEntity}
     */
    public YaegerEntity(final Coordinate2D initialLocation) {
        this.anchorLocation = initialLocation;

        this.anchorPoint = AnchorPoint.TOP_LEFT;
        this.rotationBuffer = new RotationBuffer();
    }

    /**
     * Set the cursor to be shown. This cursor will be applied to the whole
     * {@link com.github.hanyaeger.api.engine.scenes.YaegerScene}.
     *
     * @param cursor the {@link Cursor} to be shown
     */
    public void setCursor(final Cursor cursor) {
        getNode().ifPresentOrElse(node -> {
            node.getScene().setCursor(cursor);
        }, () -> this.cursor = Optional.of(cursor));
    }

    /**
     * Change the visibility of this {@link YaegerEntity}. The value can be either {@code true}
     * or {@code false}. Use the method {@link #setOpacity(double)} to set the transparency of
     * the {@link YaegerEntity}.
     *
     * @param visible a {@code boolean} repesenting the visibility if the {@link YaegerEntity}
     */
    public void setVisible(final boolean visible) {
        getNode().ifPresentOrElse(node -> {
            node.setVisible(visible);
        }, () -> this.visible = visible);
    }

    /**
     * Return the visibility of this {@link YaegerEntity}.
     *
     * @return the visibility of this {@link YaegerEntity} as a {@code boolean}
     */
    public boolean isVisible() {
        if (getNode().isPresent()) {
            return getNode().get().isVisible();
        } else {
            return visible;
        }
    }

    /**
     * Specifies how opaque (that is, solid) the {@link YaegerEntity} appears. An Entity
     * with 0% opacity is fully translucent. That is, while it is still visible and rendered,
     * you generally won't be able to see it.
     * <p>
     * Opacity is specified as a value between 0 and 1. Values less than 0 are
     * treated as 0, values greater than 1 are treated as 1.
     *
     * @param opacity a {@code double} between 0 and 1
     */
    public void setOpacity(final double opacity) {
        getNode().ifPresentOrElse(node -> {
            node.setOpacity(opacity);
        }, () -> this.opacity = opacity);
    }

    /**
     * Return the opacity of this {@link YaegerEntity}, meaning how opaque (that is, solid) the {@link YaegerEntity}
     * appears. An {@link YaegerEntity} with 0% opacity is fully translucent. That is, while it is still visible
     * and rendered, you generally won't be able to see it.
     * <p>
     * Opacity is specified as a value between 0 and 1. Values less than 0 are
     * treated as 0, values greater than 1 are treated as 1.
     *
     * @return the opacity of this {@link YaegerEntity}
     */
    public double getOpacity() {
        if (getNode().isPresent()) {
            return getNode().get().getOpacity();
        } else {
            return opacity;
        }
    }

    /**
     * Calculates the distance to a given {@link YaegerEntity}. This distance
     * is based on the {@link AnchorPoint} of the Entities and not on the shortest distance between them.
     *
     * @param entity the {@link YaegerEntity} to which the distance should be calculated.
     * @return The distance as a {@code double}
     */
    public double distanceTo(final YaegerEntity entity) {
        return distanceTo(entity.getAnchorLocation());
    }

    /**
     * Calculates the distance between the {@link AnchorPoint} of this {@link YaegerEntity} and the to a given
     * {@link Coordinate2D}.
     *
     * @param location the {@link Coordinate2D} to which the distance should be calculated
     * @return the distance as a {@code double}
     */
    public double distanceTo(final Coordinate2D location) {
        return getAnchorLocation().distance(new Point2D(location.getX(), location.getY()));
    }

    /**
     * Computes the angle (in degrees) between the unit vector that originates at the reference
     * point of this {@link YaegerEntity} and the vector with its origin at that same reference point
     * that points towards the reference point of the specified {@link YaegerEntity}. Note that the unit
     * vector points downwards, so:
     *
     * <ul>
     *     <li> If {@code otherLocation} is directly below the reference point of this
     *          {@link YaegerEntity}, the angle will be 0 degrees.</li>
     *     <li> If {@code otherLocation} is directly above the reference point of this
     *          {@link YaegerEntity}, the angle will be 180 degrees.</li>
     *     <li> If {@code otherLocation} is directly to the right of the reference point of this
     *          {@link YaegerEntity}, the angle will be 90 degrees.</li>
     *     <li> If {@code otherLocation} is directly to the left of the reference point of this
     *          {@link YaegerEntity}, the angle will be 270 degrees.</li>
     * </ul>
     *
     * @param entity the {@link YaegerEntity} of the other vector
     * @return the angle between the two vectors measured in degrees,
     * {@code NaN} if any of the two vectors is a zero vector
     * @throws NullPointerException if the specified {@code location} is null
     */
    public double angleTo(final YaegerEntity entity) {
        if (this.equals(entity)) {
            return 0d;
        }
        return angleTo(entity.getAnchorLocation());
    }

    /**
     * Computes the angle (in degrees) between the unit vector that originates at the reference
     * point of this {@link YaegerEntity} and the vector with its origin at that same reference point
     * that points towards the specified {@link Coordinate2D}. Note that the unit vector points downwards,
     * so:
     *
     * <ul>
     *     <li> If {@code otherLocation} is directly below the reference point of this
     *          {@link YaegerEntity}, the angle will be 0 degrees.</li>
     *     <li> If {@code otherLocation} is directly above the reference point of this
     *          {@link YaegerEntity}, the angle will be 180 degrees.</li>
     *     <li> If {@code otherLocation} is directly to the right of the reference point of this
     *          {@link YaegerEntity}, the angle will be 90 degrees.</li>
     *     <li> If {@code otherLocation} is directly to the left of the reference point of this
     *          {@link YaegerEntity}, the angle will be 270 degrees.</li>
     * </ul>
     *
     * @param otherLocation the {@link Coordinate2D} of the other vector
     * @return the angle between the two vectors measured in degrees,
     * {@code NaN} if any of the two vectors is a zero vector
     * @throws NullPointerException if the specified {@code otherLocation} is null
     */
    public double angleTo(final Coordinate2D otherLocation) {

        if (getAnchorLocation().equals(otherLocation)) {
            return 0D;
        }

        var delta = otherLocation.subtract(getAnchorLocation());
        var normalizedDelta = delta.normalize();
        var angle = new Point2D(0, 1).angle(normalizedDelta);

        if (delta.getX() < 0) {
            angle = 360 - angle;
        }
        return angle;
    }

    /**
     * Add this {@link YaegerEntity} to the {@link EntityCollection}, which will make it
     * part of the {@link com.github.hanyaeger.api.engine.YaegerGame}. Since an {@link EntityCollection}
     * contains different lists of Entities, a {@link YaegerEntity} itself is responsible for
     * knowing to which list it should be added.
     *
     * <b>Note that this method is for internal use only and should not be used when creating a {@link com.github.hanyaeger.api.engine.YaegerGame}</b>
     *
     * @param collection the {@link EntityCollection} to which this {@link YaegerEntity} should add itself
     */
    public void addToEntityCollection(final EntityCollection collection) {
        collection.addStaticEntity(this);
    }

    /**
     * A {@link YaegerEntity}'s location is defined by its {@code anchorLocation} and its {@code anchorPoint}.
     * For most implementation of {@link YaegerEntity}, the default {@code anchorPoint} is {@code TOP_LEFT}.
     * Using a different {@code anchorpoint} will mean a translation will be applied. Its {@code anchorLocation}
     * will remain te same.
     */
    public void applyTranslationsForAnchorPoint() {
        getNode().ifPresent(node -> {
            var localBounds = getNode().get().getBoundsInLocal();
            switch (anchorPoint) {
                case TOP_LEFT:
                    node.setTranslateX(0);
                    node.setTranslateY(0);
                    break;
                case TOP_CENTER:
                    node.setTranslateX(-localBounds.getWidth() / 2);
                    break;
                case TOP_RIGHT:
                    node.setTranslateX(-localBounds.getWidth());
                    break;
                case CENTER_LEFT:
                    node.setTranslateY(-localBounds.getHeight() / 2);
                    break;
                case CENTER_CENTER:
                    node.setTranslateX(-localBounds.getWidth() / 2);
                    node.setTranslateY(-localBounds.getHeight() / 2);
                    break;
                case CENTER_RIGHT:
                    node.setTranslateX(-localBounds.getWidth());
                    node.setTranslateY(-localBounds.getHeight() / 2);
                    break;
                case BOTTOM_LEFT:
                    node.setTranslateY(-localBounds.getHeight());
                    break;
                case BOTTOM_CENTER:
                    node.setTranslateX(-localBounds.getWidth() / 2);
                    node.setTranslateY(-localBounds.getHeight());
                    break;
                case BOTTOM_RIGHT:
                    node.setTranslateX(-localBounds.getWidth());
                    node.setTranslateY(-localBounds.getHeight());
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void init(final Injector injector) {
        setVisible(visible);
        setOpacity(opacity);
        cursor.ifPresent(this::setCursor);
        setRotate(getRotationBuffer().getRotation());
    }

    @Override
    public void setAnchorPoint(final AnchorPoint anchorPoint) {
        this.anchorPoint = anchorPoint;
        applyTranslationsForAnchorPoint();
    }

    @Override
    public AnchorPoint getAnchorPoint() {
        return anchorPoint;
    }

    @Override
    public void setAnchorLocation(final Coordinate2D anchorLocation) {
        this.anchorLocation = anchorLocation;
    }

    @Override
    public Coordinate2D getAnchorLocation() {
        return this.anchorLocation;
    }

    @Override
    public void setAnchorLocationX(double x) {
        setAnchorLocation(new Coordinate2D(x, getAnchorLocation().getY()));
    }

    @Override
    public void setAnchorLocationY(double y) {
        setAnchorLocation(new Coordinate2D(getAnchorLocation().getX(), y));
    }

    @Override
    public void remove() {
        getNode().ifPresent(node -> {
            node.setVisible(false);
            notifyRemove();
        });
    }

    @Override
    public List<Timer> getTimers() {
        return timers;
    }

    @Override
    public void transferCoordinatesToNode() {
        getNode().ifPresent(node -> {
            setAnchorLocation(anchorLocation);
        });
    }

    @Override
    public void attachEventListener(final EventType eventType, final EventHandler eventHandler) {
        getNode().ifPresent(node -> node.addEventHandler(eventType, eventHandler));
    }

    /**
     * Apply an {@link EntityProcessor} to this {@link YaegerEntity}. An {@link EntityProcessor} is most
     * likely a lambda expression passed by a parent object.
     *
     * @param processor an instance of {@link EntityProcessor}, most likely a lambda expression that should
     *                  be called for processing this {@link YaegerEntity}
     */
    public void applyEntityProcessor(final EntityProcessor processor) {
        processor.process(this);
    }

    /**
     * The {@link Node} encapsulated by this {@link YaegerEntity} should be added to a parent {@link Node} to
     * be displayed on the screen and become part of the {@link com.github.hanyaeger.api.engine.scenes.YaegerScene}.
     *
     * @param processor an instance of {@link EntityProcessor}, most likely a lambda expression that can be
     *                  used for adding this node as a child to a parent node
     */
    public void addToParent(final EntityProcessor processor) {
        processor.process(this);
    }

    @Override
    public RotationBuffer getRotationBuffer() {
        return rotationBuffer;
    }
}
