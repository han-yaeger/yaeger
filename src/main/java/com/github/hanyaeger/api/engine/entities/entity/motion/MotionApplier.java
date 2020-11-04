package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import javafx.geometry.Point2D;

import java.util.Optional;

/**
 * Base interface of all Objects that apply a motion to a subclass of {@link YaegerEntity}.
 */
public interface MotionApplier extends MotionModifier, LocationUpdater {

    /**
     * Return the current transformation.
     *
     * @return A {@link Coordinate2D} representing the transformation applied on {@link LocationUpdater#updateLocation(Point2D)}
     */
    Coordinate2D get();

    /**
     * Return the previous location. This Object is exposed to resolve an issue with the
     * fact that collision detection occurs after all Entities are updated. If an
     * Entity sets its speed to 0 on collision detection, it still received its last motion.
     * <p>
     * Because of that it is impossible that halt an Entities movement if the user continues
     * to press the movement buttons or another system is continuing to set the speed to a positive
     * value.
     *
     * @return An {@link Optional} containing the {@link Coordinate2D} representing the previous location of the
     * Entity.
     */
    Optional<Coordinate2D> getPreviousLocation();

    boolean isHalted();

    void setHalted(boolean halted);
}
