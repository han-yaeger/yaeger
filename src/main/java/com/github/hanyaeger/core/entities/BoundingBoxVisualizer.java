package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.UpdateExposer;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.github.hanyaeger.core.entities.events.EventTypes;
import com.github.hanyaeger.api.entities.impl.rectangle.DynamicRectangleEntity;
import javafx.scene.paint.Color;

/**
 * When a Yaeger Game is run with the commandline option {@code --showBB}, for each {@link YaegerEntity} that implements
 * either {@link Collider} or {@link Collided}
 * an instance of {@link BoundingBoxVisualizer} will be added to the {@link YaegerScene}.
 * <p>
 * A {@link BoundingBoxVisualizer} will consist of a rectangle with exactly the same dimensions as the BoundingBox of the
 * {@link YaegerEntity} that is passed to its constructor. This way, it will visualize the dimensions and location of each
 * {@link Collided} and {@link Collider}
 * and help debugging the Yaeger Game.
 */
class BoundingBoxVisualizer extends DynamicRectangleEntity implements UpdateExposer {

    private final YaegerEntity yaegerEntity;
    private static final Color DEFAULT_FILL = Color.TRANSPARENT;
    private static final Color DEFAULT_STROKE_COLOR = Color.GREEN;
    private static final double DEFAULT_STROKE_WIDTH = 2;

    /**
     * Create a new {@link BoundingBoxVisualizer} for the given {@link YaegerEntity}.
     *
     * @param yaegerEntity the {@link YaegerEntity} of  which the BoundingBox should be visualized.
     */
    BoundingBoxVisualizer(final YaegerEntity yaegerEntity) {
        super(new Coordinate2D(yaegerEntity.getBoundingBox().getMinX(), yaegerEntity.getBoundingBox().getMinY()));
        this.yaegerEntity = yaegerEntity;
        setFill(DEFAULT_FILL);
        setStrokeWidth(DEFAULT_STROKE_WIDTH);
        setStrokeColor(DEFAULT_STROKE_COLOR);
        yaegerEntity.attachEventListener(EventTypes.REMOVE, e -> remove());
    }

    @Override
    public void explicitUpdate(long timestamp) {
        final var newLocation = new Coordinate2D(yaegerEntity.getBoundingBox().getMinX(), yaegerEntity.getBoundingBox().getMinY());
        setAnchorLocation(newLocation);

        setWidth(yaegerEntity.getWidth());
        setHeight(yaegerEntity.getHeight());
    }

    @Override
    public void addToEntityCollection(final EntityCollection collection) {
        collection.addBoundingBoxVisualizer(this);
    }
}
