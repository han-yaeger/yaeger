package com.github.hanyaeger.api.engine.entities.entity.motion;

/**
 * A {@link MotionModifier} is capable of modifying a motion.
 */
public interface MotionModifier extends SpeedProvider, DirectionProvider {

    /**
     * Set the motion with which this {@link com.github.hanyaeger.api.engine.entities.entity.YaegerEntity}
     * is travelling.
     *
     * @param speed     the speed as a {@code double}
     * @param direction the direction in degrees as a {@code double}
     */
    void setMotion(final double speed, final double direction);

    /**
     * Set the motion with which this {@link com.github.hanyaeger.api.engine.entities.entity.YaegerEntity}
     * is travelling.
     *
     * @param speed     the speed as a {@code double}
     * @param direction the direction as a {@link Direction}
     */
    void setMotion(final double speed, final Direction direction);

    /**
     * Alter the speed through multiplication. Using this method will increase or decrease the current speed. It will multiply the current
     * speed by the provided value.
     * <p>
     * If it is required to set the speed to a specific value, use the method {@link #setSpeed(double)}}.
     *
     * @param multiplication a value greater than 1 will mean an increment in speed. A value between 0 and 1 will mean a
     *                       decrement in speed
     */
    void multiplySpeed(final double multiplication);

    /**
     * Alter the speed through addition.  Using this method will increase or decrease the current speed. It will add the provided value
     * to the current speed. Use a negative value to decrease the speed.
     *
     * @param increment a value greater than 1 will mean an increment in speed. A value between 0 and 1 will mean a
     *                  decrement in speed
     */
    void incrementSpeed(final double increment);

    /**
     * Set the speed.
     *
     * @param speed the speed as a {@code double}
     */
    void setSpeed(final double speed);

    /**
     * Set the {@link Direction}. This value is in degrees, where
     *
     * <ul>
     * <li>0 means up</li>
     * <li>90 means to the right</li>
     * <li>180 means down</li>
     * <li>270 to the left</li>
     * </ul>
     * <p>
     * If one of the values above is set, it is more convinient to use the method {@link #setDirection(Direction)},
     * which accepts a {@link Direction} as its parameter.
     *
     * @param direction the direction in degrees as a {@code double}
     */
    void setDirection(final double direction);

    /**
     * Set the {@link Direction}, which is in essence a value in degrees, but by using this
     * method, one can use the enumeration {@link Direction}. If a more specific value is
     * required, use the method {@link #setDirection(double)}.
     *
     * @param direction a {@link Direction}
     */
    void setDirection(final Direction direction);

    /**
     * Change the direction by adding a rotation in degrees. A positive value will be added and will result
     * in a clockwise rotation. A negative value will be subtracted and will result in a counter clockwise
     * rotation.
     *
     * @param rotation the rotation as a double
     */
    void changeDirection(final double rotation);
}
