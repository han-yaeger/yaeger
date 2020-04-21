package nl.han.yaeger.engine.entities.entity.motion;

import javafx.geometry.Point2D;

/**
 * Base interface of all Objects that apply a motion to a subclass of {@link nl.han.yaeger.engine.entities.entity.Entity}.
 */
public interface MotionApplier extends MotionModifier, LocationUpdater {


    /**
     * Return the current transformation.
     *
     * @return A {@link Point2D} representing the transformation applied on {@link LocationUpdater#updateLocation(Point2D)}
     */
    Point2D get();
}
