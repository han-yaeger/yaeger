package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import javafx.geometry.Point2D;

/**
 * Base interface of all Objects that apply a motion to a subclass of {@link YaegerEntity}.
 */
public interface MotionApplier extends MotionModifier, LocationUpdater {


    /**
     * Return the current transformation.
     *
     * @return A {@link Point2D} representing the transformation applied on {@link LocationUpdater#updateLocation(Point2D)}
     */
    Point2D get();
}
