package nl.meron.yaeger.engine.entities.entity.shape.rectangle;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.YaegerEntity;

import java.util.Optional;

/**
 * A {@link RectangleEntity} provides the option to use a drawable Rectangle as an
 * {@link nl.meron.yaeger.engine.entities.entity.Entity}.
 */
public abstract class RectangleEntity extends YaegerEntity {

    private Optional<Rectangle> rectangle;
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
        this.rectangle = Optional.empty();
    }

    /**
     * Set the color of the stroke of the rectangle.
     *
     * @param strokeColor The {@link Color} of the stroke
     */
    public void setStrokeColor(final Color strokeColor) {
        rectangle.ifPresentOrElse(rectangle -> rectangle.setStroke(strokeColor), () -> this.strokeColor = strokeColor);
    }

    /**
     * Set the width of the stroke of the rectangle.
     *
     * @param strokeWidth The with of the stroke as a {@code double}
     */
    public void setStrokeWidth(final double strokeWidth) {
        rectangle.ifPresentOrElse(rectangle -> rectangle.setStrokeWidth(strokeWidth), () -> this.strokeWidth = strokeWidth);
    }

    /**
     * Set the height of the rectangle.
     *
     * @param height The {@code height} of the rectangle as a {@code double}
     */
    public void setHeight(final double height) {
        rectangle.ifPresentOrElse(rectangle -> rectangle.setHeight(height), () -> this.height = height);
    }

    /**
     * Set the width of the rectangle.
     *
     * @param width The {@code width} of the rectangle as a {@code double}
     */
    public void setWidth(final double width) {
        rectangle.ifPresentOrElse(rectangle -> rectangle.setWidth(width), () -> this.width = width);
    }

    /**
     * Set the fill color of the rectangle.
     *
     * @param fill The {@link Color} of the fill
     */
    public void setFill(final Color fill) {
        rectangle.ifPresentOrElse(rectangle -> rectangle.setFill(fill), () -> this.fill = fill);
    }

    /**
     * Set the height of the arc corner of the rectangle.
     *
     * @param arcHeight The {@code height} of the arc corner of the rectangle as a {@code double}
     */
    public void setArcHeight(final double arcHeight) {
        rectangle.ifPresentOrElse(rectangle -> rectangle.setArcHeight(arcHeight), () -> this.arcHeight = arcHeight);
    }

    /**
     * Set the width of the arc corner of the rectangle.
     *
     * @param arcWidth The {@code width} of the arc corner of the rectangle as a {@code double}
     */
    public void setArcWidth(final double arcWidth) {
        rectangle.ifPresentOrElse(rectangle -> rectangle.setArcWidth(arcWidth), () -> this.arcWidth = arcWidth);
    }

    @Override
    public void setOriginX(double x) {
        rectangle.ifPresentOrElse(rectangle -> rectangle.setX(x), () -> this.initialX = x);
    }

    @Override
    public void setOriginY(double y) {
        rectangle.ifPresentOrElse(rectangle -> rectangle.setY(y), () -> this.initialY = y);
    }

    @Override
    public Optional<Node> getGameNode() {
        if (rectangle.isPresent()) {
            return Optional.of(rectangle.get());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);

        if (strokeColor != null) {
            rectangle.get().setStroke(strokeColor);
        }
        if (fill != null) {
            rectangle.get().setFill(fill);
        }
        rectangle.get().setStrokeWidth(strokeWidth);
        rectangle.get().setHeight(height);
        rectangle.get().setWidth(width);
        rectangle.get().setArcHeight(arcHeight);
        rectangle.get().setArcWidth(arcWidth);
    }

    @Override
    public double getTopY() {
        return super.getTopY() + (0.5 * rectangle.get().getStrokeWidth());
    }

    @Override
    public double getLeftX() {
        return super.getLeftX() + (0.5 * rectangle.get().getStrokeWidth());
    }

    @Inject
    public void setRectangle(final Rectangle rectangle) {
        this.rectangle = Optional.of(rectangle);
    }
}
