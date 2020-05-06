package nl.meron.yaeger.engine.entities.entity.shape.ellipse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.YaegerEntity;

import java.util.Optional;

/**
 * A {@link EllipseEntity} provides the option to use a drawable Ellipse as an
 * {@link nl.meron.yaeger.engine.entities.entity.YaegerEntity}.
 */
public abstract class EllipseEntity extends YaegerEntity {

    private Optional<Ellipse> ellipse;
    private Color strokeColor;
    private Color fill;
    private double strokeWidth;
    private double radiusX;
    private double radiusY;

    /**
     * Create a new {@link EllipseEntity} on the given {@code initialPosition}.
     *
     * @param initialPosition The initial position at which this {@link EllipseEntity} should be placed
     */
    public EllipseEntity(final Location initialPosition) {
        super(initialPosition);
        this.ellipse = Optional.empty();
    }

    /**
     * Set the color of the stroke of the ellipse.
     *
     * @param strokeColor The {@link Color} of the stroke
     */
    public void setStrokeColor(final Color strokeColor) {
        ellipse.ifPresentOrElse(ellipse -> ellipse.setStroke(strokeColor), () -> this.strokeColor = strokeColor);
    }

    /**
     * Set the width of the stroke of the ellipse.
     *
     * @param strokeWidth The with of the stroke as a {@code double}
     */
    public void setStrokeWidth(final double strokeWidth) {
        ellipse.ifPresentOrElse(ellipse -> ellipse.setStrokeWidth(strokeWidth), () -> this.strokeWidth = strokeWidth);
    }

    /**
     * Set the width of the ellipse.
     *
     * @param width The {@code width} of the ellipse as a {@code double}
     */
    public void setRadiusX(final double width) {
        ellipse.ifPresentOrElse(ellipse -> ellipse.setRadiusX(width), () -> this.radiusX = width);
    }
    /**
     * Set the height of the rectangle.
     *
     * @param height The {@code height} of the ellipse as a {@code double}
     */

    public void setRadiusY(final double height) {
        ellipse.ifPresentOrElse(ellipse -> ellipse.setRadiusY(height), () -> this.radiusY = height);
    }

    /**
     * Set the fill color of the ellipse.
     *
     * @param fill The {@link Color} of the fill
     */
    public void setFill(final Color fill) {
        ellipse.ifPresentOrElse(ellipse -> ellipse.setFill(fill), () -> this.fill = fill);
    }

    @Override
    public void setOriginX(double x) {
        ellipse.ifPresentOrElse(ellipse -> ellipse.setCenterX(x), () -> this.initialX = x);
    }

    @Override
    public void setOriginY(double y) {
        ellipse.ifPresentOrElse(ellipse -> ellipse.setCenterY(y), () -> this.initialY = y);
    }

    @Override
    public Optional<Node> getGameNode() {
        if (ellipse.isPresent()) {
            return Optional.of(ellipse.get());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);

        if (strokeColor != null) {
            ellipse.get().setStroke(strokeColor);
        }
        if (fill != null) {
            ellipse.get().setFill(fill);
        }
        ellipse.get().setStrokeWidth(strokeWidth);
        ellipse.get().setRadiusX(radiusX);
        ellipse.get().setRadiusY(radiusY);
    }

    @Override
    public double getTopY() {
        return super.getTopY() + (0.5 * ellipse.get().getStrokeWidth());
    }

    @Override
    public double getLeftX() {
        return super.getLeftX() + (0.5 * ellipse.get().getStrokeWidth());
    }

    @Inject
    public void setEllipse(final Ellipse ellipse) {
        this.ellipse = Optional.of(ellipse);
    }
}
