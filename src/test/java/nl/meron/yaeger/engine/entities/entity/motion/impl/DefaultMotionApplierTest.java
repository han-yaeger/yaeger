package nl.meron.yaeger.engine.entities.entity.motion.impl;

import javafx.geometry.Point2D;
import nl.meron.yaeger.engine.entities.entity.motion.DefaultMotionApplier;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultMotionApplierTest {

    private static final double DELTA = 0.00001d;
    private static final Point2D DEFAULT_START_LOCATION = new Point2D(0, 0);
    private static final Point2D DEFAULT_MOVEMENT_UP = new Point2D(0, 1);

    private static final double SPEED_MULTIPLACTION_FRACTION = 0.5;
    private static final double ANGLE = 6;
    private static final double ANGLE_INVERSE_NEGATIVE = -6;
    private static final double FULL_ROTATION_MINUS_NEGATIVE_ANGLE = 323;
    private static final double FULL_ROTATION = 360;
    private static final double HALF_ROTATION = 180;

    private DefaultMotionApplier sut;

    @BeforeEach
    void setup() {
        sut = new DefaultMotionApplier();
    }

    @Test
    void newInstanceHasNoMotion() {
        // Setup

        // Test
        var updatedLocation = sut.updateLocation(DEFAULT_START_LOCATION);

        // Verify
        assertEquals(DEFAULT_START_LOCATION.getX(), updatedLocation.getX(), DELTA);
        assertEquals(DEFAULT_START_LOCATION.getY(), updatedLocation.getY(), DELTA);
    }

    @Test
    void speedWithNoAngleDefaultsToDirectionOfZero() {
        // Setup
        sut.setMotion(1, Direction.DOWN.getValue());
        sut.multiplySpeed(SPEED_MULTIPLACTION_FRACTION).get();

        // Test
        var updatedLocation = sut.updateLocation(DEFAULT_START_LOCATION);

        // Verify
        assertEquals(DEFAULT_START_LOCATION.getY(), updatedLocation.getX(), DELTA);
        assertEquals(DEFAULT_START_LOCATION.getY() + SPEED_MULTIPLACTION_FRACTION, updatedLocation.getY(), DELTA);
    }

    @Test
    void multiplySpeedOfZeroFreezesMotion() {
        // Setup

        // Test
        var updatedMotion = sut.multiplySpeed(0).get();

        // Verify
        assertEquals(0, updatedMotion.magnitude());
    }

    @Test
    void motionIsImmutable() {
        // Setup

        // Test
        var updatedMotion = sut.multiplySpeed(0).get();

        // Verify
        assertNotSame(DEFAULT_MOVEMENT_UP, updatedMotion);
    }

    @Test
    void multiplySpeedOfOneKeepsMotionFromConstructor() {
        // Setup
        sut.setMotion(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.multiplySpeed(1).get();

        // Verify
        assertEquals(0, updatedMotion.getX(), DELTA);
        assertEquals(1, updatedMotion.getY(), DELTA);
    }

    @Test
    void multiplySpeedOfMinusOneInvertsMotionFromConstructor() {
        // Setup
        sut.setMotion(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.multiplySpeed(-1).get();

        // Verify
        assertEquals(0, updatedMotion.getX(), DELTA);
        assertEquals(-1, updatedMotion.getY(), DELTA);
    }

    @Test
    void multiplySpeedMultipliesSpeed() {
        // Setup
        sut.setMotion(0.5, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.multiplySpeed(2).get();

        // Verify
        assertEquals(0, updatedMotion.getX(), DELTA);
        assertEquals(1, updatedMotion.getY(), DELTA);
    }

    @Test
    void setSpeedToZeroFreezesMotion() {
        // Setup
        sut.setMotion(1, Direction.UP.getValue());

        // Test
        var updatedMotion = sut.setSpeed(0).get();

        // Verify
        assertEquals(DEFAULT_START_LOCATION.getX(), updatedMotion.getX(), DELTA);
        assertEquals(DEFAULT_START_LOCATION.getY(), updatedMotion.getY(), DELTA);
    }

    @Test
    void setSpeedToOneSetsSpeedToOne() {
        // Setup
        sut.setMotion(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.setSpeed(1).get();

        // Verify
        assertEquals(0, updatedMotion.getX(), DELTA);
        assertEquals(1, updatedMotion.getY(), DELTA);
    }

    @Test
    void changeDirection180WhenRightChangesToLeft() {
        // Setup
        sut.setMotion(1, Direction.RIGHT.getValue());

        // Test
        var updatedMotion = sut.changeDirection(HALF_ROTATION).get();

        // Verify
        assertEquals(-1, updatedMotion.getX(), DELTA);
        assertEquals(0, updatedMotion.getY(), DELTA);
    }

    @Test
    void changeDirection180WhenLeftChangesToRight() {
        // Setup
        sut.setMotion(1, Direction.LEFT.getValue());

        // Test
        var updatedMotion = sut.changeDirection(HALF_ROTATION).get();

        // Verify
        assertEquals(1, updatedMotion.getX(), DELTA);
        assertEquals(0, updatedMotion.getY(), DELTA);
    }

    @Test
    void alterSpeedIncrementsSpeed() {
        // Setup
        sut.setMotion(1, Direction.UP.getValue());
        var increment = 0.1d;

        // Test
        var updatedMotion = sut.alterSpeed(increment).get();

        // Verify
        assertEquals(DEFAULT_START_LOCATION.magnitude() + DEFAULT_MOVEMENT_UP.magnitude() + increment, updatedMotion.magnitude(), DELTA);
    }

    @Test
    void alterSpeedWithNegativeValueDecrementsSpeed() {
        // Setup
        sut.setMotion(1, Direction.UP.getValue());
        var increment = -0.1d;

        // Test
        var updatedMotion = sut.alterSpeed(increment).get();

        // Verify
        assertEquals(DEFAULT_START_LOCATION.magnitude() + DEFAULT_MOVEMENT_UP.magnitude() + increment, updatedMotion.magnitude(), DELTA);
    }

    @Test
    void setDirectionTo90SetsDirectionRight() {
        // Setup
        sut.setMotion(1, Direction.UP.getValue());

        // Test
        var updatedMotion = sut.setDirection(Direction.RIGHT.getValue()).get();

        // Verify
        assertEquals(1, updatedMotion.getX(), DELTA);
        assertEquals(0, updatedMotion.getY(), DELTA);
    }

    @Test
    void setDirectionTo180SetsDirectionUp() {
        // Setup
        sut.setMotion(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.setDirection(Direction.UP.getValue()).get();

        // Verify
        assertEquals(0, updatedMotion.getX(), DELTA);
        assertEquals(-1, updatedMotion.getY(), DELTA);
    }

    @Test
    void setDirectionTo270SetsDirectionLeft() {
        // Setup
        sut.setMotion(1, Direction.UP.getValue());

        // Test
        var updatedMotion = sut.setDirection(Direction.LEFT.getValue()).get();

        // Verify
        assertEquals(-1, updatedMotion.getX(), DELTA);
        assertEquals(0, updatedMotion.getY(), DELTA);
    }

    @Test
    void changeDirectionWithZeroDoesNotChangeAngle() {
        // Setup
        sut.setMotion(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.changeDirection(0).get();

        // Verify
        assertEquals(0d, DEFAULT_MOVEMENT_UP.angle(updatedMotion), DELTA);
    }

    @Test
    void changeDirectionWithZeroDoesNotChangeSpeed() {
        // Setup
        sut.setMotion(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.changeDirection(0).get();

        // Verify
        assertEquals(DEFAULT_MOVEMENT_UP.getX(), updatedMotion.getX(), DELTA);
        assertEquals(DEFAULT_MOVEMENT_UP.getY(), updatedMotion.getY(), DELTA);
    }

    @Test
    void changeDirectionChangesTheAngle() {
        // Setup
        sut.setMotion(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.changeDirection(ANGLE).get();

        // Verify
        assertEquals(ANGLE, DEFAULT_MOVEMENT_UP.angle(updatedMotion), DELTA);
    }

    @Test
    void changeDirectionWithNegativeChangesTheAngle() {
        // Setup
        sut.setMotion(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.changeDirection(ANGLE_INVERSE_NEGATIVE).get();

        // Verify
        assertEquals(Math.abs(ANGLE), DEFAULT_MOVEMENT_UP.angle(updatedMotion), DELTA);
    }

    @Test
    void changeDirectionWithClockwiseEqualsCounterClockwise() {
        // Setup
        sut.setMotion(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.changeDirection(FULL_ROTATION_MINUS_NEGATIVE_ANGLE).get();

        // Verify
        assertEquals(Math.abs(FULL_ROTATION - FULL_ROTATION_MINUS_NEGATIVE_ANGLE), DEFAULT_MOVEMENT_UP.angle(updatedMotion), DELTA);
    }

    @Test
    void changeDirectionZeroDoesNotChangeAngle() {
        // Setup
        sut.setMotion(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.changeDirection(0).get();

        // Verify
        assertEquals(0d, DEFAULT_MOVEMENT_UP.angle(updatedMotion), DELTA);
    }
}
