package nl.meron.showcase.scenes.shapeentities.entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.shapebased.shape.ShapeEntity;

public class RectangleEntity extends ShapeEntity {

    public RectangleEntity(Point initialPosition) {
        super(initialPosition);
    }

    @Override
    protected Shape provideShape() {
        var rect = new Rectangle();
        rect.setHeight(100);
        rect.setWidth(50);
        rect.setFill(Color.TOMATO);
        rect.setStroke(Color.SPRINGGREEN);
        return rect;
    }
}
