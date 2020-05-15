package com.github.hanyaeger.api.engine;

/**
 * A {@link Size} encapsulates the {@code width} and {@code height}.
 */
public class Size {

    private double width;
    private double height;

    /**
     * Creates a new instance of {@code Size}.
     *
     * @param width  the width of the {@code Size}
     * @param height the height of the {@code Size}
     */
    public Size(final double width, final double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * @return The height the height of the {@code Size}
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return The width the height of the {@code Size}
     */
    public double getWidth() {
        return width;
    }
}
