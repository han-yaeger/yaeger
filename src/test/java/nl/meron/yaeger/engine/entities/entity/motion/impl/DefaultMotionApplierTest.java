package nl.meron.yaeger.engine.entities.entity.motion.impl;

import javafx.geometry.Point2D;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultMotionApplierTest {

    private static final double DELTA = 0.00001d;
    private static final Point2D DEFAULT_START_LOCATION = new Point2D(0, 0);
    private static final Point2D DEFAULT_MOVEMENT_UP = new Point2D(0, 1);
    private static final Point2D FRACTIONAL_MOVEMENT_UP = new Point2D(0, 0.5);

    private DefaultMotionApplier sut;

    @BeforeEach
    void setup() {
        sut = new DefaultMotionApplier(DEFAULT_MOVEMENT_UP);
    }

    @Test
    void newInstanceAppliesMotionFromConstructor() {
        // Setup

        // Test
        Point2D updatedLocation = sut.updateLocation(DEFAULT_START_LOCATION);

        // Verify
        assertEquals(0, updatedLocation.getX(), DELTA);
        assertEquals(1, updatedLocation.getY(), DELTA);
    }

    @Test
    void multiplySpeedOfZeroFreezesMotion() {
        // Setup
        sut.multiplySpeed(0);

        // Test
        Point2D updatedLocation = sut.updateLocation(DEFAULT_START_LOCATION);

        // Verify
        assertEquals(DEFAULT_START_LOCATION.getX(), updatedLocation.getX(), DELTA);
        assertEquals(DEFAULT_START_LOCATION.getY(), updatedLocation.getY(), DELTA);
    }

    @Test
    void multiplySpeedOfOneKeepsMotionFromConstructor() {
        // Setup
        sut.multiplySpeed(1);

        // Test
        Point2D updatedLocation = sut.updateLocation(DEFAULT_START_LOCATION);

        // Verify
        assertEquals(0, updatedLocation.getX(), DELTA);
        assertEquals(1, updatedLocation.getY(), DELTA);
    }

    @Test
    void multiplySpeedOfMinusOneInvertsMotionFromConstructor() {
        // Setup
        sut.multiplySpeed(-1);

        // Test
        Point2D updatedLocation = sut.updateLocation(DEFAULT_START_LOCATION);

        // Verify
        assertEquals(0, updatedLocation.getX(), DELTA);
        assertEquals(-1, updatedLocation.getY(), DELTA);
    }

    @Test
    void multiplySpeedMultipliesSpeed() {
        // Setup
        sut = new DefaultMotionApplier(FRACTIONAL_MOVEMENT_UP);
        sut.multiplySpeed(2);

        // Test
        Point2D updatedLocation = sut.updateLocation(DEFAULT_START_LOCATION);

        // Verify
        assertEquals(0, updatedLocation.getX(), DELTA);
        assertEquals(1, updatedLocation.getY(), DELTA);
    }

    @Test
    void setSpeedToZeroFreezesMotion() {
        // Setup
        sut.setSpeed(0);

        // Test
        Point2D updatedLocation = sut.updateLocation(DEFAULT_START_LOCATION);

        // Verify
        assertEquals(DEFAULT_START_LOCATION.getX(), updatedLocation.getX(), DELTA);
        assertEquals(DEFAULT_START_LOCATION.getY(), updatedLocation.getY(), DELTA);
    }

    @Test
    void setSpeedToOneSetsSpeedToOne() {
        // Setup
        sut = new DefaultMotionApplier(FRACTIONAL_MOVEMENT_UP);
        sut.setSpeed(1);

        // Test
        Point2D updatedLocation = sut.updateLocation(DEFAULT_START_LOCATION);

        // Verify
        assertEquals(0, updatedLocation.getX(), DELTA);
        assertEquals(1, updatedLocation.getY(), DELTA);
    }

    @Test
    void alterSpeedIncrementsSpeed() {
        // Setup
        var increment = 0.1d;
        sut.alterSpeed(increment);

        // Test
        Point2D updatedLocation = sut.updateLocation(DEFAULT_START_LOCATION);

        // Verify
        assertEquals(DEFAULT_START_LOCATION.magnitude() + DEFAULT_MOVEMENT_UP.magnitude() + increment, updatedLocation.magnitude(), DELTA);
    }

    @Test
    void alterSpeedWithNegativeValueDecrementsSpeed() {
        // Setup
        var increment = -0.1d;
        sut.alterSpeed(increment);

        // Test
        Point2D updatedLocation = sut.updateLocation(DEFAULT_START_LOCATION);

        // Verify
        assertEquals(DEFAULT_START_LOCATION.magnitude() + DEFAULT_MOVEMENT_UP.magnitude() + increment, updatedLocation.magnitude(), DELTA);
    }

    @Test
    void setDirectionTo90SetsDirectionRight() {
        // Setup
        sut = new DefaultMotionApplier(DEFAULT_MOVEMENT_UP);
        sut.setDirection(Direction.RIGHT.getValue());

        // Test
        Point2D updatedLocation = sut.updateLocation(DEFAULT_START_LOCATION);

        // Verify
        assertEquals(1, updatedLocation.getX(), DELTA);
        assertEquals(0, updatedLocation.getY(), DELTA);
    }

    @Test
    void setDirectionTo180SetsDirectionDown() {
        // Setup
        sut = new DefaultMotionApplier(DEFAULT_MOVEMENT_UP);
        sut.setDirection(Direction.DOWN.getValue());

        // Test
        Point2D updatedLocation = sut.updateLocation(DEFAULT_START_LOCATION);

        // Verify
        assertEquals(0, updatedLocation.getX(), DELTA);
        assertEquals(-1, updatedLocation.getY(), DELTA);
    }

    @Test
    void setDirectionTo270SetsDirectionLeft() {
        // Setup
        sut = new DefaultMotionApplier(DEFAULT_MOVEMENT_UP);
        sut.setDirection(Direction.LEFT.getValue());

        // Test
        Point2D updatedLocation = sut.updateLocation(DEFAULT_START_LOCATION);

        // Verify
        assertEquals(-1, updatedLocation.getX(), DELTA);
        assertEquals(0, updatedLocation.getY(), DELTA);
    }
}
