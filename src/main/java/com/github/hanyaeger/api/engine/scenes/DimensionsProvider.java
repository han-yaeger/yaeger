package com.github.hanyaeger.api.engine.scenes;

/**
 * Implementing the {@link DimensionsProvider} interface exposes the {@link #getWidth()} and
 * {@link #getHeight()} methods.
 */
public interface DimensionsProvider {
    /**
     * Return the width.
     *
     * @return The width as a {@code double}.
     */
    double getWidth();

    /**
     * Return the height.
     *
     * @return The height as a {@code double}.
     */
    double getHeight();
}
