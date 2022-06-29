package com.github.hanyaeger.core;

/**
 * An encapsulation of the various configuration settings that can be applied to a Yaeger game
 * at start-up.
 */
public record YaegerConfig(boolean showSplash, boolean showBoundingBox, boolean showDebug, boolean enableScroll,
                           boolean limitGWU) {
}
