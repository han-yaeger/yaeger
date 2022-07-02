package com.github.hanyaeger.api.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.YaegerGame;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.github.hanyaeger.core.Initializable;
import com.github.hanyaeger.api.Timer;
import com.github.hanyaeger.core.TimerListProvider;
import com.github.hanyaeger.core.ViewOrders;
import com.github.hanyaeger.core.YaegerGameObject;
import com.github.hanyaeger.core.entities.*;
import com.github.hanyaeger.core.entities.events.EventTypes;
import com.github.hanyaeger.core.entities.motion.InitializationBuffer;
import com.github.hanyaeger.core.repositories.DragNDropRepository;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.BoundingBox;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A {@code YaegerEntity} is the base class for all things that can be drawn on a
 * {@link YaegerScene}.
 */

public abstract class YaegerEntity extends YaegerGameObject implements Initializable, TimerListProvider, Bounded, Removable, Placeable, SceneChild, GameNode, Rotatable, EventInitiator, DragRepositoryAccessor {

    static final boolean DEFAULT_VISIBILITY = true;
    static final double DEFAULT_OPACITY = 1;

    private Coordinate2D anchorLocation;
    private AnchorPoint anchorPoint;
    private Pane rootPane;

    private boolean visible = DEFAULT_VISIBILITY;
    private double opacity = DEFAULT_OPACITY;
    private double viewOrder = ViewOrders.VIEW_ORDER_ENTITY_DEFAULT;

    private Optional<Cursor> cursor = Optional.empty();
    private final List<Timer> timers = new ArrayList<>();

    private final InitializationBuffer initializationBuffer;

    private DragNDropRepository dragNDropRepository;

    /**
     * Create a new {@code YaegerEntity} on the given {@link Coordinate2D}.
     *
     * @param initialLocation the initial {@link Coordinate2D} of this {@link YaegerEntity}
     */
    protected YaegerEntity(final Coordinate2D initialLocation) {
        this.anchorLocation = initialLocation;

        this.anchorPoint = AnchorPoint.TOP_LEFT;
        this.initializationBuffer = new InitializationBuffer();
        this.colorAdjust = new ColorAdjust();
    }

    /**
     * Set the cursor to be shown. This cursor will be applied to the whole
     * {@link YaegerScene}.
     *
     * @param cursor the {@link Cursor} to be shown
     */
    public void setCursor(final Cursor cursor) {
        getNode().ifPresentOrElse(node ->
                node.getScene().setCursor(cursor), () -> this.cursor = Optional.of(cursor));
    }

    /**
     * Return the {@link Cursor} that is currently being used. If this {@code YaegerEntity} is not yet part of a {@link YaegerScene},
     * this method will return {@code null}.
     *
     * @return the {@link Cursor}
     */
    public Cursor getCursor() {
        return getNode().map(node -> node.getScene().getCursor()).orElse(null);
    }

    /**
     * Change the visibility of this {@link YaegerEntity}. The value can be either {@code true}
     * or {@code false}. Use the method {@link #setOpacity(double)} to set the transparency of
     * the {@link YaegerEntity}.
     *
     * @param visible a {@code boolean} representing the visibility if the {@link YaegerEntity}
     */
    public void setVisible(final boolean visible) {
        getNode().ifPresentOrElse(node ->
                        node.setVisible(visible)
                , () -> this.visible = visible);
    }

    /**
     * Return the visibility of this {@link YaegerEntity}.
     *
     * @return the visibility of this {@link YaegerEntity} as a {@code boolean}
     */
    public boolean isVisible() {
        return getNode().map(Node::isVisible).orElse(visible);
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
        getNode().ifPresentOrElse(node ->
                        node.setOpacity(opacity)
                , () -> this.opacity = opacity);
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
        return getNode().map(Node::getOpacity).orElse(opacity);
    }

    /**
     * Calculates the distance to a given {@link YaegerEntity}. This distance
     * is based on the {@link AnchorPoint} of the Entities and not on the shortest distance between them.
     *
     * @param entity the {@link YaegerEntity} to which the distance should be calculated.
     * @return The distance as a {@code double}
     */
    public double distanceTo(final YaegerEntity entity) {
        return distanceTo(entity.getLocationInScene());
    }

    /**
     * Calculates the distance between the {@link AnchorPoint} of this {@link YaegerEntity} and the given
     * {@link Coordinate2D}.
     *
     * @param location the {@link Coordinate2D} to which the distance should be calculated
     * @return the distance as a {@code double}
     */
    public double distanceTo(final Coordinate2D location) {
        if (location == null) {
            throw new NullPointerException("Cannot calculate distance a coordinate that is null.");
        }
        var locationInScene = getLocationInScene();
        return locationInScene.distance(location);
    }

    /**
     * Computes the angle (in degrees) between the unit vector that originates at the {@link #getAnchorLocation()}
     * point of this {@link YaegerEntity} and the vector with its origin at that same  {@link #getAnchorLocation()}
     * that points towards the {@link #getAnchorLocation()} of the specified {@link YaegerEntity}. Note that the unit
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
     * @throws NullPointerException if the specified {@code location} is {@code null}
     */
    public double angleTo(final YaegerEntity entity) {
        if (this.equals(entity)) {
            return 0d;
        }
        return angleTo(entity.getLocationInScene());
    }

    /**
     * Computes the angle (in degrees) between the unit vector that originates at the {@link #getAnchorLocation()}
     * of this {@link YaegerEntity} and the vector with its origin at that same {@link #getAnchorLocation()}
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
        return getLocationInScene().angleTo(otherLocation);
    }

    /**
     * Add this {@link YaegerEntity} to the {@link EntityCollection}, which will make it
     * part of the {@link YaegerGame}. Since an {@link EntityCollection}
     * contains different lists of Entities, a {@link YaegerEntity} itself is responsible for
     * knowing to which list it should be added.
     *
     * <b>Note that this method is for internal use only and should not be used when creating a {@link YaegerGame}</b>
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
                case TOP_LEFT -> {
                    node.setTranslateX(0);
                    node.setTranslateY(0);
                }
                case TOP_CENTER -> node.setTranslateX(-localBounds.getWidth() / 2);
                case TOP_RIGHT -> node.setTranslateX(-localBounds.getWidth());
                case CENTER_LEFT -> node.setTranslateY(-localBounds.getHeight() / 2);
                case CENTER_CENTER -> {
                    node.setTranslateX(-localBounds.getWidth() / 2);
                    node.setTranslateY(-localBounds.getHeight() / 2);
                }
                case CENTER_RIGHT -> {
                    node.setTranslateX(-localBounds.getWidth());
                    node.setTranslateY(-localBounds.getHeight() / 2);
                }
                case BOTTOM_LEFT -> node.setTranslateY(-localBounds.getHeight());
                case BOTTOM_CENTER -> {
                    node.setTranslateX(-localBounds.getWidth() / 2);
                    node.setTranslateY(-localBounds.getHeight());
                }
                case BOTTOM_RIGHT -> {
                    node.setTranslateX(-localBounds.getWidth());
                    node.setTranslateY(-localBounds.getHeight());
                }
            }
        });
    }

    @Override
    public void init(final Injector injector) {
        getNode().ifPresent(node -> node.setEffect(colorAdjust));
        getNode().ifPresent(node -> node.setViewOrder(viewOrder));
        setVisible(visible);
        setOpacity(opacity);

        setRotate(getInitializationBuffer().getRotation());
        cursor.ifPresent(this::setCursor);
        initializationBuffer.getRemoveHandlers().forEach(handler -> attachEventListener(EventTypes.REMOVE, handler));
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
    public void setAnchorLocationX(final double x) {
        setAnchorLocation(new Coordinate2D(x, getAnchorLocation().getY()));
    }

    @Override
    public void setAnchorLocationY(final double y) {
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
        getNode().ifPresent(node -> setAnchorLocation(anchorLocation));
    }

    @Override
    public void attachEventListener(final EventType eventType, final EventHandler eventHandler) {
        getNode().ifPresentOrElse(
                node -> node.addEventHandler(eventType, eventHandler),
                () -> initializationBuffer.addRemoveHandler(eventHandler)
        );
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
     * be displayed on the screen and become part of the {@link YaegerScene}.
     *
     * @param processor an instance of {@link EntityProcessor}, most likely a lambda expression that can be
     *                  used for adding this node as a child to a parent node
     */
    public void addToParent(final EntityProcessor processor) {
        processor.process(this);
    }

    @Override
    public InitializationBuffer getInitializationBuffer() {
        return initializationBuffer;
    }

    @Override
    public DragNDropRepository getDragNDropRepository() {
        return dragNDropRepository;
    }

    /**
     * Set the viewOrder of this {@link YaegerEntity}. The viewOrder defines the rendering order
     * of this {@link YaegerEntity} within the {@link YaegerScene}. The lower the viewOrder, the closer
     * the {@link YaegerEntity} is to the front of the {@link YaegerScene}.
     * <p>
     * By default, a {@link YaegerEntity} will receive the viewOrder of {@link ViewOrders#VIEW_ORDER_ENTITY_DEFAULT}.
     * A {@link YaegerEntity} that is part of an {@link com.github.hanyaeger.api.scenes.TileMap} will default
     * to the value 100 to ensure it is placed behind other Entities.
     *
     * @param viewOrder the value of viewOrder as a {@code double}
     */
    public void setViewOrder(final double viewOrder) {
        getNode().ifPresentOrElse(node -> node.setViewOrder(viewOrder),
                () -> this.viewOrder = viewOrder);
    }

    /**
     * Return the viewOrder of this {@link YaegerEntity}. The viewOrder defines the rendering order
     * of this {@link YaegerEntity} within the {@link YaegerScene}. The lower the viewOrder, the closer
     * the {@link YaegerEntity} is to the front of the {@link YaegerScene}.
     * <p>
     * By default, a {@link YaegerEntity} will receive the viewOrder of {@link ViewOrders#VIEW_ORDER_ENTITY_DEFAULT}.
     * A {@link YaegerEntity} that is part of an {@link com.github.hanyaeger.api.scenes.TileMap} will default
     * to the value 100 to ensure it is placed behind other Entities.
     *
     * @return the value of viewOrder as a {@code double}
     */
    public double getViewOrder() {
        return getNode()
                .map(Node::getViewOrder)
                .orElse(viewOrder);
    }

    /**
     * Calculate the absolute location of this {@link YaegerEntity} in the scene.
     * Because {@link CompositeEntity} uses a relative coordinate system, this method is needed to make calculations
     * for entities that are part of a {@link CompositeEntity}.
     *
     * @return a {@link Coordinate2D} with the absolute coordinates of the {@link YaegerEntity}.
     */
    protected Coordinate2D getLocationInScene() {
        var boundsInScene = getNode()
                .map(node -> node.localToScene(node.getBoundsInLocal(), true))
                .orElse(new BoundingBox(0, 0, 0, 0));

        return switch (getAnchorPoint()) {
            case TOP_LEFT -> new Coordinate2D(boundsInScene.getMinX(), boundsInScene.getMinY());
            case TOP_CENTER -> new Coordinate2D(boundsInScene.getCenterX(), boundsInScene.getMinY());
            case TOP_RIGHT -> new Coordinate2D(boundsInScene.getMaxX(), boundsInScene.getMinY());
            case CENTER_LEFT -> new Coordinate2D(boundsInScene.getMinX(), boundsInScene.getCenterY());
            case CENTER_CENTER -> new Coordinate2D(boundsInScene.getCenterX(), boundsInScene.getCenterY());
            case CENTER_RIGHT -> new Coordinate2D(boundsInScene.getMaxX(), boundsInScene.getCenterY());
            case BOTTOM_LEFT -> new Coordinate2D(boundsInScene.getMinX(), boundsInScene.getMaxY());
            case BOTTOM_CENTER -> new Coordinate2D(boundsInScene.getCenterX(), boundsInScene.getMaxY());
            case BOTTOM_RIGHT -> new Coordinate2D(boundsInScene.getMaxX(), boundsInScene.getMaxY());
        };
    }

    /**
     * Return the root pane to which this {@link YaegerEntity} is added.
     *
     * @return the root pane, which is an instance of {@link Pane}
     */
    public Pane getRootPane() {
        return rootPane;
    }

    /**
     * Set the root pane to which this {@link YaegerEntity} is added.
     *
     * @param rootPane the root pane, which is an instance of {@link Pane}
     */
    public void setRootPane(final Pane rootPane) {
        this.rootPane = rootPane;
    }

    /**
     * Set the {@link DragNDropRepository} to be used.
     *
     * @param dragNDropRepository the {@link DragNDropRepository} to be used
     */
    @Inject
    public void setDragNDropRepository(final DragNDropRepository dragNDropRepository) {
        this.dragNDropRepository = dragNDropRepository;
    }

    @Override
    public double getSceneWidth() {
        if (getRootPane() == null) {
            return 0;
        }
        return getRootPane().getWidth();
    }

    @Override
    public double getSceneHeight() {
        if (getRootPane() == null) {
            return 0;
        }
        return getRootPane().getHeight();
    }
}
