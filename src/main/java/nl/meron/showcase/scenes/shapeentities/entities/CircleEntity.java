package nl.meron.showcase.scenes.shapeentities.entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.shapebased.shape.ShapeEntity;

public class CircleEntity extends ShapeEntity {

    public CircleEntity(Point initialPosition) {
        super(initialPosition);
    }

    @Override
    protected Shape provideShape() {
        var circle = new Circle();
        circle.setCenterX(initialPosition.getX());
        circle.setCenterY(initialPosition.getY());
        circle.setRadius(50);
        circle.setFill(Color.STEELBLUE);
        circle.setStroke(Color.SLATEGREY);
        circle.setStrokeWidth(6);
        return circle;
    }
}
