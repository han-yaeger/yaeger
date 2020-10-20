package com.github.hanyaeger.api.engine.entities.entity.shape.ellipse;

import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.shape.CenteredShapeEntity;
import com.github.hanyaeger.api.engine.entities.entity.shape.circle.CircleEntity;
import com.google.inject.Injector;
import javafx.scene.shape.Ellipse;

import java.util.Optional;

/**
 * An {@link EllipseEntity} provides the option to use a drawable Ellipse as a
 * {@link YaegerEntity}. As opposed to some of the other shapes
 * ({@link com.github.hanyaeger.api.engine.entities.entity.shape.rectangle.RectangleEntity}, for instance),
 * the reference point of a {@link EllipseEntity} is its center.
 * <p>
 * For an {@link EllipseEntity} it is possible to set the x-radius and y-radius, through the methods {@link EllipseEntity#setRadiusX(double)}
 * and {@link EllipseEntity#setRadiusY(double)}. By default, both values will be set to 1.
 */
public abstract class EllipseEntity extends CenteredShapeEntity<Ellipse> {

    static final double DEFAULT_RADIUS_X = 1;
    static final double DEFAULT_RADIUS_Y = 1;

    private double radiusX = DEFAULT_RADIUS_X;
    private double radiusY = DEFAULT_RADIUS_Y;

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
        shape.ifPresentOrElse(ellipse -> ellipse.setRadiusX(radiusX), () -> this.radiusX = radiusX);
    }

    /**
     * Set the vertical radius of the ellipse.
     *
     * @param radiusY The vertical {@code radius} of the ellipse as a {@code double}
     */
    public void setRadiusY(final double radiusY) {
        shape.ifPresentOrElse(ellipse -> ellipse.setRadiusY(radiusY), () -> this.radiusY = radiusY);
    }

    /**
     * Return the horizontal {@code radius} of this {@link EllipseEntity}.
     *
     * @return The {@code radiusX} as a {@code double}.
     */
    public double getRadiusX() {
        if (shape.isPresent()) {
            return shape.get().getRadiusX();
        } else {
            return radiusX;
        }
    }

    /**
     * Return the vertical {@code radius} of this {@link CircleEntity}.
     *
     * @return The {@code radiusY} as a {@code double}.
     */
    public double getRadiusY() {
        if (shape.isPresent()) {
            return shape.get().getRadiusY();
        } else {
            return radiusY;
        }
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
        return super.getTopY() + radiusY;
    }

    @Override
    public double getLeftX() {
        return super.getLeftX() + radiusX;
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);
        shape.get().setRadiusX(radiusX);
        shape.get().setRadiusY(radiusY);
    }
}
