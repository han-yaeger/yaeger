package com.github.hanyaeger.api.engine.entities;

import com.github.hanyaeger.api.engine.UpdateExposer;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.events.EventTypes;
import com.github.hanyaeger.api.engine.entities.entity.shape.rectangle.DynamicRectangleEntity;
import javafx.scene.paint.Color;

/**
 * When a Yaeger Game is run with the commandline option {@code --showBB}, for each {@link YaegerEntity} that implements
 * either {@link com.github.hanyaeger.api.engine.entities.entity.collisions.Collider} or {@link com.github.hanyaeger.api.engine.entities.entity.collisions.Collided}
 * an instance of {@link BoundingBoxVisualizer} will be added to the {@link com.github.hanyaeger.api.engine.scenes.YaegerScene}.
 * <p>
 * A {@link BoundingBoxVisualizer} will consist of a rectangle with exactly the same dimensions as the BoundingBox of the
 * {@link YaegerEntity} that is passed to its constructor. This way, it will visualize the dimensions and location of each
 * {@link com.github.hanyaeger.api.engine.entities.entity.collisions.Collided} and {@link com.github.hanyaeger.api.engine.entities.entity.collisions.Collider}
 * and help debugging the Yaeger Game.
 */
class BoundingBoxVisualizer extends DynamicRectangleEntity implements UpdateExposer {

    private YaegerEntity yaegerEntity;

    /**
     * Create a new {@link BoundingBoxVisualizer} for the given {@link YaegerEntity}.
     *
     * @param yaegerEntity the {@link YaegerEntity} of  which the BoundingBox should be visualized.
     */
    BoundingBoxVisualizer(final YaegerEntity yaegerEntity) {
        super(new Coordinate2D(yaegerEntity.getBoundingBox().getMinX(), yaegerEntity.getBoundingBox().getMinY()));
        this.yaegerEntity = yaegerEntity;
        setFill(Color.TRANSPARENT);
        setStrokeWidth(2);
        setStrokeColor(Color.GREEN);
        yaegerEntity.attachEventListener(EventTypes.REMOVE, (e) -> remove());
    }

    @Override
    public void explicitUpdate(long timestamp) {
        setAnchorLocation(new Coordinate2D(yaegerEntity.getBoundingBox().getMinX(), yaegerEntity.getBoundingBox().getMinY()));
        setWidth(yaegerEntity.getWidth());
        setHeight(yaegerEntity.getHeight());
    }
}




