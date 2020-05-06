package nl.meron.showcase.scenes.shapeentities.entities;

import javafx.scene.paint.Color;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.shape.ellipse.EllipseEntity;

public class StaticEllipse extends EllipseEntity {

    public StaticEllipse(Location initialPosition) {
        super(initialPosition);
        setRadiusX(80);
        setRadiusY(120);
        setFill(Color.TURQUOISE);
        setStrokeColor(Color.RED);
        setStrokeWidth(6);
    }
}
