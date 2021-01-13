package com.github.hanyaeger.api.engine.entities.entity.shape.rectangle;

import com.github.hanyaeger.api.engine.Size;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.shape.ShapeEntity;
import com.google.inject.Injector;
import javafx.scene.shape.Rectangle;

/**
 * A {@link RectangleEntity} provides the option to use a drawable Rectangle as an
 * {@link YaegerEntity}.
 */
public abstract class RectangleEntity extends ShapeEntity<Rectangle> {

    static final double DEFAULT_HEIGHT = 1;
    static final double DEFAULT_WIDTH = 2;
    static final double DEFAULT_ARC = 0;

    private double width = DEFAULT_WIDTH;
    private double height = DEFAULT_HEIGHT;
    private double arcWidth = DEFAULT_ARC;
    private double arcHeight = DEFAULT_ARC;

    /**
     * Create a new {@link RectangleEntity} on the given {@code initialPosition}.
     *
     * @param initialPosition the initial position at which this {@link RectangleEntity} should be placed
     */
    public RectangleEntity(final Coordinate2D initialPosition) {
        super(initialPosition);
    }

    /**
     * Create a new {@link RectangleEntity} on the given {@code initialPosition} with the given {@link Size}.
     * Using this constructor results in the same situation as using {@link #RectangleEntity(Coordinate2D)},
     * {@link #setWidth(double)} and {@link #setHeight(double)}.
     *
     * @param initialPosition the initial position at which this {@link RectangleEntity} should be placed
     * @param size            the {@link Size} of this {@link RectangleEntity}
     */
    public RectangleEntity(final Coordinate2D initialPosition, final Size size) {
        super(initialPosition);
        this.width = size.getWidth();
        this.height = size.getHeight();
    }

    /**
     * Set the height of the rectangle.
     *
     * @param height the {@code height} of the rectangle as a {@code double}
     */
    public void setHeight(final double height) {
        shape.ifPresentOrElse(shape -> shape.setHeight(height), () -> this.height = height);
    }

    /**
     * Set the width of the rectangle.
     *
     * @param width the {@code width} of the rectangle as a {@code double}
     */
    public void setWidth(final double width) {
        shape.ifPresentOrElse(shape -> shape.setWidth(width), () -> this.width = width);
    }

    /**
     * Return the {@code height} of this {@link RectangleEntity}.
     *
     * @return the {@code height} as a {@code double}
     */
    public double getHeight() {
        return shape.map(Rectangle::getHeight).orElse(height);
    }

    /**
     * Return the  {@code width} of this {@link RectangleEntity}.
     *
     * @return the {@code width} as a {@code double}
     */
    public double getWidth() {
        return shape.map(Rectangle::getWidth).orElse(width);
    }

    /**
     * Set the height of the arc corner of the rectangle.
     *
     * @param arcHeight the {@code height} of the arc corner of the rectangle as a {@code double}
     */
    public void setArcHeight(final double arcHeight) {
        shape.ifPresentOrElse(shape -> shape.setArcHeight(arcHeight), () -> this.arcHeight = arcHeight);
    }

    /**
     * Set the width of the arc corner of the rectangle.
     *
     * @param arcWidth the {@code width} of the arc corner of the rectangle as a {@code double}
     */
    public void setArcWidth(final double arcWidth) {
        shape.ifPresentOrElse(shape -> shape.setArcWidth(arcWidth), () -> this.arcWidth = arcWidth);
    }

    /**
     * Return the  {@code arcHeight} of this {@link RectangleEntity}.
     *
     * @return the {@code arcHeight} as a {@code double}
     */
    public double getArcHeight() {
        return shape.map(Rectangle::getArcHeight).orElse(arcHeight);
    }

    /**
     * Return the {@code arcWidth} of this {@link RectangleEntity}.
     *
     * @return the {@code arcWidth} as a {@code double}
     */
    public double getArcWidth() {
        return shape.map(Rectangle::getArcWidth).orElse(arcWidth);
    }

    @Override
    public final void setAnchorLocation(Coordinate2D anchorLocation) {
        super.setAnchorLocation(anchorLocation);
        shape.ifPresent(rectangle -> {
            rectangle.setX(anchorLocation.getX());
            rectangle.setY(anchorLocation.getY());
        });
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);

        shape.ifPresent(shape -> {
            shape.setHeight(height);
            shape.setWidth(width);
            shape.setArcHeight(arcHeight);
            shape.setArcWidth(arcWidth);
        });
    }
}
