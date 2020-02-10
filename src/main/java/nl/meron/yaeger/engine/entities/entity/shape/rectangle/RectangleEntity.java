package nl.meron.yaeger.engine.entities.entity.shape.rectangle;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.JavaFXEntity;

/**
 * A {@link RectangleEntity} provides the option to use a drawable Rectangle as an
 * {@link nl.meron.yaeger.engine.entities.entity.Entity}.
 */
public abstract class RectangleEntity extends JavaFXEntity {

    private Rectangle rectangle;
    private Color strokeColor;
    private Color fill;
    private double strokeWidth;
    private double height;
    private double width;
    private double arcHeight;
    private double arcWidth;

    /**
     * Create a new {@link RectangleEntity} on the given {@code initialPosition}.
     *
     * @param initialPosition The initial position at which this {@link RectangleEntity} should be placed
     */
    public RectangleEntity(final Location initialPosition) {
        super(initialPosition);
    }

    /**
     * Set the color of the stroke of the rectangle.
     *
     * @param strokeColor The {@link Color} of the stroke
     */
    public void setStrokeColor(final Color strokeColor) {
        if (rectangle == null) {
            this.strokeColor = strokeColor;
        } else {
            rectangle.setStroke(strokeColor);
        }
    }

    /**
     * Set the width of the stroke of the rectangle.
     *
     * @param strokeWidth The with of the stroke as a {@code double}
     */
    public void setStrokeWidth(final double strokeWidth) {
        if (rectangle == null) {
            this.strokeWidth = strokeWidth;
        } else {
            rectangle.setStrokeWidth(strokeWidth);
        }
    }

    /**
     * Set the height of the rectangle.
     *
     * @param height The {@code height} of the rectangle as a {@code double}
     */
    public void setHeight(final double height) {
        if (rectangle == null) {
            this.height = height;
        } else {
            rectangle.setHeight(height);
        }
    }

    /**
     * Set the width of the rectangle.
     *
     * @param width The {@code width} of the rectangle as a {@code double}
     */
    public void setWidth(final double width) {
        if (rectangle == null) {

            this.width = width;
        } else {
            rectangle.setWidth(width);
        }
    }

    /**
     * Set the fill color of the rectangle.
     *
     * @param fill The {@link Color} of the fill
     */
    public void setFill(final Color fill) {
        if (rectangle == null) {
            this.fill = fill;
        } else {
            rectangle.setFill(fill);
        }
    }

    /**
     * Set the height of the arc corner of the rectangle.
     *
     * @param arcHeight The {@code height} of the arc corner of the rectangle as a {@code double}
     */
    public void setArcHeight(final double arcHeight) {
        if (rectangle == null) {
            this.arcHeight = arcHeight;
        } else {
            rectangle.setArcHeight(arcHeight);
        }
    }

    /**
     * Set the width of the arc corner of the rectangle.
     *
     * @param arcWidth The {@code width} of the arc corner of the rectangle as a {@code double}
     */
    public void setArcWidth(final double arcWidth) {
        if (rectangle == null) {
            this.arcWidth = arcWidth;
        } else {
            rectangle.setArcWidth(arcWidth);
        }
    }

    @Override
    public void init(final Injector injector) {
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

    @Override
    public Node getGameNode() {
        return rectangle;
    }

    @Override
    public void placeOnLocation(final double x, final double y) {
        if (rectangle == null) {
            initialPosition = new Location(x, y);
        } else {
            rectangle.setX(x);
            rectangle.setY(y);
        }
    }

    @Override
    public double getTopY() {
        return super.getTopY() + (0.5 * rectangle.getStrokeWidth());
    }

    @Override
    public double getLeftX() {
        return super.getLeftX() + (0.5 * rectangle.getStrokeWidth());
    }

    @Inject
    public void setRectangle(final Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
