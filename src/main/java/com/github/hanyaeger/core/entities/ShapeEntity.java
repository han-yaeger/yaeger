package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
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

    /**
     * The actual {@link Shape} of type {@link T} to be used
     */
    protected Optional<T> shape = Optional.empty();

    private Optional<Color> fill = Optional.empty();
    private Optional<Color> strokeColor = Optional.empty();
    private Optional<Double> strokeWidth = Optional.empty();

    /**
     * Instantiate a new {@link ShapeEntity} for the given {@link Coordinate2D}.
     *
     * @param initialPosition the initial {@link Coordinate2D} of this {@link ShapeEntity}
     */
    protected ShapeEntity(final Coordinate2D initialPosition) {
        super(initialPosition);
    }

    /**
     * Set the fill color to be used.
     *
     * @param fill the {@link Color} of the fill
     */
    public void setFill(final Color fill) {
        shape.ifPresentOrElse(s -> s.setFill(fill), () -> this.fill = Optional.of(fill));
    }

    /**
     * Set the color of the stroke to be used.
     *
     * @param strokeColor the {@link Color} of the stroke
     */
    public void setStrokeColor(final Color strokeColor) {
        shape.ifPresentOrElse(s -> s.setStroke(strokeColor), () -> this.strokeColor = Optional.of(strokeColor));
    }

    /**
     * Set the width of the stroke to be used.
     *
     * @param strokeWidth the with of the stroke as a {@code double}
     */
    public void setStrokeWidth(final double strokeWidth) {
        shape.ifPresentOrElse(s -> s.setStrokeWidth(strokeWidth), () -> this.strokeWidth = Optional.of(strokeWidth));
    }

    /**
     * Return the {@code strokeColor} used for this {@link ShapeEntity}.
     *
     * @return the {@code strokeColor} as a {@link Color}
     */
    public Color getStrokeColor() {
        if (shape.isPresent()) {
            return (Color) shape.get().getStroke();
        } else return strokeColor.orElse(null);
    }

    /**
     * Return the {@code fill} used for this {@link ShapeEntity}.
     *
     * @return the {@code fill} as a {@link Color}
     */
    public Color getFill() {
        if (shape.isPresent()) {
            return (Color) shape.get().getFill();
        } else return fill.orElse(null);
    }

    /**
     * Return the {@code strokeWidth} used for this {@link ShapeEntity}.
     *
     * @return the {@code strokeWidth} as a {@code double}
     */
    public Double getStrokeWidth() {
        return shape.map(Shape::getStrokeWidth).orElseGet(() -> strokeWidth.orElse(null));
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);
        shape.ifPresent(s -> {
            fill.ifPresent(s::setFill);
            strokeColor.ifPresent(s::setStroke);
            strokeWidth.ifPresent(s::setStrokeWidth);
        });
    }

    @Override
    public Optional<? extends Node> getNode() {
        return shape;
    }

    /**
     * Set the actual {@link Shape} of type {@link T}.
     *
     * @param shape the {@link Shape} of type {@link T}
     */
    @Inject
    public void setShape(final T shape) {
        shape.setStrokeType(StrokeType.INSIDE);
        shape.setManaged(false);
        shape.setFocusTraversable(false);
        this.shape = Optional.of(shape);
    }
}
