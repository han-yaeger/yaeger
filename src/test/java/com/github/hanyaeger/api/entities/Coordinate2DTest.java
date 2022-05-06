package com.github.hanyaeger.api.entities;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Coordinate2DTest {

    private static final double DELTA = 0.00000001;
    private static final Coordinate2D LOCATION = new Coordinate2D(37, 37);

    @Test
    void addingPointWorksAsExpected() {
        // Arrange
        var position = new Coordinate2D(0d, 0d);
        var positionToAdd = new Coordinate2D(1d, 1d);

        // Act
        var addedPosition = position.add(positionToAdd);

        // Assert
        assertEquals(1d, addedPosition.getX());
        assertEquals(1d, addedPosition.getY());
    }

    @Test
    void addingPoint2DWorksAsExpected() {
        // Arrange
        var position = new Coordinate2D(0d, 0d);
        var positionToAdd = new Point2D(1d, 1d);

        // Act
        var addedPosition = position.add(positionToAdd);

        // Assert
        assertEquals(1d, addedPosition.getX());
        assertEquals(1d, addedPosition.getY());
    }

    @Test
    void subtractingPointWorksAsExpected() {
        // Arrange
        var position = new Coordinate2D(0d, 0d);
        var positionToSubtract = new Coordinate2D(1d, 1d);

        // Act
        var subtractedPosition = position.subtract(positionToSubtract);

        // Assert
        assertEquals(-1d, subtractedPosition.getX());
        assertEquals(-1d, subtractedPosition.getY());
    }

    @Test
    void subtractingPoint2DWorksAsExpected() {
        // Arrange
        var position = new Coordinate2D(0d, 0d);
        var positionToSubtract = new Point2D(1d, 1d);

        // Act
        var subtractedPosition = position.subtract(positionToSubtract);

        // Assert
        assertEquals(-1d, subtractedPosition.getX());
        assertEquals(-1d, subtractedPosition.getY());
    }

    @Test
    void multiplyPointWorksAsExpected() {
        // Arrange
        var position = new Coordinate2D(1d, 1d);
        var positionToMultiply = new Coordinate2D(2d, 2d);

        // Act
        var multipliedPosition = position.multiply(positionToMultiply);

        // Assert
        assertEquals(2d, multipliedPosition.getX());
        assertEquals(2d, multipliedPosition.getY());
    }

    @Test
    void dividePointWorksAsExpected() {
        // Arrange
        var position = new Coordinate2D(1d, 1d);
        var positionToDivide = new Coordinate2D(2d, 2d);

        // Act
        var dividedPosition = position.divide(positionToDivide);

        // Assert
        assertEquals(0.5d, dividedPosition.getX());
        assertEquals(0.5d, dividedPosition.getY());
    }

    @Test
    void invertPointWorksAsExpected() {
        // Arrange
        var position = new Coordinate2D(1d, 1d);

        // Act
        var invertedPosition = position.invert();

        // Assert
        assertEquals(-1d, invertedPosition.getX());
        assertEquals(-1d, invertedPosition.getY());
    }

    @Test
    void normalizePointWorksAsExpected() {
        // Arrange
        var position = new Coordinate2D(1d, 1d);

        // Act
        var normalizedPosition = position.normalize();

        // Assert
        assertEquals(0.5 * Math.sqrt(2), normalizedPosition.getX(), DELTA);
        assertEquals(0.5 * Math.sqrt(2), normalizedPosition.getY(), DELTA);
    }

    @Test
    void angleToSelfIs0() {
        // Arrange
        var expected = 0d;

        // Act
        var actual = LOCATION.angleTo(LOCATION);

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    void angleToOwnCoordinatesIs0() {
        // Arrange
        var other = new Coordinate2D(LOCATION.getX(), LOCATION.getY());
        var expected = 0d;

        // Act
        var actual = LOCATION.angleTo(new Coordinate2D(other.getX(), other.getY()));

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    void angleToNullCoordinate2DThrowsNullPointerException() {
        // Arrange
        Coordinate2D other = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> LOCATION.angleTo(other));
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForAngleTo")
    void testAngleToOtherCoordinate(final Coordinate2D otherLocation, final double expectedAngle) {
        // Act
        var actual = LOCATION.angleTo(otherLocation);

        // Assert
        assertEquals(expectedAngle, actual);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForMidpoint")
    void testMidPoint(final Coordinate2D otherLocation, final Coordinate2D expectedMidPoint) {
        // Act
        var actual = LOCATION.middlePoint(otherLocation);

        // Assert
        assertEquals(expectedMidPoint, actual);
    }

    private static Stream<Arguments> provideArgumentsForAngleTo() {
        return Stream.of(
                Arguments.of(new Coordinate2D(LOCATION.getX(), LOCATION.getY() - 10), Direction.UP.getValue()),
                Arguments.of(new Coordinate2D(LOCATION.getX(), LOCATION.getY() + 10), Direction.DOWN.getValue()),
                Arguments.of(new Coordinate2D(LOCATION.getX() - 10, LOCATION.getY()), Direction.LEFT.getValue()),
                Arguments.of(new Coordinate2D(LOCATION.getX() + 10, LOCATION.getY()), Direction.RIGHT.getValue()),
                Arguments.of(new Coordinate2D(LOCATION.getX() - 10, LOCATION.getY() - 10), 225d), // left above
                Arguments.of(new Coordinate2D(LOCATION.getX() + 10, LOCATION.getY() - 10), 135d), // right above
                Arguments.of(new Coordinate2D(LOCATION.getX() - 10, LOCATION.getY() + 10), 315d), // left below
                Arguments.of(new Coordinate2D(LOCATION.getX() + 10, LOCATION.getY() + 10), 45d) // right below

        );
    }

    private static Stream<Arguments> provideArgumentsForMidpoint() {
        return Stream.of(
                Arguments.of(new Coordinate2D(LOCATION.getX(), LOCATION.getY() - 10), new Coordinate2D(LOCATION.getX(), LOCATION.getY() - 5)),
                Arguments.of(new Coordinate2D(LOCATION.getX(), LOCATION.getY() + 10), new Coordinate2D(LOCATION.getX(), LOCATION.getY() + 5)),
                Arguments.of(new Coordinate2D(LOCATION.getX() - 10, LOCATION.getY()), new Coordinate2D(LOCATION.getX() - 5, LOCATION.getY())),
                Arguments.of(new Coordinate2D(LOCATION.getX() + 10, LOCATION.getY()), new Coordinate2D(LOCATION.getX() + 5, LOCATION.getY()))
        );
    }
}
