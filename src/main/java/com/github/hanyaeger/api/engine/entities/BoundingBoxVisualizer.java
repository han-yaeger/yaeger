package com.github.hanyaeger.api.engine.entities;

import com.github.hanyaeger.api.engine.UpdateExposer;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.events.EventTypes;
import com.github.hanyaeger.api.engine.entities.entity.shape.rectangle.DynamicRectangleEntity;
import javafx.scene.paint.Color;

/**
 * TODO unittest & document
 */
class BoundingBoxVisualizer extends DynamicRectangleEntity implements UpdateExposer {

    private YaegerEntity yaegerEntity;

    BoundingBoxVisualizer(final YaegerEntity yaegerEntity) {
        super(yaegerEntity.getAnchorLocation());
        this.yaegerEntity = yaegerEntity;
        setFill(Color.TRANSPARENT);
        setStrokeWidth(1);
        setStrokeColor(Color.GREEN);
        yaegerEntity.attachEventListener(EventTypes.REMOVE, (e) -> remove());
    }

    @Override
    public void explicitUpdate(long timestamp) {
        var location = new Coordinate2D(yaegerEntity.getBoundingBox().getMinX(), yaegerEntity.getBoundingBox().getMinY());
        var width = yaegerEntity.getWidth();
        var height = yaegerEntity.getHeight();

        setAnchorLocation(location);
        setWidth(width);
        setHeight(height);
    }
}




