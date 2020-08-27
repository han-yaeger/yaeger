package com.github.hanyaeger.api.engine.entities.entity.shape.ellipse;

import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.shape.CenteredShapeEntity;
import com.google.inject.Injector;
import javafx.scene.shape.Ellipse;

import java.util.Optional;

/**
 * A {@link EllipseEntity} provides the option to use a drawable Ellipse as an
 * {@link YaegerEntity}. As opposed to some of the other shapes
 * ({@link com.github.hanyaeger.api.engine.entities.entity.shape.rectangle.RectangleEntity}, for instance),
 * the reference point of a {@link EllipseEntity} is its center.
 */
public abstract class EllipseEntity extends CenteredShapeEntity<Ellipse> {

    private Optional<Double> radiusX = Optional.empty();
    private Optional<Double> radiusY = Optional.empty();

    /**
     * Create a new {@link EllipseEntity} on the given {@code initialPosition}.
     *
     * @param initialPosition The initial position at which this {@link EllipseEntity} should be placed
     */
    public EllipseEntity(final Coordinate2D initialPosition) {
        super(initialPosition);
    }

    /**
     * Set the horizontal radius of the ellipse.
     *
     * @param radiusX The horizontal {@code radius} of the ellipse as a {@code double}
     */
    public void setRadiusX(final double radiusX) {
        shape.ifPresentOrElse(ellipse -> ellipse.setRadiusX(radiusX), () -> this.radiusX = Optional.of(radiusX));
    }

    /**
     * Set the vertical radius of the ellipse.
     *
     * @param radiusY The vertical {@code radius} of the ellipse as a {@code double}
     */
    public void setRadiusY(final double radiusY) {
        shape.ifPresentOrElse(ellipse -> ellipse.setRadiusY(radiusY), () -> this.radiusY = Optional.of(radiusY));
    }

    @Override
    public void setReferenceX(double x) {
        shape.ifPresentOrElse(ellipse -> ellipse.setCenterX(x), () -> this.x = x);
    }

    @Override
    public void setReferenceY(double y) {
        shape.ifPresentOrElse(ellipse -> ellipse.setCenterY(y), () -> this.y = y);
    }

    @Override
    public double getTopY() {
        return super.getTopY() + radiusY.get();
    }

    @Override
    public double getLeftX() {
        return super.getLeftX() + radiusX.get();
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);
        radiusX.ifPresent(radiusX -> shape.get().setRadiusX(radiusX));
        radiusY.ifPresent(radiusY -> shape.get().setRadiusY(radiusY));
    }
}
