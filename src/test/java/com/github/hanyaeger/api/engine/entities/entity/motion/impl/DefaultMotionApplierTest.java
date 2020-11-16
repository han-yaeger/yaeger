package com.github.hanyaeger.api.engine.entities.entity.motion.impl;

import com.github.hanyaeger.api.engine.entities.entity.motion.DefaultMotionApplier;
import com.github.hanyaeger.api.engine.entities.entity.motion.Direction;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultMotionApplierTest {

    private static final double DELTA = 0.00001d;
    private static final Point2D DEFAULT_START_LOCATION = new Point2D(0, 0);
    private static final Point2D DEFAULT_MOVEMENT_UP = new Point2D(0, 1);

    private static final double SPEED_MULTIPLACTION_FRACTION = 0.5;
    private static final Direction DIRECTION_ENUM = Direction.RIGHT;
    private static final double DIRECTION = 6;
    private static final double DIRECTION_INVERSE_NEGATIVE = -1 * DIRECTION;
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
        // Arrange

        // Act
        var updatedLocation = sut.updateLocation(DEFAULT_START_LOCATION);

        // Assert
        assertEquals(DEFAULT_START_LOCATION.getX(), updatedLocation.getX(), DELTA);
        assertEquals(DEFAULT_START_LOCATION.getY(), updatedLocation.getY(), DELTA);
    }

    @Test
    void newInstanceHasEmptyPreviousLocation() {
        // Arrange

        // Act

        // Assert
        assertTrue(sut.getPreviousLocation().isEmpty());
    }

    @Test
    void setHaltedIsStored() {
        // Arrange
        sut.setHalted(true);

        // Act
        var halted = sut.isHalted();

        // Assert
        assertTrue(halted);
    }

    @Test
    void speedWithNoAngleDefaultsToDirectionOfZero() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());
        sut.multiplySpeed(SPEED_MULTIPLACTION_FRACTION);

        // Act
        var updatedLocation = sut.updateLocation(DEFAULT_START_LOCATION);

        // Assert
        assertEquals(DEFAULT_START_LOCATION.getY(), updatedLocation.getX(), DELTA);
        assertEquals(DEFAULT_START_LOCATION.getY() + SPEED_MULTIPLACTION_FRACTION, updatedLocation.getY(), DELTA);
    }

    @Test
    void multiplySpeedOfZeroFreezesMotion() {
        // Arrange

        // Act
        sut.multiplySpeed(0);

        // Assert
        assertEquals(0, sut.get().magnitude());
    }

    @Test
    void isMotionImmutable() {
        // Arrange

        // Act
        sut.multiplySpeed(0);

        // Assert
        assertNotSame(DEFAULT_MOVEMENT_UP, sut.get());
    }

    @Test
    void multiplySpeedOfOneKeepsMotionFromConstructor() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());

        // Act
        sut.multiplySpeed(1);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(1, sut.get().getY(), DELTA);
    }

    @Test
    void multiplySpeedOfMinusOneInvertsMotionFromConstructor() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());

        // Act
        sut.multiplySpeed(-1);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(-1, sut.get().getY(), DELTA);
    }

    @Test
    void multiplySpeedMultipliesSpeed() {
        // Arrange
        sut.setMotion(0.5, Direction.DOWN.getValue());

        // Act
        sut.multiplySpeed(2);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(1, sut.get().getY(), DELTA);
    }

    @Test
    void setSpeedToZeroFreezesLocationIfPreviousSpeedNotZero() {
        // Arrange
        sut.setMotion(1, Direction.UP.getValue());

        // Act
        sut.setSpeed(0);

        // Assert
        assertEquals(DEFAULT_START_LOCATION.getX(), sut.get().getX(), DELTA);
        assertEquals(DEFAULT_START_LOCATION.getY(), sut.get().getY(), DELTA);
        assertTrue(sut.isHalted());
    }

    @Test
    void setSpeedToZeroFreezesLocationButNotHaltedIfPreviousSpeedZero() {
        // Arrange
        sut.setMotion(0, Direction.UP.getValue());

        // Act
        sut.setSpeed(0);

        // Assert
        assertEquals(DEFAULT_START_LOCATION.getX(), sut.get().getX(), DELTA);
        assertEquals(DEFAULT_START_LOCATION.getY(), sut.get().getY(), DELTA);
        assertFalse(sut.isHalted());
    }

    @Test
    void setSpeedToOneSetsSpeedToOne() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());

        // Act
        sut.setSpeed(1);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(1, sut.get().getY(), DELTA);
    }

    @Test
    void changeDirection180WhenRightChangesToLeft() {
        // Arrange
        sut.setMotion(1, Direction.RIGHT.getValue());

        // Act
        sut.changeDirection(HALF_ROTATION);

        // Assert
        assertEquals(-1, sut.get().getX(), DELTA);
        assertEquals(0, sut.get().getY(), DELTA);
    }

    @Test
    void changeDirection180WhenLeftChangesToRight() {
        // Arrange
        sut.setMotion(1, Direction.LEFT.getValue());

        // Act
        sut.changeDirection(HALF_ROTATION);

        // Assert
        assertEquals(1, sut.get().getX(), DELTA);
        assertEquals(0, sut.get().getY(), DELTA);
    }

    @Test
    void alterSpeedIncrementsSpeed() {
        // Arrange
        sut.setMotion(1, Direction.UP.getValue());
        var increment = 0.1d;

        // Act
        sut.incrementSpeed(increment);

        // Assert
        assertEquals(DEFAULT_START_LOCATION.magnitude() + DEFAULT_MOVEMENT_UP.magnitude() + increment, sut.get().magnitude(), DELTA);
    }

    @Test
    void alterSpeedWithNegativeValueDecrementsSpeed() {
        // Arrange
        sut.setMotion(1, Direction.UP.getValue());
        var increment = -0.1d;

        // Act
        sut.incrementSpeed(increment);

        // Assert
        assertEquals(DEFAULT_START_LOCATION.magnitude() + DEFAULT_MOVEMENT_UP.magnitude() + increment, sut.get().magnitude(), DELTA);
    }

    @Test
    void getSpeedReturnsCorrectValue() {
        // Arrange
        var SPEED = 3.7;
        sut.setMotion(SPEED, Direction.UP.getValue());

        // Act
        var speed = sut.getSpeed();

        // Assert
        assertEquals(SPEED, speed, DELTA);
    }

    @Test
    void setDirectionRIGHTForSpeedOneCreatesRightVector() {
        // Arrange
        sut.setSpeed(1);

        // Act
        sut.setDirection(Direction.RIGHT);

        // Assert
        assertEquals(1, sut.get().getX(), DELTA);
        assertEquals(0, sut.get().getY(), DELTA);
    }

    @Test
    void setDirectionToUPForSpeedOneCreatesUpVector() {
        // Arrange
        sut.setSpeed(1);

        // Act
        sut.setDirection(Direction.UP);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(-1, sut.get().getY(), DELTA);
    }

    @Test
    void setDirectionToLEFTForSpeedOneCreatesLEFTVector() {
        // Arrange
        sut.setSpeed(1);

        // Act
        sut.setDirection(Direction.LEFT);

        // Assert
        assertEquals(-1, sut.get().getX(), DELTA);
        assertEquals(0, sut.get().getY(), DELTA);
    }

    @Test
    void setDirectionDOWNForSpeedOneCreatesDownVector() {
        // Arrange
        sut.setSpeed(1);

        // Act
        sut.setDirection(Direction.DOWN);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(1, sut.get().getY(), DELTA);
    }

    @Test
    void setDirectionWithZeroSpeedCreatesZeroVector(){
        // Arrange

        // Act
        sut.setDirection(Direction.LEFT);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(0, sut.get().getY(), DELTA);
    }

    @Test
    void setSpeedAfterDirectionCreatesCorrectVector(){
        // Arrange
        sut.setDirection(Direction.UP);

        // Act
        sut.setSpeed(1);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(-1, sut.get().getY(), DELTA);
    }

    @Test
    void setSpeedBeforeDirectionCreatesCorrectVector(){
        // Arrange
        sut.setSpeed(1);

        // Act
        sut.setDirection(Direction.UP);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(-1, sut.get().getY(), DELTA);
    }

    @Test
    void resetSpeedAfterSpeedHasBeenSetToZeroCreatesCorrectVector(){
        // Arrange
        sut.setSpeed(1);
        sut.setDirection(Direction.UP);
        sut.setSpeed(0);

        // Act
        sut.setSpeed(1);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(-1, sut.get().getY(), DELTA);
    }

    @Test
    void getDirectionForDirectionBelow180() {
        // Arrange
        final double DIRECTION = 42;
        sut.setMotion(1, DIRECTION);

        // Act
        var direction = sut.getDirection();

        // Assert
        assertEquals(DIRECTION, direction, DELTA);
    }

    @Test
    void getDirectionsReturnsNumericValueWhenEnumIsForDirectionWasUsed() {
        // Arrange
        sut.setDirection(DIRECTION_ENUM);

        // Act
        var direction = sut.getDirection();

        // Assert
        assertEquals(DIRECTION_ENUM.getValue(), direction, DELTA);
    }

    @Test
    void getDirectionsReturnsNumericValueWhenEnumIsForDirectionWasUsedInMotion() {
        // Arrange
        sut.setMotion(1, DIRECTION_ENUM);

        // Act
        var direction = sut.getDirection();

        // Assert
        assertEquals(DIRECTION_ENUM.getValue(), direction, DELTA);
    }

    @Test
    void getDirectionForDirectionAbove180() {
        // Arrange
        final double DIRECTION = 189;
        sut.setMotion(1, DIRECTION);

        // Act
        var direction = sut.getDirection();

        // Assert
        assertEquals(DIRECTION, direction, DELTA);
    }

    @Test
    void changeDirectionWithZeroDoesNotChangeAngle() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());

        // Act
        sut.changeDirection(0);

        // Assert
        assertEquals(0d, DEFAULT_MOVEMENT_UP.angle(sut.get()), DELTA);
    }

    @Test
    void changeDirectionWithZeroDoesNotChangeSpeed() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());

        // Act
        sut.changeDirection(0);

        // Assert
        assertEquals(DEFAULT_MOVEMENT_UP.getX(), sut.get().getX(), DELTA);
        assertEquals(DEFAULT_MOVEMENT_UP.getY(), sut.get().getY(), DELTA);
    }

    @Test
    void changeDirectionChangesTheAngle() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());

        // Act
        sut.changeDirection(DIRECTION);

        // Assert
        assertEquals(DIRECTION, DEFAULT_MOVEMENT_UP.angle(sut.get()), DELTA);
    }

    @Test
    void changeDirectionWithNegativeChangesTheAngle() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());

        // Act
        sut.changeDirection(DIRECTION_INVERSE_NEGATIVE);

        // Assert
        assertEquals(Math.abs(DIRECTION), DEFAULT_MOVEMENT_UP.angle(sut.get()), DELTA);
    }

    @Test
    void changeDirectionWithClockwiseEqualsCounterClockwise() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());

        // Act
        sut.changeDirection(FULL_ROTATION_MINUS_NEGATIVE_ANGLE);

        // Assert
        assertEquals(Math.abs(FULL_ROTATION - FULL_ROTATION_MINUS_NEGATIVE_ANGLE), DEFAULT_MOVEMENT_UP.angle(sut.get()), DELTA);
    }

    @Test
    void changeDirectionZeroDoesNotChangeAngle() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());

        // Act
        sut.changeDirection(0);

        // Assert
        assertEquals(0d, DEFAULT_MOVEMENT_UP.angle(sut.get()), DELTA);
    }

    @Test
    void getPreviousLocationReturnsPreviousLocation() {
        // Arrange
        var expected = new Point2D(37, 42);
        sut.setMotion(4, Direction.DOWN.getValue());

        // Act
        sut.updateLocation(expected);
        var actual = sut.getPreviousLocation().get();

        // Assert
        Assertions.assertEquals(expected, actual);
    }
}
