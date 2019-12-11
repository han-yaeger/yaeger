package nl.meron.yaeger.engine.entities.entity;

import nl.meron.yaeger.engine.entities.entity.motion.MotionApplier;
import nl.meron.yaeger.engine.entities.entity.motion.MotionModifier;

public interface Moveable extends Bounded, MotionModifier {

    /**
     * Set the {@link MotionApplier} that will be used.
     *
     * @param motionApplier an instance of {@link MotionApplier}
     */
    void setMotionApplier(MotionApplier motionApplier);

    /**
     * Return the {@link MotionApplier} that should be used.
     *
     * @return an instance of {@link MotionApplier}
     */
    MotionApplier getMotionApplier();
}
