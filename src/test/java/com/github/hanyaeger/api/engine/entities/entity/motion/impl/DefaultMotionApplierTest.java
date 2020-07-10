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
        // Arrange

        // Act
        var updatedLocation = sut.updateLocation(DEFAULT_START_LOCATION);

        // Assert
        assertEquals(DEFAULT_START_LOCATION.getX(), updatedLocation.getX(), DELTA);
        assertEquals(DEFAULT_START_LOCATION.getY(), updatedLocation.getY(), DELTA);
    }

    @Test
    void speedWithNoAngleDefaultsToDirectionOfZero() {
        // Arrange
        sut.setMotionTo(1, Direction.DOWN.getValue());
        sut.multiplySpeedWith(SPEED_MULTIPLACTION_FRACTION);

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
        sut.multiplySpeedWith(0);

        // Assert
        assertEquals(0, sut.get().magnitude());
    }

    @Test
    void motionIsImmutable() {
        // Arrange

        // Act
        sut.multiplySpeedWith(0);

        // Assert
        assertNotSame(DEFAULT_MOVEMENT_UP, sut.get());
    }

    @Test
    void multiplySpeedOfOneKeepsMotionFromConstructor() {
        // Arrange
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Act
        sut.multiplySpeedWith(1);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(1, sut.get().getY(), DELTA);
    }

    @Test
    void multiplySpeedOfMinusOneInvertsMotionFromConstructor() {
        // Arrange
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Act
        sut.multiplySpeedWith(-1);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(-1, sut.get().getY(), DELTA);
    }

    @Test
    void multiplySpeedMultipliesSpeed() {
        // Arrange
        sut.setMotionTo(0.5, Direction.DOWN.getValue());

        // Act
        sut.multiplySpeedWith(2);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(1, sut.get().getY(), DELTA);
    }

    @Test
    void setSpeedToZeroFreezesMotion() {
        // Arrange
        sut.setMotionTo(1, Direction.UP.getValue());

        // Act
        sut.setSpeedTo(0);

        // Assert
        assertEquals(DEFAULT_START_LOCATION.getX(), sut.get().getX(), DELTA);
        assertEquals(DEFAULT_START_LOCATION.getY(), sut.get().getY(), DELTA);
    }

    @Test
    void setSpeedToOneSetsSpeedToOne() {
        // Arrange
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Act
        sut.setSpeedTo(1);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(1, sut.get().getY(), DELTA);
    }

    @Test
    void changeDirection180WhenRightChangesToLeft() {
        // Arrange
        sut.setMotionTo(1, Direction.RIGHT.getValue());

        // Act
        sut.changeDirectionBy(HALF_ROTATION);

        // Assert
        assertEquals(-1, sut.get().getX(), DELTA);
        assertEquals(0, sut.get().getY(), DELTA);
    }

    @Test
    void changeDirection180WhenLeftChangesToRight() {
        // Arrange
        sut.setMotionTo(1, Direction.LEFT.getValue());

        // Act
        sut.changeDirectionBy(HALF_ROTATION);

        // Assert
        assertEquals(1, sut.get().getX(), DELTA);
        assertEquals(0, sut.get().getY(), DELTA);
    }

    @Test
    void alterSpeedIncrementsSpeed() {
        // Arrange
        sut.setMotionTo(1, Direction.UP.getValue());
        var increment = 0.1d;

        // Act
        sut.alterSpeedBy(increment);

        // Assert
        assertEquals(DEFAULT_START_LOCATION.magnitude() + DEFAULT_MOVEMENT_UP.magnitude() + increment, sut.get().magnitude(), DELTA);
    }

    @Test
    void alterSpeedWithNegativeValueDecrementsSpeed() {
        // Arrange
        sut.setMotionTo(1, Direction.UP.getValue());
        var increment = -0.1d;

        // Act
        sut.alterSpeedBy(increment);

        // Assert
        assertEquals(DEFAULT_START_LOCATION.magnitude() + DEFAULT_MOVEMENT_UP.magnitude() + increment, sut.get().magnitude(), DELTA);
    }

    @Test
    void getSpeedReturnsCorrectValue() {
        // Arrange
        var SPEED = 3.7;
        sut.setMotionTo(SPEED, Direction.UP.getValue());

        // Act
        var speed = sut.getSpeed();

        // Assert
        assertEquals(SPEED, speed, DELTA);
    }

    @Test
    void setDirectionTo90SetsDirectionRight() {
        // Arrange
        sut.setMotionTo(1, Direction.UP.getValue());

        // Act
        sut.setDirectionTo(Direction.RIGHT.getValue());

        // Assert
        assertEquals(1, sut.get().getX(), DELTA);
        assertEquals(0, sut.get().getY(), DELTA);
    }

    @Test
    void setDirectionTo180SetsDirectionUp() {
        // Arrange
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Act
        sut.setDirectionTo(Direction.UP.getValue());

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(-1, sut.get().getY(), DELTA);
    }

    @Test
    void setDirectionTo270SetsDirectionLeft() {
        // Arrange
        sut.setMotionTo(1, Direction.UP.getValue());

        // Act
        sut.setDirectionTo(Direction.LEFT.getValue());

        // Assert
        assertEquals(-1, sut.get().getX(), DELTA);
        assertEquals(0, sut.get().getY(), DELTA);
    }

    @Test
    void getDirectionForDirectionBelow180() {
        // Arrange
        final double DIRECTION = 42;
        sut.setMotionTo(1, DIRECTION);

        // Act
        var direction = sut.getDirection();

        // Assert
        assertEquals(DIRECTION, direction, DELTA);
    }

    @Test
    void getDirectionForDirectionAbove180() {
        // Arrange
        final double DIRECTION = 189;
        sut.setMotionTo(1, DIRECTION);

        // Act
        var direction = sut.getDirection();

        // Assert
        assertEquals(DIRECTION, direction, DELTA);
    }

    @Test
    void changeDirectionWithZeroDoesNotChangeAngle() {
        // Arrange
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Act
        sut.changeDirectionBy(0);

        // Assert
        assertEquals(0d, DEFAULT_MOVEMENT_UP.angle(sut.get()), DELTA);
    }

    @Test
    void changeDirectionWithZeroDoesNotChangeSpeed() {
        // Arrange
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Act
        sut.changeDirectionBy(0);

        // Assert
        assertEquals(DEFAULT_MOVEMENT_UP.getX(), sut.get().getX(), DELTA);
        assertEquals(DEFAULT_MOVEMENT_UP.getY(), sut.get().getY(), DELTA);
    }

    @Test
    void changeDirectionChangesTheAngle() {
        // Arrange
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Act
        sut.changeDirectionBy(ANGLE);

        // Assert
        assertEquals(ANGLE, DEFAULT_MOVEMENT_UP.angle(sut.get()), DELTA);
    }

    @Test
    void changeDirectionWithNegativeChangesTheAngle() {
        // Arrange
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Act
        sut.changeDirectionBy(ANGLE_INVERSE_NEGATIVE);

        // Assert
        assertEquals(Math.abs(ANGLE), DEFAULT_MOVEMENT_UP.angle(sut.get()), DELTA);
    }

    @Test
    void changeDirectionWithClockwiseEqualsCounterClockwise() {
        // Arrange
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Act
        sut.changeDirectionBy(FULL_ROTATION_MINUS_NEGATIVE_ANGLE);

        // Assert
        assertEquals(Math.abs(FULL_ROTATION - FULL_ROTATION_MINUS_NEGATIVE_ANGLE), DEFAULT_MOVEMENT_UP.angle(sut.get()), DELTA);
    }

    @Test
    void changeDirectionZeroDoesNotChangeAngle() {
        // Arrange
        sut.setMotionTo(1, Direction.DOWN.getValue());

        // Act
        sut.changeDirectionBy(0);

        // Assert
        assertEquals(0d, DEFAULT_MOVEMENT_UP.angle(sut.get()), DELTA);
    }

    @Test
    void getPreviousLocationReturnsPreviousLocation() {
        // Arrange
        var expected = new Point2D(37, 42);
        sut.setMotionTo(4, Direction.DOWN.getValue());

        // Act
        sut.updateLocation(expected);
        var actual = sut.getPreviousLocation();

        // Assert
        Assertions.assertEquals(expected, actual);
    }
}
