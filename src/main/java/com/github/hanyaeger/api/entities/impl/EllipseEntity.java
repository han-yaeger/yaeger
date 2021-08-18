package com.github.hanyaeger.api.entities.impl;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.core.entities.CenteredShapeEntity;
import com.google.inject.Injector;
import javafx.scene.shape.Ellipse;

/**
 * An {@link EllipseEntity} provides the option to use a drawable Ellipse as a
 * {@link YaegerEntity}. As opposed to some of the other shapes
 * ({@link RectangleEntity}, for instance),
 * the reference point of a {@link EllipseEntity} is its center.
 * <p>
 * For an {@link EllipseEntity} it is possible to set the x-radius and y-radius, through the methods {@link EllipseEntity#setRadiusX(double)}
 * and {@link EllipseEntity#setRadiusY(double)}. By default, both values will be set to 1.
 */
public abstract class EllipseEntity extends CenteredShapeEntity<Ellipse> {

    static final double DEFAULT_RADIUS_X = 1;
    static final double DEFAULT_RADIUS_Y = 1;

    double radiusX = DEFAULT_RADIUS_X;
    double radiusY = DEFAULT_RADIUS_Y;

    /**
     * Create a new {@link EllipseEntity} on the given {@code initialLocation}.
     *
     * @param initialLocation the initial position at which this {@link EllipseEntity} should be placed
     */
    protected EllipseEntity(final Coordinate2D initialLocation) {
        super(initialLocation);
    }

    /**
     * Create a new {@link EllipseEntity} on the given {@code initialPosition} with the given {@link Size}.
     * Using this constructor results in the same situation as using {@link #EllipseEntity(Coordinate2D)},
     * {@link #setRadiusX(double)} and {@link #setRadiusY(double)}, where both the {@link Size#width()} and
     * {@link Size#height()} are divided by 2.
     *
     * @param initialPosition the initial position at which this {@link EllipseEntity} should be placed
     * @param size            the {@link Size} of this {@link EllipseEntity}
     */
    protected EllipseEntity(final Coordinate2D initialPosition, final Size size) {
        super(initialPosition);
        this.radiusX = size.width() / 2;
        this.radiusY = size.height() / 2;
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
