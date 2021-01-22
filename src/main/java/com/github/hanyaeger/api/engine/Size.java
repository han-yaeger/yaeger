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
     * @param widthAndHeight the width and height of the {@link Size}
     */
    public Size(double widthAndHeight) {
        this(widthAndHeight, widthAndHeight);
    }

    /**
     * Creates a new instance of {@link Size}.
     *
     * @param width  the width of the {@link Size}
     * @param height the height of the {@link Size}
     */
    public Size(final double width, final double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * @return the height of the {@code Size}
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return the width of the {@code Size}
     */
    public double getWidth() {
        return width;
    }
}
