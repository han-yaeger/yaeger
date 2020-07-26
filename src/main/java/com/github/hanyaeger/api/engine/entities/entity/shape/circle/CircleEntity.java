package com.github.hanyaeger.api.engine.entities.entity.shape.circle;

import com.github.hanyaeger.api.engine.entities.entity.Location;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.shape.CenteredShapeEntity;
import com.google.inject.Injector;
import javafx.scene.shape.Circle;

import java.util.Optional;

/**
 * A {@link CircleEntity} provides the option to use a drawable Circle as an
 * {@link YaegerEntity}. As opposed to some of the other shapes
 * ({@link com.github.hanyaeger.api.engine.entities.entity.shape.rectangle.RectangleEntity}, for instance),
 * the reference point of a {@link CircleEntity} is its center.
 */
public abstract class CircleEntity extends CenteredShapeEntity<Circle> {

    private Optional<Double> radius = Optional.empty();

    /**
     * Create a new {@link CircleEntity} on the given {@code initialPosition}.
     *
     * @param initialPosition The initial position at which this {@link CircleEntity} should be placed
     */
    public CircleEntity(final Location initialPosition) {
        super(initialPosition);
    }

    /**
     * Set the radius of the circle.
     *
     * @param radius The {@code radius} of the circle as a {@code double}
     */
    public void setRadius(final double radius) {
        shape.ifPresentOrElse(circle -> circle.setRadius(radius), () -> this.radius = Optional.of(radius));
    }

    @Override
    public void setReferenceX(double x) {
        shape.ifPresentOrElse(circle -> circle.setCenterX(x), () -> this.x = x);
    }

    @Override
    public void setReferenceY(double y) {
        shape.ifPresentOrElse(circle -> circle.setCenterY(y), () -> this.y = y);
    }

    @Override
    public double getTopY() {
        return super.getTopY() + radius.get();
    }

    @Override
    public double getLeftX() {
        return super.getLeftX() + radius.get();
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);
        radius.ifPresent(radius -> shape.get().setRadius(radius));
    }
}
