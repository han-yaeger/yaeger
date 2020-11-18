package com.github.hanyaeger.api.engine;

import java.util.Objects;

/**
 * An encapsulation of the various configuration settings that can be applied to Yaeger
 * at start up.
 */
public class YaegerConfig {

    private boolean showSplash = true;

    /**
     * Whether the splash screen should be shown during start up.
     *
     * @return true by default.
     */
    public boolean isShowSplash() {
        return showSplash;
    }

    /**
     * Set whether the Splash Scene should be shown during start up.
     *
     * @param showSplash A {@code boolean} value.
     */
    void setShowSplash(final boolean showSplash) {
        this.showSplash = showSplash;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YaegerConfig that = (YaegerConfig) o;
        return showSplash == that.showSplash;
    }

    @Override
    public int hashCode() {
        return Objects.hash(showSplash);
    }
}
