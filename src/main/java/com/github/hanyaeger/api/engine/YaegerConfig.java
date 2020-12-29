package com.github.hanyaeger.api.engine;

import java.util.Objects;

/**
 * An encapsulation of the various configuration settings that can be applied to a Yaeger game
 * at start-up.
 */
public class YaegerConfig {

    private boolean showSplash = true;
    private boolean showBoundingBox = false;

    /**
     * Whether the splash screen should be shown during start up.
     *
     * @return a {@code boolean}, true by default
     */
    public boolean isShowSplash() {
        return showSplash;
    }

    /**
     * Set whether the Splash Scene should be shown during start up.
     *
     * @param showSplash A {@code boolean} value
     */
    void setShowSplash(final boolean showSplash) {
        this.showSplash = showSplash;
    }

    /**
     * Whether a BoundingBox should be drawn around every {@link com.github.hanyaeger.api.engine.entities.entity.collisions.Collided}
     * and {@link com.github.hanyaeger.api.engine.entities.entity.collisions.Collider}.
     *
     * @return a {@code boolean}, false by default
     */
    public boolean isShowBoundingBox() {
        return showBoundingBox;
    }

    /**
     * Set whether a BoundingBox should be drawn around every {@link com.github.hanyaeger.api.engine.entities.entity.collisions.Collided}
     * and {@link com.github.hanyaeger.api.engine.entities.entity.collisions.Collider}.
     *
     * @param showBoundingBox A {@code boolean} value
     */
    public void setShowBoundingBox(boolean showBoundingBox) {
        this.showBoundingBox = showBoundingBox;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YaegerConfig that = (YaegerConfig) o;
        return showSplash == that.showSplash && showBoundingBox == that.showBoundingBox;
    }

    @Override
    public int hashCode() {
        return Objects.hash(showSplash, showBoundingBox);
    }


}
