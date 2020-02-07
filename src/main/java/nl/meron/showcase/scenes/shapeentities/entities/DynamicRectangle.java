package nl.meron.showcase.scenes.shapeentities.entities;

import javafx.scene.paint.Color;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;
import nl.meron.yaeger.engine.entities.entity.shapebased.rectangle.DynamicRectangleEntity;

public class DynamicRectangle extends DynamicRectangleEntity {

    public DynamicRectangle(Point initialPosition) {
        super(initialPosition);
        setWidth(40);
        setHeight(80);
        setFill(Color.PALEGREEN);
        setStrokeColor(Color.SPRINGGREEN);
        setStrokeWidth(3);
        setMotionTo(2, Direction.RIGHT.getValue());
    }
}
