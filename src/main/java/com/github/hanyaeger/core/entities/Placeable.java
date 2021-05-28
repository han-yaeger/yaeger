package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.scenes.YaegerScene;

/**
 * A {@link Placeable} has a methods that can be used to place it at a different x,y-coordinates
 * on the {@link YaegerScene}.
 */
public interface Placeable extends Bounded, Anchorable {

    /**
     * Set the x-coordinate of the {@code AnchorLocation} of this {@link YaegerEntity};
     *
     * @param x the x-coordinate as a {@code double}
     */
    void setAnchorLocationX(final double x);

    /**
     * Set the y-coordinate of the {@code AnchorLocation} of this {@link YaegerEntity};
     *
     * @param y the y-coordinate as a {@code double}
     */
    void setAnchorLocationY(final double y);

    /**
     * Set the {@link Coordinate2D} where the {@link AnchorPoint} of this {@link YaegerEntity} will be placed, within
     * the {@link YaegerScene}.
     *
     * @param anchorLocation the {@link Coordinate2D} that should be used
     */
    void setAnchorLocation(final Coordinate2D anchorLocation);

    /**
     * Return the {@link Coordinate2D} where the {@link AnchorPoint} of this {@link YaegerEntity} is placed, within
     * the {@link YaegerScene}.
     *
     * @return the {@link Coordinate2D} that is currently being used
     */
    Coordinate2D getAnchorLocation();

    /**
     * Transfer the x and y-coordinate of this {@link YaegerEntity} to its JavaFX {@link javafx.scene.Node}
     * and apply the requested transformations.
     */
    void transferCoordinatesToNode();
}
