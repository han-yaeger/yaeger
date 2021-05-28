package com.github.hanyaeger.api;

/**
 * A {@link Size} encapsulates the {@code width} and {@code height}. Note that due to the fact that
 * this is a {@link Record}, it is immutable and the {@link #width} and {@link #height} cannot be changed
 * after construction. If such behavior is required, create a new {@code Size} with the new values.
 */
public record Size(double width, double height) {

    /**
     * Creates a new instance of {@link Size}, where the {@link Size} is rectangular
     * shaped, this the {@code width} and {@code height} have the same value.
     *
     * @param widthAndHeight the width and height of the {@link Size}
     */
    public Size(double widthAndHeight) {
        this(widthAndHeight, widthAndHeight);
    }
}
