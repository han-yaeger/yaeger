package nl.meron.yaeger.engine.entities.entity;

/**
 * A {@link Placeable} has a methods that can be used to place it at a different x,y-coordinates
 * on the {@link nl.meron.yaeger.engine.scenes.YaegerScene}.
 */
public interface Placeable extends Bounded {

    /**
     * Return the {@link AnchorPoint} of this {@link Placeable}.
     *
     * @return The {@link AnchorPoint} of this {@link Placeable}.
     */
    AnchorPoint getAnchorPoint();

    /**
     * @return the x-coordinate of the top-left corner as a {@code double}.
     */
    default double getX() {
        return getLeftX();
    }

    /**
     * @return the y-coordinate of the top-left corner as a {@code double}.
     */
    default double getY() {
        return getTopY();
    }

    /**
     * Set the new x-coordinate of this {@link Entity}. The x-coordinate will be of the top-left
     * corner of the {@link javafx.geometry.BoundingBox} that is being used by this {@link Entity}.
     *
     * @param x the x-coordinate as a {@code double}.
     */
    void setX(final double x);

    /**
     * Set the new y-coordinate of this {@link Entity}.The y-coordinate will be of the top-left
     * corner of the {@link javafx.geometry.BoundingBox} that is being used by this {@link Entity}.
     *
     * @param y the y-coordinate as a {@code double}.
     */
    void setY(final double y);

    /**
     * Place the {@link Entity} on the scene at its x,y-coordinate.
     */
    void placeOnScene();
}
