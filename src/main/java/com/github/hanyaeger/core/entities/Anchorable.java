package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.AnchorPoint;

/**
 * When an {@link com.github.hanyaeger.api.entities.YaegerEntity} is {@code Anchorable}, it has access to
 * am anchorpoint. This anchorpoint is used to place the {@link com.github.hanyaeger.api.entities.YaegerEntity}
 * on the {@link com.github.hanyaeger.api.scenes.YaegerScene}.
 */
public interface Anchorable {
    /**
     * Set the {@link AnchorPoint} of this {@link Placeable}. The {@link AnchorPoint} can be used for aligning
     * the {@link com.github.hanyaeger.api.entities.YaegerEntity}, and will be used
     * to set the given x, y-coordinate. By default a {@link Placeable} will use the top-left as
     * its anchor-point.
     *
     * @param anchorPoint the {@link AnchorPoint} of this {@link com.github.hanyaeger.api.entities.YaegerEntity}
     */
    void setAnchorPoint(final AnchorPoint anchorPoint);

    /**
     * Return the {@link AnchorPoint} of this {@link Placeable}.
     *
     * @return the {@link AnchorPoint} of this {@link Placeable}
     */
    AnchorPoint getAnchorPoint();
}
