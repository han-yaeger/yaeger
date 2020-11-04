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
    protected Optional<T> shape = Optional.empty();

    private Optional<Color> fill = Optional.empty();
    private Optional<Color> strokeColor = Optional.empty();
    private Optional<Double> strokeWidth = Optional.empty();

    /**
     * Instantiate a new {@link ShapeEntity} for the given {@link Coordinate2D}.
     *
     * @param initialPosition the initial {@link Coordinate2D} of this {@link ShapeEntity}
     */
    public ShapeEntity(Coordinate2D initialPosition) {
        super(initialPosition);

//        strokeWidth = getDefaultStrokeWidth();
//        strokeColor = getDefaultStrokeColor();
    }

    /**
     * Set the fill color to be used.
     *
     * @param fill the {@link Color} of the fill
     */
    public void setFill(final Color fill) {
        shape.ifPresentOrElse(shape -> shape.setFill(fill), () -> this.fill = Optional.of(fill));
    }

    /**
     * Set the color of the stroke to be used.
     *
     * @param strokeColor the {@link Color} of the stroke
     */
    public void setStrokeColor(final Color strokeColor) {
        shape.ifPresentOrElse(shape -> shape.setStroke(strokeColor), () -> this.strokeColor = Optional.of(strokeColor));
    }

    /**
     * Set the width of the stroke to be used.
     *
     * @param strokeWidth the with of the stroke as a {@code double}
     */
    public void setStrokeWidth(final double strokeWidth) {
        shape.ifPresentOrElse(shape -> shape.setStrokeWidth(strokeWidth), () -> this.strokeWidth = Optional.of(strokeWidth));
    }

    /**
     * Return the {@code strokeColor} used for this {@link ShapeEntity}.
     *
     * @return the {@code strokeColor} as a {@link Color}
     */
    public Color getStrokeColor() {
        if (shape.isPresent()) {
            return (Color) shape.get().getStroke();
        } else if (strokeColor.isPresent()) {
            return strokeColor.get();
        } else {
            return null;
        }
    }

    /**
     * Return the {@code fill} used for this {@link ShapeEntity}.
     *
     * @return the {@code fill} as a {@link Color}
     */
    public Color getFill() {
        if (shape.isPresent()) {
            return (Color) shape.get().getFill();
        } else if (fill.isPresent()) {
            return fill.get();
        } else {
            return null;
        }
    }

    /**
     * Return the {@code strokeWidth} used for this {@link ShapeEntity}.
     *
     * @return the {@code strokeWidth} as a {@code double}
     */
    public Double getStrokeWidth() {
        if (shape.isPresent()) {
            return shape.get().getStrokeWidth();
        } else if (strokeWidth.isPresent()) {
            return strokeWidth.get();
        } else {
            return null;
        }
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);
        shape.ifPresent(shape -> {
            fill.ifPresent(fill -> shape.setFill(fill));
            strokeColor.ifPresent(strokeColor -> shape.setStroke(strokeColor));
            strokeWidth.ifPresent(strokeWidth -> shape.setStrokeWidth(strokeWidth));
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
}
