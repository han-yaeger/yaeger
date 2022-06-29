package com.github.hanyaeger.api.entities.impl;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.core.entities.CenteredShapeEntity;
import com.google.inject.Injector;
import javafx.scene.shape.Circle;

/**
 * A {@link CircleEntity} provides the option to use a drawable Circle as an
 * {@link YaegerEntity}. As opposed to some shapes ({@link RectangleEntity}, for instance),
 * the reference point of a {@link CircleEntity} is its center.
 * <p>
 * For a {@link CircleEntity} it is possible to set the radius, through the methods {@link CircleEntity#setRadius(double)}.
 * By default, this value will be set to 1.
 */
public abstract class CircleEntity extends CenteredShapeEntity<Circle> {

    static final double DEFAULT_RADIUS = 1;
    private double radius = DEFAULT_RADIUS;

    /**
     * Create a new {@link CircleEntity} on the given {@code initialLocation}.
     *
     * @param initialLocation the initial position at which this {@link CircleEntity} should be placed
     */
    protected CircleEntity(final Coordinate2D initialLocation) {
        super(initialLocation);
    }

    /**
     * Set the radius of the circle.
     *
     * @param radius the {@code radius} of the circle as a {@code double}
     */
    public void setRadius(final double radius) {
        shape.ifPresentOrElse(circle -> circle.setRadius(radius), () -> this.radius = radius);
    }

    /**
     * Return the {@code radius} of this {@link CircleEntity}.
     *
     * @return the {@code radius} as a {@code double}
     */
    public double getRadius() {
        return shape.map(Circle::getRadius).orElse(radius);
    }


    @Override
    public final void setAnchorLocation(Coordinate2D anchorLocation) {
        super.setAnchorLocation(anchorLocation);
        shape.ifPresent(circle -> {
            circle.setCenterX(anchorLocation.getX());
            circle.setCenterY(anchorLocation.getY());
        });
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);
        shape.ifPresent(shape -> shape.setRadius(radius));
    }
}
