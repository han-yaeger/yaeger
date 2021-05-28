package com.github.hanyaeger.api;

import com.github.hanyaeger.api.entities.YaegerEntity;

/**
 * An {@link AnchorPoint} denotes the point of an {@link YaegerEntity} which is being used for
 * placement on the x,y-coordinate.
 */
public enum AnchorPoint {
    /**
     * Use the top-left corner of the bounding box as the Anchor Point.
     */
    TOP_LEFT,
    /**
     * Use the top-center point of the bounding box as the Anchor Point.
     */
    TOP_CENTER,
    /**
     * Use the top-right corner of the bounding box as the Anchor Point.
     */
    TOP_RIGHT,
    /**
     * Use the center-left point of the bounding box as the Anchor Point.
     */
    CENTER_LEFT,
    /**
     * Use the center point of the bounding box as the Anchor Point.
     */
    CENTER_CENTER,
    /**
     * Use the center-right point of the bounding box as the Anchor Point.
     */
    CENTER_RIGHT,
    /**
     * Use the bottom-left corner of the bounding box as the Anchor Point.
     */
    BOTTOM_LEFT,
    /**
     * Use the bottom-center point of the bounding box as the Anchor Point.
     */
    BOTTOM_CENTER,
    /**
     * Use the bottom-right corner of the bounding box as the Anchor Point.
     */
    BOTTOM_RIGHT
}
