package com.github.hanyaeger.api.engine.entities.entity.shape;

import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.shape.rectangle.RectangleEntity;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

import java.util.Optional;

/**
 * The {@link ShapeEntity} is the abstract super class of all Entities that encapsulate
 * a JavaFX {@link Shape}. The specific {@link Shape} to be used, should be set as
 * the generic type, when this class is extended.
 *
 * @param <T> The Generic type to be used, which should extend {@link Shape}.
 */
public abstract class ShapeEntity<T extends Shape> extends YaegerEntity {
    static final Color DEFAULT_FILL_COLOR = Color.rgb(229, 0, 85);
    static final Color DEFAULT_STROKE_COLOR = Color.BLACK;
    static final double DEFAULT_STROKE_WIDTH = 1;

    protected Optional<T> shape = Optional.empty();

    private Color fill = DEFAULT_FILL_COLOR;
    private Color strokeColor;
    private Double strokeWidth;

    /**
     * Instantiate a new {@link ShapeEntity} for the given {@link Coordinate2D}.
     *
     * @param initialPosition the initial {@link Coordinate2D} of this {@link ShapeEntity}
     */
    public ShapeEntity(Coordinate2D initialPosition) {
        super(initialPosition);

        strokeWidth = getDefaultStrokeWidth();
        strokeColor = getDefaultStrokeColor();
    }

    /**
     * Set the fill color to be used.
     *
     * @param fill the {@link Color} of the fill
     */
    public void setFill(final Color fill) {
        shape.ifPresentOrElse(shape -> shape.setFill(fill), () -> this.fill = fill);
    }

    /**
     * Set the color of the stroke to be used.
     *
     * @param strokeColor the {@link Color} of the stroke
     */
    public void setStrokeColor(final Color strokeColor) {
        shape.ifPresentOrElse(shape -> shape.setStroke(strokeColor), () -> this.strokeColor = strokeColor);
    }

    /**
     * Set the width of the stroke to be used.
     *
     * @param strokeWidth the with of the stroke as a {@code double}
     */
    public void setStrokeWidth(final double strokeWidth) {
        shape.ifPresentOrElse(shape -> shape.setStrokeWidth(strokeWidth), () -> this.strokeWidth = strokeWidth);
    }

    /**
     * Return the {@code strokeColor} used for this {@link ShapeEntity}.
     *
     * @return the {@code strokeColor} as a {@link Color}
     */
    public Color getStrokeColor() {
        if (shape.isPresent()) {
            return (Color) shape.get().getStroke();
        }
        return strokeColor;
    }

    /**
     * Return the {@code fill} used for this {@link ShapeEntity}.
     *
     * @return the {@code fill} as a {@link Color}
     */
    public Color getFill() {
        if (shape.isPresent()) {
            return (Color) shape.get().getFill();
        }
        return fill;
    }

    /**
     * Return the {@code strokeWidth} used for this {@link ShapeEntity}.
     *
     * @return the {@code strokeWidth} as a {@code double}
     */
    public Double getStrokeWidth() {
        if (shape.isPresent()) {
            return shape.get().getStrokeWidth();
        }
        return strokeWidth;
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);
        shape.ifPresent(shape -> {
            shape.setFill(fill);
            shape.setStroke(strokeColor);
            shape.setStrokeWidth(strokeWidth);
        });
    }

    @Override
    public Optional<? extends Node> getNode() {
        return shape;
    }

    @Inject
    public void setShape(final T shape) {
        shape.setStrokeType(StrokeType.INSIDE);
        shape.setManaged(false);
        shape.setFocusTraversable(false);
        this.shape = Optional.of(shape);
    }

    protected double getDefaultStrokeWidth() {
        return DEFAULT_STROKE_WIDTH;
    }

    protected Color getDefaultStrokeColor() {
        return DEFAULT_STROKE_COLOR;
    }
}
