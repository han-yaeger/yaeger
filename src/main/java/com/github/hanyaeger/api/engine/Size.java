package com.github.hanyaeger.api.engine;

/**
 * A {@link Size} encapsulates the {@code width} and {@code height}.
 */
public class Size {

    private final double width;
    private final double height;

    /**
     * Creates a new instance of {@link Size}, where the {@link Size} is rectangular
     * shaped, this the {@code width} and {@code height} have the same value.
     *
     * @param widthAndHeight The width and height of the {@link Size}.
     */
    public Size(double widthAndHeight) {
        this(widthAndHeight, widthAndHeight);
    }

    /**
     * Creates a new instance of {@link Size}.
     *
     * @param width  The width of the {@link Size}.
     * @param height The height of the {@link Size}.
     */
    public Size(final double width, final double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * @return The height of the {@code Size}.
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return The width of the {@code Size}.
     */
    public double getWidth() {
        return width;
    }
}
