package com.github.hanyaeger.api.engine.entities.entity.shape.ellipse;

import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.shape.CenteredShapeEntity;
import com.github.hanyaeger.api.engine.entities.entity.shape.circle.CircleEntity;
import com.google.inject.Injector;
import javafx.scene.shape.Ellipse;

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
     * @param initialPosition the initial position at which this {@link EllipseEntity} should be placed
     */
    public EllipseEntity(final Coordinate2D initialPosition) {
        super(initialPosition);
    }

    /**
     * Set the horizontal radius of the ellipse.
     *
     * @param radiusX the horizontal {@code radius} of the ellipse as a {@code double}
     */
    public void setRadiusX(final double radiusX) {
        shape.ifPresentOrElse(ellipse -> ellipse.setRadiusX(radiusX), () -> this.radiusX = radiusX);
    }

    /**
     * Set the vertical radius of the ellipse.
     *
     * @param radiusY the vertical {@code radius} of the ellipse as a {@code double}
     */
    public void setRadiusY(final double radiusY) {
        shape.ifPresentOrElse(ellipse -> ellipse.setRadiusY(radiusY), () -> this.radiusY = radiusY);
    }

    /**
     * Return the horizontal {@code radius} of this {@link EllipseEntity}.
     *
     * @return the {@code radiusX} as a {@code double}
     */
    public double getRadiusX() {
        return shape.map(Ellipse::getRadiusX).orElse(radiusX);
    }

    /**
     * Return the vertical {@code radius} of this {@link CircleEntity}.
     *
     * @return the {@code radiusY} as a {@code double}
     */
    public double getRadiusY() {
        return shape.map(Ellipse::getRadiusY).orElse(radiusY);
    }

    @Override
    public final void setAnchorLocation(final Coordinate2D anchorLocation) {
        super.setAnchorLocation(anchorLocation);
        shape.ifPresent(ellipse -> {
            ellipse.setCenterX(anchorLocation.getX());
            ellipse.setCenterY(anchorLocation.getY());
        });
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);
        shape.ifPresent(shape -> {
            shape.setRadiusX(radiusX);
            shape.setRadiusY(radiusY);
        });
    }
}
