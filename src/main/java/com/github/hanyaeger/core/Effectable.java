package com.github.hanyaeger.core;

import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.scenes.YaegerScene;

/**
 * By implementing the interface {@link Effectable}, it is possible to apply
 * various effects on both a {@link YaegerScene} and a {@link YaegerEntity}.
 */
public interface Effectable {

    /**
     * Set the brightness of the {@link YaegerScene} or {@link YaegerEntity}. The value should be a value between
     * -1 and 1, inclusive.
     * <p>
     * The brightness adjustment value.
     * <pre>
     *      Min: -1.0 Completely dark
     *      Max: +1.0 Fully bright
     * </pre>
     *
     * @param brightness the brightness as a {@code double}, between -1 and 1
     */
    void setBrightness(final double brightness);

    /**
     * Return the brightness level of this {@link YaegerScene} or {@link YaegerEntity}. Brightness is a {@code double}
     * between -1 and 1, where
     * <pre>
     *      Min: -1.0 Completely dark
     *      Max: +1.0 Fully bright
     * </pre>
     *
     * @return The brightness as a {@code double}.
     */
    double getBrightness();

    /**
     * Set the contrast of the {@link YaegerScene} or {@link YaegerEntity}. The value should be a value between
     * -1 and 1, inclusive.
     * <p>
     * The contrast adjustment value.
     * <pre>
     *      Min: -1.0 No contrast
     *      Max: +1.0 Maximum contrast
     * </pre>
     *
     * @param contrast the contrast as a {@code double}, between -1 and 1
     */
    void setContrast(final double contrast);

    /**
     * Return the contrast level of this {@link YaegerScene} or {@link YaegerEntity}. Contrast is a {@code double}
     * between -1 and 1, where
     * <pre>
     *      Min: -1.0 No contrast
     *      Max: +1.0 Maximum contrast
     * </pre>
     *
     * @return The contrast as a {@code double}.
     */
    double getContrast();

    /**
     * Set the hue of the {@link YaegerScene} or {@link YaegerEntity}. The value should be a value between
     * -1 and 1, inclusive.
     * <p>
     * The contrast adjustment value.
     * <pre>
     *      Min: -1.0 Minimum hue
     *      Max: +1.0 Maximum hue
     * </pre>
     *
     * @param hue the hue as a {@code double}, between -1 and 1
     */
    void setHue(final double hue);

    /**
     * Return the contrast level of this {@link YaegerScene} or {@link YaegerEntity}. Hue is a {@code double}
     * between -1 and 1, where
     * <pre>
     *      Min: -1.0 Minimum hue
     *      Max: +1.0 Maximum hue
     * </pre>
     *
     * @return The contrast as a {@code double}.
     */
    double getHue();

    /**
     * Set the saturation of the {@link YaegerScene} or {@link YaegerEntity}. The value should be a value between
     * -1 and 1, inclusive.
     * <p>
     * The contrast adjustment value.
     * <pre>
     *      Min: -1.0 Minimum saturation
     *      Max: +1.0 Maximum saturation
     * </pre>
     *
     * @param saturation the saturation as a {@code double}, between -1 and 1
     */
    void setSaturation(final double saturation);

    /**
     * Return the saturation level of this {@link YaegerScene} or {@link YaegerEntity}. Saturation is a {@code double}
     * between -1 and 1, where
     * <pre>
     *      Min: -1.0 Minimum saturation
     *      Max: +1.0 Maximum saturation
     * </pre>
     *
     * @return The saturation as a {@code double}.
     */
    double getSaturation();
}
