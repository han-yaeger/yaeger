package nl.meron.showcase.scenes.shapeentities.entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.shapebased.shape.ShapeEntity;

public class EllipseEntity extends ShapeEntity {

    public EllipseEntity(Point initialPosition) {
        super(initialPosition);
    }

    @Override
    protected Shape provideShape() {
        var ellipse = new Ellipse();
        ellipse.setCenterX(initialPosition.getX());
        ellipse.setCenterY(initialPosition.getY());
        ellipse.setRadiusX(25);
        ellipse.setRadiusY(40);
        ellipse.setFill(Color.SPRINGGREEN);
        ellipse.setStroke(Color.TOMATO);
        return ellipse;
    }
}
