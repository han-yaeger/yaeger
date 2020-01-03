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
        sut.setMotionTo(1, Direction.DOWN.getValue());
        sut.multiplySpeedWith(SPEED_MULTIPLACTION_FRACTION).get();

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
        var updatedMotion = sut.multiplySpeedWith(0).get();

        // Verify
        assertEquals(0, updatedMotion.magnitude());
    }

    @Test
    void motionIsImmutable() {
        // Setup

        // Test
        var updatedMotion = sut.multiplySpeedWith(0).get();

        // Verify
        assertNotSame(DEFAULT_MOVEMENT_UP, updatedMotion);
    }

    @Test
    void multiplySpeedOfOneKeepsMotionFromConstructor() {
        // Setup
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.multiplySpeedWith(1).get();

        // Verify
        assertEquals(0, updatedMotion.getX(), DELTA);
        assertEquals(1, updatedMotion.getY(), DELTA);
    }

    @Test
    void multiplySpeedOfMinusOneInvertsMotionFromConstructor() {
        // Setup
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.multiplySpeedWith(-1).get();

        // Verify
        assertEquals(0, updatedMotion.getX(), DELTA);
        assertEquals(-1, updatedMotion.getY(), DELTA);
    }

    @Test
    void multiplySpeedMultipliesSpeed() {
        // Setup
        sut.setMotionTo(0.5, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.multiplySpeedWith(2).get();

        // Verify
        assertEquals(0, updatedMotion.getX(), DELTA);
        assertEquals(1, updatedMotion.getY(), DELTA);
    }

    @Test
    void setSpeedToZeroFreezesMotion() {
        // Setup
        sut.setMotionTo(1, Direction.UP.getValue());

        // Test
        var updatedMotion = sut.setSpeedTo(0).get();

        // Verify
        assertEquals(DEFAULT_START_LOCATION.getX(), updatedMotion.getX(), DELTA);
        assertEquals(DEFAULT_START_LOCATION.getY(), updatedMotion.getY(), DELTA);
    }

    @Test
    void setSpeedToOneSetsSpeedToOne() {
        // Setup
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.setSpeedTo(1).get();

        // Verify
        assertEquals(0, updatedMotion.getX(), DELTA);
        assertEquals(1, updatedMotion.getY(), DELTA);
    }

    @Test
    void changeDirection180WhenRightChangesToLeft() {
        // Setup
        sut.setMotionTo(1, Direction.RIGHT.getValue());

        // Test
        var updatedMotion = sut.changeDirectionBy(HALF_ROTATION).get();

        // Verify
        assertEquals(-1, updatedMotion.getX(), DELTA);
        assertEquals(0, updatedMotion.getY(), DELTA);
    }

    @Test
    void changeDirection180WhenLeftChangesToRight() {
        // Setup
        sut.setMotionTo(1, Direction.LEFT.getValue());

        // Test
        var updatedMotion = sut.changeDirectionBy(HALF_ROTATION).get();

        // Verify
        assertEquals(1, updatedMotion.getX(), DELTA);
        assertEquals(0, updatedMotion.getY(), DELTA);
    }

    @Test
    void alterSpeedIncrementsSpeed() {
        // Setup
        sut.setMotionTo(1, Direction.UP.getValue());
        var increment = 0.1d;

        // Test
        var updatedMotion = sut.alterSpeedBy(increment).get();

        // Verify
        assertEquals(DEFAULT_START_LOCATION.magnitude() + DEFAULT_MOVEMENT_UP.magnitude() + increment, updatedMotion.magnitude(), DELTA);
    }

    @Test
    void alterSpeedWithNegativeValueDecrementsSpeed() {
        // Setup
        sut.setMotionTo(1, Direction.UP.getValue());
        var increment = -0.1d;

        // Test
        var updatedMotion = sut.alterSpeedBy(increment).get();

        // Verify
        assertEquals(DEFAULT_START_LOCATION.magnitude() + DEFAULT_MOVEMENT_UP.magnitude() + increment, updatedMotion.magnitude(), DELTA);
    }

    @Test
    void getSpeedReturnsCorrectValue() {
        // Setup
        var SPEED = 3.7;
        sut.setMotionTo(SPEED, Direction.UP.getValue());

        // Test
        var speed = sut.getSpeed();

        // Verify
        assertEquals(SPEED, speed, DELTA);
    }

    @Test
    void setDirectionTo90SetsDirectionRight() {
        // Setup
        sut.setMotionTo(1, Direction.UP.getValue());

        // Test
        var updatedMotion = sut.setDirectionTo(Direction.RIGHT.getValue()).get();

        // Verify
        assertEquals(1, updatedMotion.getX(), DELTA);
        assertEquals(0, updatedMotion.getY(), DELTA);
    }

    @Test
    void setDirectionTo180SetsDirectionUp() {
        // Setup
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.setDirectionTo(Direction.UP.getValue()).get();

        // Verify
        assertEquals(0, updatedMotion.getX(), DELTA);
        assertEquals(-1, updatedMotion.getY(), DELTA);
    }

    @Test
    void setDirectionTo270SetsDirectionLeft() {
        // Setup
        sut.setMotionTo(1, Direction.UP.getValue());

        // Test
        var updatedMotion = sut.setDirectionTo(Direction.LEFT.getValue()).get();

        // Verify
        assertEquals(-1, updatedMotion.getX(), DELTA);
        assertEquals(0, updatedMotion.getY(), DELTA);
    }

    @Test
    void getDirectionForDirectionBelow180() {
        // Setup
        final double DIRECTION = 42;
        sut.setMotionTo(1, DIRECTION);

        // Test
        var direction = sut.getDirection();

        // Verify
        assertEquals(DIRECTION, direction, DELTA);
    }

    @Test
    void getDirectionForDirectionAbove180() {
        // Setup
        final double DIRECTION = 189;
        sut.setMotionTo(1, DIRECTION);

        // Test
        var direction = sut.getDirection();

        // Verify
        assertEquals(DIRECTION, direction, DELTA);
    }

    @Test
    void changeDirectionWithZeroDoesNotChangeAngle() {
        // Setup
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.changeDirectionBy(0).get();

        // Verify
        assertEquals(0d, DEFAULT_MOVEMENT_UP.angle(updatedMotion), DELTA);
    }

    @Test
    void changeDirectionWithZeroDoesNotChangeSpeed() {
        // Setup
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.changeDirectionBy(0).get();

        // Verify
        assertEquals(DEFAULT_MOVEMENT_UP.getX(), updatedMotion.getX(), DELTA);
        assertEquals(DEFAULT_MOVEMENT_UP.getY(), updatedMotion.getY(), DELTA);
    }

    @Test
    void changeDirectionChangesTheAngle() {
        // Setup
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.changeDirectionBy(ANGLE).get();

        // Verify
        assertEquals(ANGLE, DEFAULT_MOVEMENT_UP.angle(updatedMotion), DELTA);
    }

    @Test
    void changeDirectionWithNegativeChangesTheAngle() {
        // Setup
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.changeDirectionBy(ANGLE_INVERSE_NEGATIVE).get();

        // Verify
        assertEquals(Math.abs(ANGLE), DEFAULT_MOVEMENT_UP.angle(updatedMotion), DELTA);
    }

    @Test
    void changeDirectionWithClockwiseEqualsCounterClockwise() {
        // Setup
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.changeDirectionBy(FULL_ROTATION_MINUS_NEGATIVE_ANGLE).get();

        // Verify
        assertEquals(Math.abs(FULL_ROTATION - FULL_ROTATION_MINUS_NEGATIVE_ANGLE), DEFAULT_MOVEMENT_UP.angle(updatedMotion), DELTA);
    }

    @Test
    void changeDirectionZeroDoesNotChangeAngle() {
        // Setup
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Test
        var updatedMotion = sut.changeDirectionBy(0).get();

        // Verify
        assertEquals(0d, DEFAULT_MOVEMENT_UP.angle(updatedMotion), DELTA);
    }
}
