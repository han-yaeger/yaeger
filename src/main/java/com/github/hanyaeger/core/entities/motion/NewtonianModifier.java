package com.github.hanyaeger.core.entities.motion;

/**
 * A {@link NewtonianModifier} is capable of modifying all properties that are related to the laws of
 * gravity and friction.
 */
public interface NewtonianModifier {

    /**
     * Set the friction constant to be used.
     *
     * @param frictionConstant the friction constant as a {@code double}
     */
    void setFrictionConstant(final double frictionConstant);

    /**
     * Return the friction constant used.
     *
     * @return the friction constant as a {@code double}
     */
    double getFrictionConstant();

    /**
     * Set the gravitational constant to be used.
     *
     * @param gravityConstant the gravitational constant as a {@code double}
     */
    void setGravityConstant(final double gravityConstant);

    /**
     * Return the gravitational constant used.
     *
     * @return the gravitational constant as a {@code double}
     */
    double getGravityConstant();

    /**
     * Set the gravitational direction used..
     *
     * @param gravityDirection the gravitational direction as a {@code double}
     */
    void setGravityDirection(final double gravityDirection);

    /**
     * Return the gravitational direction used.
     *
     * @return the gravitational direction as a {@code double}
     */
    double getGravityDirection();
}
