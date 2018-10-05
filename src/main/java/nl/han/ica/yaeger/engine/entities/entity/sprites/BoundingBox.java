package nl.han.ica.yaeger.engine.entities.entity.sprites;

/**
 * A 2D geometric box that represents a bounding box.
 */
public class BoundingBox {

    private double width;
    private double height;

    /**
     * Creates a new instance of {@code BoundingBox}.
     *
     * @param width  the width of the {@code BoundingBox}
     * @param height the height of the {@code BoundingBox}
     */
    public BoundingBox(final double width, final double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * @return The height the height of the {@code BoundingBox}
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return The width the height of the {@code BoundingBox}
     */
    public double getWidth() {
        return width;
    }
}
