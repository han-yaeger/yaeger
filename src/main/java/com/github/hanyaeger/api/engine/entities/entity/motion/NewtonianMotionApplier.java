package com.github.hanyaeger.api.engine.entities.entity.motion;

/**
 * A {@link NewtonianMotionApplier} is an implementation of {@link MotionApplier} that does not abide
 * the laws of Physics and only provides basis behaviour regarding speed and direction.
 */
public class NewtonianMotionApplier extends DefaultMotionApplier {

    public static final double DEFAULT_GRAVITATIONAL_CONSTANT = 0.1d;
    public static final double DEFAULT_GRAVITATIONAL_DIRECTION = Direction.DOWN.getValue();

    private double gravityConstant = DEFAULT_GRAVITATIONAL_CONSTANT;
    private double gravityDirection = DEFAULT_GRAVITATIONAL_DIRECTION;

    private boolean gravitationalPull = true;

    @Override
    public void setGravitationalPull(final boolean pull) {
        this.gravitationalPull = pull;
    }

    @Override
    public boolean isGravitationalPull() {
        return gravitationalPull;
    }

    /**
     * Return the gravitational contant used by this {@link NewtonianMotionApplier}.
     *
     * @return the gravitational constant as a {@code double}
     */
    public double getGravityConstant() {
        return gravityConstant;
    }

    /**
     * Set the gravitational contant used by this {@link NewtonianMotionApplier}.
     *
     * @param gravityConstant the gravitational constant as a {@code double}
     */
    public void setGravityConstant(double gravityConstant) {
        this.gravityConstant = gravityConstant;
    }

    /**
     * Return the gravitational direction used by this {@link NewtonianMotionApplier}.
     *
     * @return the gravitational direction as a {@code double}
     */
    public double getGravityDirection() {
        return gravityDirection;
    }

    /**
     * Set the gravitational direction used by this {@link NewtonianMotionApplier}.
     *
     * @param gravityDirection the gravitational constant as a {@code double}
     */
    public void setGravityDirection(double gravityDirection) {
        this.gravityDirection = gravityDirection;
    }
}
