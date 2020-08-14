package com.github.hanyaeger.api.engine;

import com.github.hanyaeger.api.engine.scenes.YaegerScene;

/**
 * By implementing the interface {@link Effectable}, it is possible to applies
 * various effects on a {@link javafx.scene.Node} from a {@link YaegerScene}. This
 * can be either the {@link YaegerScene} itself, or the instances of {@link com.github.hanyaeger.api.engine.entities.entity.YaegerEntity}
 * that populate the {@link YaegerScene}.
 */
public interface Effectable {

    /**
     * Set the brightness of the whole {@link YaegerScene}. The value should be a value between
     * -1 and 1, inclusive.
     * <p>
     * The brightness adjustment value.
     * <pre>
     *      Min: -1.0 Completely dark
     *      Max: +1.0 Fully bright
     * </pre>
     *
     * @param brightness The brightness as a {@code double}, between 0 and 1.
     */
    void setBrightness(final double brightness);

    /**
     * Return the brightness level of this scene. Brightness is a {@code double}
     * between -1 and 1, where
     * <pre>
     *      Min: -1.0 Completely dark
     *      Max: +1.0 Fully bright
     * </pre>
     *
     * @return The brightness as a {@code double}.
     */
    double getBrightness();
}
