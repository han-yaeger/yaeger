package com.github.hanyaeger.api.engine.entities.entity.motion;

/**
 * A {@link MotionApplier} is responsible for the motion of a {@link com.github.hanyaeger.api.engine.entities.entity.YaegerEntity}.
 * <p>
 * Currently two different types of {@link MotionApplier} are supported, the {@link #DEFAULT} and {@link #NEWTONIAN}.
 */
public enum MotionApplierType {
    DEFAULT,
    NEWTONIAN
}
