package nl.han.yaeger.engine.entities.entity;

/**
 * A {@link Placeable} has a methods that can be used to place it at a different x,y-coordinates
 * on the {@link nl.han.yaeger.engine.scenes.YaegerScene}.
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
     * Set the new x-coordinate of this {@link Entity}. The x-coordinate will be of the top-left
     * corner of the {@link javafx.geometry.BoundingBox} that is being used by this {@link Entity}.
     *
     * @param x the x-coordinate as a {@code double}.
     */
    void setOriginX(final double x);

    /**
     * Set the new y-coordinate of this {@link Entity}.The y-coordinate will be of the top-left
     * corner of the {@link javafx.geometry.BoundingBox} that is being used by this {@link Entity}.
     *
     * @param y the y-coordinate as a {@code double}.
     */
    void setOriginY(final double y);

    /**
     * Place the {@link Entity} on the scene at its x,y-coordinate.
     */
    void placeOnScene();
}
