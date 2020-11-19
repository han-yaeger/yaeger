package com.github.hanyaeger.api.guice.factories;

import com.github.hanyaeger.api.engine.entities.entity.motion.DefaultMotionApplier;
import com.github.hanyaeger.api.engine.entities.entity.motion.MotionApplier;
import com.github.hanyaeger.api.engine.entities.entity.motion.MotionApplierType;
import com.github.hanyaeger.api.engine.entities.entity.motion.NewtonianMotionApplier;

/**
 * A {@link MotionApplierFactory} should be used to create instances of {@link MotionApplier}.
 */
public class MotionApplierFactory {

    /**
     * Create a correct implementation of {@link MotionApplier}.
     *
     * @param type the {@link MotionApplierType} that is requested
     * @return an implementation of {@link MotionApplier}
     */
    public MotionApplier create(final MotionApplierType type) {
        return switch (type) {
            case NEWTONIAN -> new NewtonianMotionApplier();
            default -> new DefaultMotionApplier();
        };
    }
}
