package nl.meron.showcase.scenes.shapeentities.entities;

import javafx.scene.paint.Color;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.shapebased.rectangle.RectangleEntity;

public class StaticRectangle extends RectangleEntity {

    public StaticRectangle(Point initialPosition) {
        super(initialPosition);
        setWidth(40);
        setHeight(80);
        setFill(Color.DARKBLUE);
        setStrokeColor(Color.LIGHTCORAL);
        setStrokeWidth(6);
    }
}
