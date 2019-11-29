package nl.meron.yaeger.engine.entities.entity.motion;

/**
 * Base interface of all Objects that apply a motion to a subclass of {@link nl.meron.yaeger.engine.entities.entity.Entity}.
 */
public interface MotionApplier extends LocationUpdater{

    /**
     * Alter the speed through multiplication. Using this method will increase or decrease the current speed. It will multiply the current
     * speed by the provided value.
     * <p>
     * If it is required to set the speed to a specific value, use the method {@link #setSpeed(double)}}.
     *
     * @param multiplication A value greater than 1 will mean an increment in speed. A value between 0 and 1 will mean a
     *                       decrement in speed.
     */
    void multiplySpeed(double multiplication);

    /**
     * Alter the speed through addition.  Using this method will increase or decrease the current speed. It will add the provided value
     * to the current speed. Use a negative value to decrease the speed.
     *
     * @param increment A value greater than 1 will mean an increment in speed. A value between 0 and 1 will mean a
     *                  decrement in speed.
     * @param increment A {@code double} representing the increment in speed.
     */
    void alterSpeed(double increment);

    /**
     * Set the speed.
     *
     * @param newSpeed the speed as a double
     */
    void setSpeed(double newSpeed);

    /**
     * Set the {@link MotionVector.Direction}. This value is in degrees, where
     *
     * <ul>
     * <li>0 means up</li>
     * <li>90 means to the right</li>
     * <li>180 means down</li>
     * <li>270 to the left</li>
     * </ul>
     * <p>
     *
     * @param newDirection the direction in degrees
     */
    void setDirection(double newDirection);

    /**
     * Change the direction by adding a rotation in degrees. A positive value will be added and will result
     * in a clockwise rotation. A negative value will be subtracted and will result in a counter clockwise
     * rotation.
     *
     * @param rotation the rotation as a double
     */
    void changeDirection(double rotation);
}
