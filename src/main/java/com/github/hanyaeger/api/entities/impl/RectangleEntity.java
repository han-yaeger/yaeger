package com.github.hanyaeger.api.entities.impl;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.core.entities.ShapeEntity;
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

    private double width;
    private double height;
    private double arcWidth = DEFAULT_ARC;
    private double arcHeight = DEFAULT_ARC;

    /**
     * Create a new {@link RectangleEntity} on the given {@code initialLocation}.
     *
     * @param initialLocation the initial position at which this {@link RectangleEntity} should be placed
     */
    protected RectangleEntity(final Coordinate2D initialLocation) {
        this(initialLocation, new Size(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

    /**
     * Create a new {@link RectangleEntity} on the given {@code initialPosition} with the given {@link Size}.
     * Using this constructor results in the same situation as using {@link #RectangleEntity(Coordinate2D)},
     * {@link #setWidth(double)} and {@link #setHeight(double)}.
     *
     * @param initialPosition the initial position at which this {@link RectangleEntity} should be placed
     * @param size            the {@link Size} of this {@link RectangleEntity}
     */
    protected RectangleEntity(final Coordinate2D initialPosition, final Size size) {
        super(initialPosition);
        this.width = size.width();
        this.height = size.height();
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
    public double getHeight() {
        return shape.map(Rectangle::getHeight).orElse(height);
    }

    @Override
    public double getWidth() {
        return shape.map(Rectangle::getWidth).orElse(width);
    }

    @Override
    public final void setAnchorLocation(final Coordinate2D anchorLocation) {
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
