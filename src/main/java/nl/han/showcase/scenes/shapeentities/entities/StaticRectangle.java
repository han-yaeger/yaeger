package nl.han.showcase.scenes.shapeentities.entities;

import javafx.scene.paint.Color;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.entity.shape.rectangle.RectangleEntity;

public class StaticRectangle extends RectangleEntity {

    public StaticRectangle(Location initialPosition) {
        super(initialPosition);
        setWidth(40);
        setHeight(80);
        setFill(Color.DARKBLUE);
        setStrokeColor(Color.LIGHTCORAL);
        setStrokeWidth(6);
    }
}
