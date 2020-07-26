package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.scenes.YaegerScene;

/**
 * A {@link Placeable} has a methods that can be used to place it at a different x,y-coordinates
 * on the {@link YaegerScene}.
 */
public interface Placeable extends Bounded, Anchorable {

    /**
     * @return The x-coordinate of the top-left corner of the Bounding Box without any transformations
     * (e.g. translations, rotations) as a {@code double}.
     */
    default double getOriginX() {
        return getLeftX();
    }

    /**
     * @return The y-coordinate of the top-left corner of the Bounding Box without any transformations
     * (e.g. translations, rotations) as a {@code double}.
     */
    default double getOriginY() {
        return getTopY();
    }

    /**
     * Set the new x-coordinate of the reference of this {@link YaegerEntity}. The reference will depend on the chosen
     * {@link AnchorPoint}.
     *
     * @param x the x-coordinate as a {@code double}.
     */
    void setReferenceX(final double x);

    /**
     * Set the new y-coordinate of the reference of this {@link YaegerEntity}. The reference will depend on the chosen
     * {@link AnchorPoint}.
     *
     * @param y the y-coordinate as a {@code double}.
     */
    void setReferenceY(final double y);

    /**
     * Place the {@link YaegerEntity} on the scene at its x,y-coordinate.
     */
    void placeOnScene();
}
