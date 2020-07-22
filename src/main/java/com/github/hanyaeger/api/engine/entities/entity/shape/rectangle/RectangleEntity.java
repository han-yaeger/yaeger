package com.github.hanyaeger.api.engine.entities.entity.shape.rectangle;

import com.github.hanyaeger.api.engine.entities.entity.Location;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.shape.ShapeEntity;
import com.google.inject.Injector;
import javafx.scene.shape.Rectangle;

import java.util.Optional;

/**
 * A {@link RectangleEntity} provides the option to use a drawable Rectangle as an
 * {@link YaegerEntity}.
 */
public abstract class RectangleEntity extends ShapeEntity<Rectangle> {

    private Optional<Double> height = Optional.empty();
    private Optional<Double> width = Optional.empty();
    private Optional<Double> arcHeight = Optional.empty();
    private Optional<Double> arcWidth = Optional.empty();

    /**
     * Create a new {@link RectangleEntity} on the given {@code initialPosition}.
     *
     * @param initialPosition The initial position at which this {@link RectangleEntity} should be placed
     */
    public RectangleEntity(final Location initialPosition) {
        super(initialPosition);
    }

    /**
     * Set the height of the rectangle.
     *
     * @param height The {@code height} of the rectangle as a {@code double}
     */
    public void setHeight(final double height) {
        shape.ifPresentOrElse(shape -> shape.setHeight(height), () -> this.height = Optional.of(height));
    }

    /**
     * Set the width of the rectangle.
     *
     * @param width The {@code width} of the rectangle as a {@code double}
     */
    public void setWidth(final double width) {
        shape.ifPresentOrElse(shape -> shape.setWidth(width), () -> this.width = Optional.of(width));
    }

    /**
     * Set the height of the arc corner of the rectangle.
     *
     * @param arcHeight The {@code height} of the arc corner of the rectangle as a {@code double}
     */
    public void setArcHeight(final double arcHeight) {
        shape.ifPresentOrElse(shape -> shape.setArcHeight(arcHeight), () -> this.arcHeight = Optional.of(arcHeight));
    }

    /**
     * Set the width of the arc corner of the rectangle.
     *
     * @param arcWidth The {@code width} of the arc corner of the rectangle as a {@code double}
     */
    public void setArcWidth(final double arcWidth) {
        shape.ifPresentOrElse(shape -> shape.setArcWidth(arcWidth), () -> this.arcWidth = Optional.of(arcWidth));
    }

    @Override
    public void setOriginX(double x) {
        shape.ifPresentOrElse(shape -> shape.setX(x), () -> this.x = x);
    }

    @Override
    public void setOriginY(double y) {
        shape.ifPresentOrElse(shape -> shape.setY(y), () -> this.y = y);
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);

        height.ifPresent(height -> shape.get().setHeight(height));
        width.ifPresent(width -> shape.get().setWidth(width));
        arcHeight.ifPresent(arcHeight -> shape.get().setArcHeight(arcHeight));
        arcWidth.ifPresent(arcWidth -> shape.get().setArcWidth(arcWidth));
    }
}
