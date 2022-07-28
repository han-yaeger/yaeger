package com.github.hanyaeger.core.entities.motion;

import com.github.hanyaeger.api.entities.Direction;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MotionApplierTest {

    private static final double DELTA = 0.00001d;
    private static final Point2D DEFAULT_START_LOCATION = new Point2D(0, 0);
    private static final Point2D DEFAULT_MOVEMENT_UP = new Point2D(0, 1);
    private static final double FRICTION_CONSTANT = 0.37;
    private static final double GRAVITATIONAL_CONSTANT = 0.42;
    private static final double GRAVITATIONAL_DIRECTION = Direction.DOWN.getValue();

    private static final double SPEED_MULTIPLICATION_FRACTION = 0.5;
    private static final Direction DIRECTION_ENUM = Direction.RIGHT;
    private static final double DIRECTION = 6;
    private static final double DIRECTION_INVERSE_NEGATIVE = -1 * DIRECTION;
    private static final double FULL_ROTATION_MINUS_NEGATIVE_ANGLE = 323;
    private static final double FULL_ROTATION = 360;
    private static final double HALF_ROTATION = 180;

    private MotionApplier sut;

    @BeforeEach
    void setup() {
        sut = new MotionApplier();
    }

    @Test
    void newInstanceHasNoMotionTest() {
        // Arrange

        // Act
        var updatedLocation = sut.updateLocation(DEFAULT_START_LOCATION);

        // Assert
        assertEquals(DEFAULT_START_LOCATION.getX(), updatedLocation.getX(), DELTA);
        assertEquals(DEFAULT_START_LOCATION.getY(), updatedLocation.getY(), DELTA);
    }

    @Test
    void speedWithNoAngleDefaultsToDirectionOfZeroTest() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());
        sut.multiplySpeed(SPEED_MULTIPLICATION_FRACTION);

        // Act
        var updatedLocation = sut.updateLocation(DEFAULT_START_LOCATION);

        // Assert
        assertEquals(DEFAULT_START_LOCATION.getY(), updatedLocation.getX(), DELTA);
        assertEquals(DEFAULT_START_LOCATION.getY() + SPEED_MULTIPLICATION_FRACTION, updatedLocation.getY(), DELTA);
    }

    @Test
    void setFrictionConstantStoresFrictionTest() {
        // Arrange

        // Act
        sut.setFrictionConstant(FRICTION_CONSTANT);

        // Assert
        assertEquals(FRICTION_CONSTANT, sut.getFrictionConstant());
    }

    @Test
    void setGravityConstantStoresGravityTest() {
        // Arrange

        // Act
        sut.setGravityConstant(GRAVITATIONAL_CONSTANT);

        // Assert
        assertEquals(GRAVITATIONAL_CONSTANT, sut.getGravityConstant());
    }

    @Test
    void setGravitationalDirectionStoresDirectionTest() {
        // Arrange

        // Act
        sut.setGravityDirection(GRAVITATIONAL_DIRECTION);

        // Assert
        assertEquals(GRAVITATIONAL_DIRECTION, sut.getGravityDirection());
    }

    @Test
    void isMotionImmutableTest() {
        // Arrange

        // Act
        sut.multiplySpeed(0);

        // Assert
        assertNotSame(DEFAULT_MOVEMENT_UP, sut.get());
    }

    @Test
    void multiplySpeedOfOneKeepsMotionFromConstructorTest() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());

        // Act
        sut.multiplySpeed(1);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(1, sut.get().getY(), DELTA);
    }

    @Test
    void multiplySpeedOfMinusOneInvertsMotionFromConstructorTest() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());

        // Act
        sut.multiplySpeed(-1);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(-1, sut.get().getY(), DELTA);
    }

    @Test
    void multiplySpeedMultipliesSpeedTest() {
        // Arrange
        sut.setMotion(0.5, Direction.DOWN.getValue());

        // Act
        sut.multiplySpeed(2);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(1, sut.get().getY(), DELTA);
    }

    @Test
    void setSpeedToOneSetsSpeedToOneTest() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());

        // Act
        sut.setSpeed(1);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(1, sut.get().getY(), DELTA);
    }

    @Test
    void changeDirection180WhenRightChangesToLeftTest() {
        // Arrange
        sut.setMotion(1, Direction.RIGHT.getValue());

        // Act
        sut.changeDirection(HALF_ROTATION);

        // Assert
        assertEquals(-1, sut.get().getX(), DELTA);
        assertEquals(0, sut.get().getY(), DELTA);
    }

    @Test
    void changeDirection180WhenLeftChangesToRightTest() {
        // Arrange
        sut.setMotion(1, Direction.LEFT.getValue());

        // Act
        sut.changeDirection(HALF_ROTATION);

        // Assert
        assertEquals(1, sut.get().getX(), DELTA);
        assertEquals(0, sut.get().getY(), DELTA);
    }

    @Test
    void alterSpeedIncrementsSpeedTest() {
        // Arrange
        sut.setMotion(1, Direction.UP.getValue());
        var increment = 0.1d;

        // Act
        sut.incrementSpeed(increment);

        // Assert
        assertEquals(DEFAULT_START_LOCATION.magnitude() + DEFAULT_MOVEMENT_UP.magnitude() + increment, sut.get().magnitude(), DELTA);
    }

    @Test
    void alterSpeedWithNegativeValueDecrementsSpeedTest() {
        // Arrange
        sut.setMotion(1, Direction.UP.getValue());
        var increment = -0.1d;

        // Act
        sut.incrementSpeed(increment);

        // Assert
        assertEquals(DEFAULT_START_LOCATION.magnitude() + DEFAULT_MOVEMENT_UP.magnitude() + increment, sut.get().magnitude(), DELTA);
    }

    @Test
    void getSpeedReturnsCorrectValueTest() {
        // Arrange
        var SPEED = 3.7;
        sut.setMotion(SPEED, Direction.UP.getValue());

        // Act
        var speed = sut.getSpeed();

        // Assert
        assertEquals(SPEED, speed, DELTA);
    }

    @Test
    void setDirectionRIGHTForSpeedOneCreatesRightVectorTest() {
        // Arrange
        sut.setSpeed(1);

        // Act
        sut.setDirection(Direction.RIGHT);

        // Assert
        assertEquals(1, sut.get().getX(), DELTA);
        assertEquals(0, sut.get().getY(), DELTA);
    }

    @Test
    void setDirectionToUPForSpeedOneCreatesUpVectorTest() {
        // Arrange
        sut.setSpeed(1);

        // Act
        sut.setDirection(Direction.UP);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(-1, sut.get().getY(), DELTA);
    }

    @Test
    void setDirectionToLEFTForSpeedOneCreatesLEFTVectorTest() {
        // Arrange
        sut.setSpeed(1);

        // Act
        sut.setDirection(Direction.LEFT);

        // Assert
        assertEquals(-1, sut.get().getX(), DELTA);
        assertEquals(0, sut.get().getY(), DELTA);
    }

    @Test
    void setDirectionDOWNForSpeedOneCreatesDownVectorTest() {
        // Arrange
        sut.setSpeed(1);

        // Act
        sut.setDirection(Direction.DOWN);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(1, sut.get().getY(), DELTA);
    }

    @Test
    void setDirectionWithZeroSpeedCreatesZeroVectorTest() {
        // Arrange

        // Act
        sut.setDirection(Direction.LEFT);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(0, sut.get().getY(), DELTA);
    }

    @Test
    void setSpeedAfterDirectionCreatesCorrectVectorTest() {
        // Arrange
        sut.setDirection(Direction.UP);

        // Act
        sut.setSpeed(1);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(-1, sut.get().getY(), DELTA);
    }

    @Test
    void setSpeedBeforeDirectionCreatesCorrectVectorTest() {
        // Arrange
        sut.setSpeed(1);

        // Act
        sut.setDirection(Direction.UP);

        // Assert
        assertEquals(0, sut.get().getX(), DELTA);
        assertEquals(-1, sut.get().getY(), DELTA);
    }

    @Test
    void resetSpeedAfterSpeedHasBeenSetToZeroCreatesCorrectVectorTest() {
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
    void getDirectionForDirectionBelow180TestReturnsCorrectValue() {
        // Arrange
        final double DIRECTION = 42;
        sut.setMotion(1, DIRECTION);

        // Act
        var direction = sut.getDirection();

        // Assert
        assertEquals(DIRECTION, direction, DELTA);
    }

    @Test
    void getDirectionsReturnsNumericValueWhenEnumIsForDirectionWasUsedTest() {
        // Arrange
        sut.setDirection(DIRECTION_ENUM);

        // Act
        var direction = sut.getDirection();

        // Assert
        assertEquals(DIRECTION_ENUM.getValue(), direction, DELTA);
    }

    @Test
    void getDirectionsReturnsNumericValueWhenEnumIsForDirectionWasUsedInMotionTest() {
        // Arrange
        sut.setMotion(1, DIRECTION_ENUM);

        // Act
        var direction = sut.getDirection();

        // Assert
        assertEquals(DIRECTION_ENUM.getValue(), direction, DELTA);
    }

    @Test
    void getDirectionForDirectionAbove180ReturnsCorrectValueTest() {
        // Arrange
        final double DIRECTION = 189;
        sut.setMotion(1, DIRECTION);

        // Act
        var direction = sut.getDirection();

        // Assert
        assertEquals(DIRECTION, direction, DELTA);
    }

    @Test
    void getDirectionAfterSpeedSetTo0ReturnsCorrectDirection(){
        // Arrange
        final double EXPECTED = 189;
        sut.setMotion(1, EXPECTED);

        // Act
        sut.setSpeed(0);

        // Assert
        assertEquals(EXPECTED, sut.getDirection(), DELTA);
    }

    @Test
    void changeDirectionWithZeroDoesNotChangeAngleTest() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());

        // Act
        sut.changeDirection(0);

        // Assert
        assertEquals(0d, DEFAULT_MOVEMENT_UP.angle(sut.get()), DELTA);
    }

    @Test
    void changeDirectionWithZeroDoesNotChangeSpeedTest() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());

        // Act
        sut.changeDirection(0);

        // Assert
        assertEquals(DEFAULT_MOVEMENT_UP.getX(), sut.get().getX(), DELTA);
        assertEquals(DEFAULT_MOVEMENT_UP.getY(), sut.get().getY(), DELTA);
    }

    @Test
    void changeDirectionChangesTheAngleTest() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());

        // Act
        sut.changeDirection(DIRECTION);

        // Assert
        assertEquals(DIRECTION, DEFAULT_MOVEMENT_UP.angle(sut.get()), DELTA);
    }

    @Test
    void changeDirectionWithNegativeChangesTheAngleTest() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());

        // Act
        sut.changeDirection(DIRECTION_INVERSE_NEGATIVE);

        // Assert
        assertEquals(Math.abs(DIRECTION), DEFAULT_MOVEMENT_UP.angle(sut.get()), DELTA);
    }

    @Test
    void changeDirectionWithClockwiseEqualsCounterClockwiseTest() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());

        // Act
        sut.changeDirection(FULL_ROTATION_MINUS_NEGATIVE_ANGLE);

        // Assert
        assertEquals(Math.abs(FULL_ROTATION - FULL_ROTATION_MINUS_NEGATIVE_ANGLE), DEFAULT_MOVEMENT_UP.angle(sut.get()), DELTA);
    }

    @Test
    void changeDirectionZeroDoesNotChangeAngleTest() {
        // Arrange
        sut.setMotion(1, Direction.DOWN.getValue());

        // Act
        sut.changeDirection(0);

        // Assert
        assertEquals(0d, DEFAULT_MOVEMENT_UP.angle(sut.get()), DELTA);
    }

    @Test
    void addToMotionOfOppositeVectorsResultInZeroVectorTest() {
        // Arrange
        sut.setMotion(1, Direction.DOWN);

        // Act
        sut.addToMotion(1, Direction.UP.getValue());

        // Assert
        assertEquals(0, sut.getSpeed(), DELTA);
    }

    @Test
    void addToMotionWithEnumOfOppositeVectorsResultInZeroVectorTest() {
        // Arrange
        sut.setMotion(1, Direction.DOWN);

        // Act
        sut.addToMotion(1, Direction.UP);

        // Assert
        assertEquals(0, sut.getSpeed(), DELTA);
    }

    @Test
    void addToMotionWithSameVectorsResultsInDoubleSpeedTest() {
        // Arrange
        sut.setMotion(1, Direction.UP);

        // Act
        sut.addToMotion(1, Direction.UP);

        // Assert
        assertEquals(2, sut.getSpeed(), DELTA);
    }

    @Nested
    class MaximizeMotionInDirectionTests {

        @Test
        void motionIsSetToMaximizedValueIfCurrentMotionIsZeroTest() {
            // Arrange
            var expectedSpeed = 3.7D;
            var expectedDirection = Direction.RIGHT;

            sut.setMotion(0, 0);

            // Act
            sut.maximizeMotionInDirection(expectedDirection, expectedSpeed);

            // Assert
            assertEquals(expectedDirection.getValue(), sut.getDirection());
            assertEquals(expectedSpeed, sut.getSpeed());
        }

        @Test
        void motionIsSetToMaximizedValueIfDirectionIsSameTest() {
            // Arrange
            var expectedSpeed = 3.7D;
            var expectedDirection = Direction.RIGHT;

            sut.setMotion(1, Direction.RIGHT);

            // Act
            sut.maximizeMotionInDirection(expectedDirection, expectedSpeed);

            // Assert
            assertEquals(expectedDirection.getValue(), sut.getDirection());
            assertEquals(expectedSpeed, sut.getSpeed());
        }

        @Test
        void motionIsSetToMaximizedValueIfDirectionIsInverseTest() {
            // Arrange
            var expectedSpeed = 3.7D;
            var expectedDirection = Direction.RIGHT;

            sut.setMotion(1, Direction.LEFT);

            // Act
            sut.maximizeMotionInDirection(expectedDirection, expectedSpeed);

            // Assert
            assertEquals(expectedDirection.getValue(), sut.getDirection());
            assertEquals(expectedSpeed, sut.getSpeed());
        }

        @Test
        void maximizedMotionOnPythagorianDiagonalDownRightTest() {
            // Arrange
            var currentSpeed = 3;
            var maximizedSpeed = 4;
            var expectedSpeed = 5;
            var expectedDirection = 36.8698976D;
            var inDirection = Direction.DOWN;

            sut.setMotion(currentSpeed, Direction.RIGHT);

            // Act
            sut.maximizeMotionInDirection(inDirection, maximizedSpeed);

            // Assert
            assertEquals(expectedDirection, sut.getDirection(), DELTA);
            assertEquals(expectedSpeed, sut.getSpeed(), DELTA);
        }

        @Test
        void maximizedMotionOnPythagorianDiagonalLeftUpTest() {
            // Arrange
            var currentSpeed = 3;
            var maximizedSpeed = 4;
            var expectedSpeed = 5;
            var expectedDirection = 216.8698976D;
            var inDirection = Direction.UP;

            sut.setMotion(currentSpeed, Direction.LEFT);

            // Act
            sut.maximizeMotionInDirection(inDirection, maximizedSpeed);

            // Assert
            System.out.println(sut.getDirection());
            assertEquals(expectedDirection, sut.getDirection(), DELTA);
            assertEquals(expectedSpeed, sut.getSpeed(), DELTA);
        }

        @Test
        void maximizedMotionFromOppositeDiagonalTest() {
            // Arrange
            var currentDirection = 225D;
            var currentSpeed = Math.sqrt(2);
            var maximizedSpeed = 1;
            var expectedSpeed = Math.sqrt(2);
            var expectedDirection = 135;
            var inDirection = Direction.RIGHT;

            sut.setMotion(currentSpeed, currentDirection);

            // Act
            sut.maximizeMotionInDirection(inDirection, maximizedSpeed);

            // Assert
            assertEquals(expectedDirection, sut.getDirection(), DELTA);
            assertEquals(expectedSpeed, sut.getSpeed(), DELTA);
        }
    }

    @Nested
    class NullifySpeedInDirectionTests {

        @Test
        void nullifySpeedInDirectionWhenSpeedIsAlreadyZeroTest() {
            // Arrange
            var currentSpeed = 0D;
            var currentDirection = 0D;
            sut.setMotion(currentSpeed, currentDirection);

            // Act
            sut.nullifySpeedInDirection(0D);

            // Assert
            assertEquals(currentSpeed, sut.getSpeed(), DELTA);
            assertEquals(currentDirection, sut.getDirection(), DELTA);
        }

        @Test
        void nullifySpeedInOppositeDirectionDoesNothingTest() {
            // Arrange
            var currentSpeed = 3.7;
            var currentDirection = 37;
            sut.setMotion(currentSpeed, currentDirection);

            // Act
            sut.nullifySpeedInDirection(180 + currentDirection);

            // Assert
            assertEquals(currentSpeed, sut.getSpeed(), DELTA);
            assertEquals(currentDirection, sut.getDirection(), DELTA);
        }

        @Test
        void nullifySpeedInDirectionWithAngleGreaterThat90DoesNothingTest() {
            // Arrange
            var currentSpeed = 3.7;
            var currentDirection = 37;
            sut.setMotion(currentSpeed, currentDirection);

            // Act

            sut.nullifySpeedInDirection(100 + currentDirection);

            // Assert
            assertEquals(currentSpeed, sut.getSpeed(), DELTA);
            assertEquals(currentDirection, sut.getDirection(), DELTA);
        }

        @Test
        void nullifySpeedInDirectionWithAngle45Test() {
            // Arrange
            var currentSpeed = Math.sqrt(2);
            var currentDirection = 45;
            sut.setMotion(currentSpeed, currentDirection);

            // Act

            sut.nullifySpeedInDirection(Direction.RIGHT);

            // Assert
            assertEquals(1, sut.getSpeed(), DELTA);
            assertEquals(Direction.DOWN.getValue(), sut.getDirection(), DELTA);
        }

        @Test
        void nullifySpeedInDirectionWithAngleMinus45Test() {
            // Arrange
            var currentSpeed = Math.sqrt(2);
            var currentDirection = 315;
            sut.setMotion(currentSpeed, currentDirection);

            // Act

            sut.nullifySpeedInDirection(Direction.LEFT);

            // Assert
            assertEquals(1, sut.getSpeed(), DELTA);
            assertEquals(Direction.DOWN.getValue(), sut.getDirection(), DELTA);
        }

        @Test
        void nullifySpeedInSameDirectionSetsSpeedTo0Test() {
            // Arrange
            var currentSpeed = 3.7D;
            var currentDirection = 37;
            sut.setMotion(currentSpeed, currentDirection);

            // Act
            sut.nullifySpeedInDirection(currentDirection);

            // Assert
            assertEquals(0, sut.getSpeed());
        }

        @Test
        void nullifyMotionInExactSameDirectionSetsSpeedToZeroTest() {
            // Arrange
            var currentSpeed = 3.7;
            var currentDirection = 37;

            // Act
            sut.setMotion(currentSpeed, currentDirection);
            sut.nullifySpeedInDirection(currentDirection);

            // Assert
            assertEquals(0, sut.getSpeed(), DELTA);
        }

        @Test
        void nullifySpeedInZeroDirectionKeepsZeroDirectionTest() {
            // Arrange
            var updatedDirection = 0.0;

            // Act
            sut.nullifySpeedInDirection(updatedDirection);

            // Assert
            assertEquals(0, Double.compare(updatedDirection, sut.getDirection()));
        }

        @Test
        void nullifySpeedInZeroDirectionDoesNotResultInNaNTest() {
            // Arrange
            var updatedDirection = 0D;

            // Act
            sut.nullifySpeedInDirection(updatedDirection);

            // Assert
            assertFalse(Double.isNaN(sut.getDirection()));
        }
    }

    @Nested
    class GetSpeedInDirectionTests {
        @Test
        void getSpeedInOppositeDirectionGives0Test() {
            // Arrange
            var currentSpeed = 3.7;
            var currentDirection = 37;

            // Act
            sut.setMotion(currentSpeed, currentDirection);
            var speedInDirection = sut.getSpeedInDirection(180 + currentDirection);

            // Assert
            assertEquals(0, speedInDirection, DELTA);
        }

        @Test
        void getSpeedInDirectionWithAngleGreaterThat90Gives0Test() {
            // Arrange
            var currentSpeed = 3.7;
            var currentDirection = 37;
            sut.setMotion(currentSpeed, currentDirection);

            // Act

            var speedInDirection = sut.getSpeedInDirection(100 + currentDirection);

            // Assert
            assertEquals(0, speedInDirection, DELTA);
        }

        @Test
        void getSpeedInRightwardDirectionFor45DegreesGives1Test() {
            // Arrange
            var currentSpeed = Math.sqrt(2);
            var currentDirection = 45D;
            sut.setMotion(currentSpeed, currentDirection);

            // Act
            var speedInDirection = sut.getSpeedInDirection(Direction.RIGHT);

            // Assert
            assertEquals(1, speedInDirection, DELTA);
        }

        @Test
        void getSpeedInDownwardDirectionFor45DegreesGives1Test() {
            // Arrange
            var currentSpeed = Math.sqrt(2);
            var currentDirection = 45D;
            sut.setMotion(currentSpeed, currentDirection);

            // Act
            var speedInDirection = sut.getSpeedInDirection(Direction.DOWN);

            // Assert
            assertEquals(1, speedInDirection, DELTA);
        }
    }

    @Nested
    class InvertSpeedInDirectionTests {

        @Test
        void invertingSpeedInOppositeDirectionDoesNothingTest() {
            // Arrange
            var currentSpeed = 3.7;
            var currentDirection = 37;

            // Act
            sut.setMotion(currentSpeed, currentDirection);
            sut.invertSpeedInDirection(180 + currentDirection);

            // Assert
            assertEquals(currentSpeed, sut.getSpeed(), DELTA);
            assertEquals(currentDirection, sut.getDirection(), DELTA);
        }

        @Test
        void invertSpeedInDirectionWithAngleGreaterThat90DoesNothingTest() {
            // Arrange
            var currentSpeed = 3.7;
            var currentDirection = 37;

            // Act
            sut.setMotion(currentSpeed, currentDirection);
            sut.invertSpeedInDirection(100 + currentDirection);

            // Assert
            assertEquals(currentSpeed, sut.getSpeed(), DELTA);
            assertEquals(currentDirection, sut.getDirection(), DELTA);
        }

        @Test
        void invertSpeedInSameDirectionKeepsSameSpeedButInvertsDirectionTest() {
            // Arrange
            var currentSpeed = 3.7D;
            var currentDirection = 37;
            sut.setMotion(currentSpeed, currentDirection);

            // Act
            sut.invertSpeedInDirection(currentDirection);

            // Assert
            assertEquals(currentSpeed, sut.getSpeed(), DELTA);
            assertEquals(currentDirection + 180, sut.getDirection(), DELTA);
        }

        @Test
        void invertSpeedDirectionRightOn45DegreesGives315DegreesTest() {
            // Arrange
            var currentSpeed = 2;
            var currentDirection = 45;
            sut.setMotion(currentSpeed, currentDirection);

            // Act
            sut.invertSpeedInDirection(Direction.RIGHT);

            // Assert
            assertEquals(currentSpeed, sut.getSpeed(), DELTA);
            assertEquals(315, sut.getDirection(), DELTA);
        }

        @Test
        void invertSpeedDirectionDownOn45DegreesGives135DegreesTest() {
            // Arrange
            var currentSpeed = 2;
            var currentDirection = 45;
            sut.setMotion(currentSpeed, currentDirection);

            // Act
            sut.invertSpeedInDirection(Direction.DOWN);

            // Assert
            assertEquals(currentSpeed, sut.getSpeed(), DELTA);
            assertEquals(135, sut.getDirection(), DELTA);
        }
    }
}
