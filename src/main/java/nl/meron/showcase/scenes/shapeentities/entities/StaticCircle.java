package nl.meron.showcase.scenes.shapeentities.entities;

import javafx.scene.paint.Color;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.shape.circle.CircleEntity;
import nl.meron.yaeger.engine.entities.entity.shape.rectangle.RectangleEntity;

public class StaticCircle extends CircleEntity {

    public StaticCircle(Location initialPosition) {
        super(initialPosition);
        setHeight(80);
        setFill(Color.DARKBLUE);
        setStrokeColor(Color.LIGHTCORAL);
        setStrokeWidth(6);
    }
}
