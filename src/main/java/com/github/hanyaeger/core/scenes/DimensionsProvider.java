package com.github.hanyaeger.core.scenes;

/**
 * Implementing the {@link DimensionsProvider} interface exposes the {@link #getWidth()} and
 * {@link #getHeight()} methods.
 */
public interface DimensionsProvider {
    /**
     * Return the width.
     *
     * @return the width as a {@code double}
     */
    double getWidth();

    /**
     * Return the height.
     *
     * @return the height as a {@code double}
     */
    double getHeight();
}
