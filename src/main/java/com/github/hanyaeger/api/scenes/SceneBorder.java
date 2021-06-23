package com.github.hanyaeger.api.scenes;

/**
 * Since a {@link YaegerScene} is always rectangular shaped, it has fout borders, being the {@link #TOP}, {@link #RIGHT},
 * {@link #BOTTOM} and {@link #LEFT}.
 */
public enum SceneBorder {
    /**
     * The top of the {@link YaegerScene}. This will be all coordinates where the y-value is 0.
     */
    TOP,
    /**
     * The top of the {@link YaegerScene}. This will be all coordinates where the y-value is the height of the {@link YaegerScene}.
     */
    BOTTOM,
    /**
     * The top of the {@link YaegerScene}. This will be all coordinates where the x-value is 0.
     */
    LEFT,
    /**
     * The top of the {@link YaegerScene}. This will be all coordinates where the x-value is the width of the {@link YaegerScene}.
     */
    RIGHT
}
