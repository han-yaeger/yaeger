package com.github.hanyaeger.core.entities.motion;

import com.github.hanyaeger.api.entities.Direction;
import com.github.hanyaeger.api.entities.YaegerEntity;

/**
 * A {@link MotionModifier} is the basic interface that defines the API of all Objects
 * that capable of modifying the motion of a {@link YaegerEntity}.
 * <p>
 * This interface will be implemented by both the Entities that should expose the API to the user, as the objects
 * that actually apply the motion.
 */
public interface MotionModifier extends SpeedProvider, DirectionProvider {

    /**
     * Set the motion with which this {@link YaegerEntity}
     * is travelling.
     *
     * @param speed     the speed as a {@code double}
     * @param direction the direction in degrees as a {@code double}
     */
    void setMotion(final double speed, final double direction);

    /**
     * Set the motion with which this {@link YaegerEntity}
     * is travelling.
     *
     * @param speed     the speed as a {@code double}
     * @param direction the direction as a {@link Direction}
     */
    void setMotion(final double speed, final Direction direction);

    /**
     * Add a given motion to the current motion. Since motions are essentially vectors, defined by speed
     * and direction, this method results in the addition of those vectors.
     *
     * @param speed     the speed as a {@code double}
     * @param direction the direction as a {@code double}
     */
    void addToMotion(final double speed, final double direction);

    /**
     * Add a given motion to the current motion. Since motions are essentially vectors, defined by speed
     * and direction, this method results in the addition of those vectors.
     *
     * @param speed     the speed as a {@code double}
     * @param direction the direction as a {@link Direction}
     */
    void addToMotion(final double speed, final Direction direction);

    /**
     * @param direction the {@link Direction} in which the speed should be calculated
     * @return the speed of the decomposed vector in the specified direction
     * @see #getSpeedInDirection(double)
     */
    double getSpeedInDirection(final Direction direction);

    /**
     * Since a motion can be described as a vector, such a vector can be decomposed in two perpendicular
     * components, of which one is parallel to the given direction. Calling this method returns the
     * magnitude of that vector, which represents the speed in the given direction.
     *
     * @param direction the {@link Direction} in which the speed should be nullified
     * @return the speed of the decomposed vector in the specified direction
     */
    double getSpeedInDirection(final double direction);

    /**
     * @param direction the {@link Direction} in which the speed should be inverted
     * @see #invertSpeedInDirection(double)
     */
    void invertSpeedInDirection(final Direction direction);

    /**
     * Since a motion can be described as a vector, such a vector can be decomposed in two perpendicular
     * components, of which one is parallel to the given direction. Calling this method inverts the vector
     * parallel to the direction and computes a new motion, based on that vector.
     *
     * <p>
     * <img width="30%" src="doc-files/invert-motion-vectors.svg" alt="Vector representation of inverting the motion in a given direction">
     *
     * <p> In the image above, let <b>v</b> denote the vector associated with the current motion and <b>b</b> be the vector
     * in the direction that should be inverted. Then <b>b`</b> represents the inverse of <b>b</b> and <b>v`</b> the resulting
     * vector of the new motion.
     *
     * <p>
     * Note that for this method only the direction of <b>b</b> has to be given. The length of <b>b</b> is automatically
     * derived.
     *
     * <p>
     * A typical use case would be an entity that bounces on a plane. At one point it collides with
     * the ground, which should invert the downward (vertical) component of its motion. The horizontal motion,
     * however, should remain the same.
     *
     * @param direction the direction in which the speed should be inverted, as a {@code double}
     */
    void invertSpeedInDirection(final double direction);

    /**
     * @param direction the {@link Direction} in which the speed of the vector describing the motion, should be
     *                  maximized
     * @param speed     the speed as a {@link double} to which the vector should be maximized
     * @see #maximizeMotionInDirection(double, double)
     */
    void maximizeMotionInDirection(final Direction direction, final double speed);

    /**
     * Since the motion can be described as a vector, such a vector can be decomposed in two perpendicular
     * components. Calling this method maximize the vector for the component in the given {@link Direction} to
     * the given speed.
     * <p>
     * <img width="30%" src="doc-files/maximize-motion-vectors.svg" alt="Vector representation of maximizing the motion in a given direction">
     *
     * <p> In the image above, let <b>v</b> be the vector associated with the current motion and <b>r</b> be the
     * vector that represents the direction and speed that should be maximized, then the resulting vector <b>s</b> represents
     * the maximized motion.
     *
     * <p>
     * A typical use case would be when an entity has to accelerate to a maximum value into a specific direction,
     * while gravity (or any other force) should be preserved.
     *
     * @param direction the direction as a {@code double} in which the speed of the vector describing the motion, should be
     *                  maximized
     * @param speed     the speed as a {@link double} to which the vector should be maximized
     */
    void maximizeMotionInDirection(final double direction, final double speed);

    /**
     * @param direction the {@link Direction} in which the speed should be nullified
     * @see #nullifySpeedInDirection(double)
     */
    void nullifySpeedInDirection(final Direction direction);

    /**
     * Since the motion can be described as a vector, such a vector can be decomposed in two perpendicular
     * components, of which one is parallel to the given direction. Calling this method nullifies that vector,
     * leaving the other as the new motion.
     *
     * <p>
     * <img width="30%" src="doc-files/nullify-motion-vectors.svg" alt="Vector representation of negating the motion in a given direction">
     *
     * <p> In the image above, let <b>v</b> denote the vector associated with the current motion and <b>b</b> be the vector
     * in the direction that has to be nullified, then the resulting vector <b>p</b> represents the new motion.
     *
     * <p>
     * Note that for this method only the direction of <b>b</b> has to be given. The length of <b>b</b> is automatically
     * derived.
     *
     * <p>
     * A typical use case would be an entity that jumps with a parabolic motion. At one point it collides with
     * the ground, which should cancel the vertical downward motion. The vertical motion, however, should not be
     * cancelled. In this case, one should call this method with the parameter {@link Direction#DOWN}.
     *
     * @param direction the direction in which the speed should be nullified, as a {@code double}
     */
    void nullifySpeedInDirection(final double direction);

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
     * If one of the values above is set, it is more convenient to use the method {@link #setDirection(Direction)},
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
     * in a clockwise rotation. A negative value will be subtracted and will result in a counterclockwise
     * rotation.
     *
     * @param rotation the rotation as a double
     */
    void changeDirection(final double rotation);
}
