package com.github.hanyaeger.api.engine.entities.entity.shape.circle;

import com.github.hanyaeger.api.engine.entities.entity.Location;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.shape.ShapeEntity;
import com.google.inject.Injector;
import javafx.scene.shape.Circle;

import java.util.Optional;

/**
 * A {@link CircleEntity} provides the option to use a drawable Circle as an
 * {@link YaegerEntity}. As opposed to some of the other shapes
 * ({@link com.github.hanyaeger.api.engine.entities.entity.shape.rectangle.RectangleEntity}, for instance),
 * the reference point of a {@link CircleEntity} is its center.
 */
public abstract class CircleEntity extends ShapeEntity<Circle> {

    private Optional<Double> radius;

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
    public void setOriginX(double x) {
        shape.ifPresentOrElse(circle -> circle.setCenterX(x), () -> this.initialX = x);
    }

    @Override
    public void setOriginY(double y) {
        shape.ifPresentOrElse(circle -> circle.setCenterY(y), () -> this.initialY = y);
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);
        radius.ifPresent(radius -> shape.get().setRadius(radius));
    }

//    @Override
//    public double getTopY() {
//        return super.getTopY() ;
//    }
//
//    @Override
//    public double getLeftX() {
//        return super.getLeftX() + (0.5 * shape.get().getStrokeWidth());
//    }
}
