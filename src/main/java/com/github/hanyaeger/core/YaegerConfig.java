package com.github.hanyaeger.core;

import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;

import java.util.Objects;

/**
 * An encapsulation of the various configuration settings that can be applied to a Yaeger game
 * at start-up.
 */
public class YaegerConfig {

    private boolean showSplash = true;
    private boolean showBoundingBox = false;
    private boolean showDebug = false;

    /**
     * Whether the splash screen should be shown during start up.
     *
     * @return a {@code boolean}, {@code true} by default
     */
    public boolean isShowSplash() {
        return showSplash;
    }

    /**
     * Set whether the Splash Scene should be shown during start up.
     *
     * @param showSplash a {@code boolean} value
     */
    public void setShowSplash(final boolean showSplash) {
        this.showSplash = showSplash;
    }

    /**
     * Whether a BoundingBox should be drawn around every {@link Collided}
     * and {@link Collider}.
     *
     * @return a {@code boolean}, {@code false} by default
     */
    public boolean isShowBoundingBox() {
        return showBoundingBox;
    }

    /**
     * Set whether a BoundingBox should be drawn around every {@link Collided}
     * and {@link Collider}.
     *
     * @param showBoundingBox A {@code boolean} value
     */
    public void setShowBoundingBox(final boolean showBoundingBox) {
        this.showBoundingBox = showBoundingBox;
    }

    /**
     * Whether the debug window should be shown.
     *
     * @return a {@code boolean}, {@code false} by default
     */
    public boolean isShowDebug() {
        return showDebug;
    }

    /**
     * Set whether the debug window should be shown.
     *
     * @param showDebug a {@code boolean} value
     */
    public void setShowDebug(final boolean showDebug) {
        this.showDebug = showDebug;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (YaegerConfig) o;
        return showSplash == that.showSplash && showBoundingBox == that.showBoundingBox && showDebug == that.showDebug;
    }

    @Override
    public int hashCode() {
        return Objects.hash(showSplash, showBoundingBox, showDebug);
    }


}
