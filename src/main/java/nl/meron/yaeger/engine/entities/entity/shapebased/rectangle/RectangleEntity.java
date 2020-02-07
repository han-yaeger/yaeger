package nl.meron.yaeger.engine.entities.entity.shapebased.rectangle;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.shapebased.ShapeEntity;

public abstract class RectangleEntity extends ShapeEntity {

    private Rectangle rectangle;
    private Color strokeColor;
    private Color fill;
    private double strokeWidth;
    private double height;
    private double width;
    private double arcHeight;
    private double arcWidth;

    public RectangleEntity(Point initialPosition) {
        super(initialPosition);
    }


    @Override
    public void init(Injector injector) {
        super.init(injector);

        if (strokeColor != null) {
            rectangle.setStroke(strokeColor);
        }
        if (fill != null) {
            rectangle.setFill(fill);
        }
        rectangle.setStrokeWidth(strokeWidth);
        rectangle.setHeight(height);
        rectangle.setWidth(width);
        rectangle.setArcHeight(arcHeight);
        rectangle.setArcWidth(arcWidth);
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setFill(Color fill) {
        this.fill = fill;
    }

    public void setArcHeight(double arcHeight) {
        this.arcHeight = arcHeight;
    }

    public void setArcWidth(double arcWidth) {
        this.arcWidth = arcWidth;
    }

    @Override
    public Node getGameNode() {
        return rectangle;
    }

    @Override
    public void placeOnPosition(double x, double y) {
        if (rectangle == null) {
            initialPosition = new Point(x, y);
        } else {
            rectangle.setX(x);
            rectangle.setY(y);
        }
    }

    @Override
    public double getTopY() {
        double topY = super.getTopY() + (0.5 * rectangle.getStrokeWidth());
        return topY;
    }

    @Override
    public double getLeftSideX() {
        double leftSideX = super.getLeftSideX() + (0.5 * rectangle.getStrokeWidth());
        return leftSideX;
    }

    @Inject
    public void setRectangle(final Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
