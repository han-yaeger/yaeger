package nl.meron.yaeger.engine.entities.entity.shape.circle;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.JavaFXEntity;

import java.util.Optional;

/**
 * A {@link CircleEntity} provides the option to use a drawable Rectangle as an
 * {@link nl.meron.yaeger.engine.entities.entity.Entity}.
 */
public abstract class CircleEntity extends JavaFXEntity {

    private Optional<Circle> circle;
    private Color strokeColor;
    private Color fill;
    private double strokeWidth;
    private double height;
    private double width;
    private double arcHeight;
    private double arcWidth;

    /**
     * Create a new {@link CircleEntity} on the given {@code initialPosition}.
     *
     * @param initialPosition The initial position at which this {@link CircleEntity} should be placed
     */
    public CircleEntity(final Location initialPosition) {
        super(initialPosition);
        this.circle = Optional.empty();
    }

    /**
     * Set the color of the stroke of the rectangle.
     *
     * @param strokeColor The {@link Color} of the stroke
     */
    public void setStrokeColor(final Color strokeColor) {
        circle.ifPresentOrElse(rectangle -> rectangle.setStroke(strokeColor), () -> this.strokeColor = strokeColor);
    }

    /**
     * Set the width of the stroke of the rectangle.
     *
     * @param strokeWidth The with of the stroke as a {@code double}
     */
    public void setStrokeWidth(final double strokeWidth) {
        circle.ifPresentOrElse(rectangle -> rectangle.setStrokeWidth(strokeWidth), () -> this.strokeWidth = strokeWidth);
    }

    /**
     * Set the height of the rectangle.
     *
     * @param height The {@code height} of the rectangle as a {@code double}
     */
    public void setHeight(final double height) {
        circle.ifPresentOrElse(rectangle -> rectangle.setRadius(height), () -> this.height = height);
    }

    /**
     * Set the fill color of the rectangle.
     *
     * @param fill The {@link Color} of the fill
     */
    public void setFill(final Color fill) {
        circle.ifPresentOrElse(rectangle -> rectangle.setFill(fill), () -> this.fill = fill);
    }

    @Override
    public void setOriginX(double x) {
        circle.ifPresentOrElse(circle -> circle.setCenterX(x), () -> this.initialX = x);
    }

    @Override
    public void setOriginY(double y) {
        circle.ifPresentOrElse(circle -> circle.setCenterY(y), () -> this.initialY = y);
    }

    @Override
    public Optional<Node> getGameNode() {
        if (circle.isPresent()) {
            return Optional.of(circle.get());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);

        if (strokeColor != null) {
            circle.get().setStroke(strokeColor);
        }
        if (fill != null) {
            circle.get().setFill(fill);
        }
        circle.get().setStrokeWidth(strokeWidth);
        circle.get().setRadius(height);
    }

    @Override
    public double getTopY() {
        return super.getTopY() + (0.5 * circle.get().getStrokeWidth());
    }

    @Override
    public double getLeftX() {
        return super.getLeftX() + (0.5 * circle.get().getStrokeWidth());
    }

    @Inject
    public void setCircle(final Circle circle) {
        this.circle = Optional.of(circle);
    }
}
