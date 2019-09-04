package nl.han.ica.yaeger.engine.entities.entity.sprites;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MovementVectorTest {

    @Test
    void defaultMovemenHasZeroSpeed() {
        // Setup
        var movement = new MovementVector();

        // Test
        double speed = movement.getSpeed();

        // Verify
        Assertions.assertEquals(0, speed, 0.00001d);
    }

    @Test
    void defaultMovemenHasZeroDirection() {
        // Setup
        var movement = new MovementVector();

        // Test
        double direction = movement.getDirection();

        // Verify
        Assertions.assertEquals(0, direction, 0.00001d);
    }

    @Test
    void setDirectionChangesDirection() {
        // Setup
        var movement = new MovementVector();

        // Test
        movement.setDirection(MovementVector.Direction.UP);

        // Verify
        Assertions.assertEquals(MovementVector.Direction.UP, movement.getDirection(), 0.00001d);
    }

    @Test
    void setSpeedChangesDirection() {
        // Setup
        var speed = 37d;
        var movement = new MovementVector();

        // Test
        movement.setSpeed(speed);

        // Verify
        Assertions.assertEquals(speed, movement.getSpeed(), 0.00001d);
    }

    @Test
    void nonDefaultConstructorSetsSpeed() {
        // Setup
        var speed = 37d;

        // Test
        var movement = new MovementVector(MovementVector.Direction.DOWN, speed);

        // Verify
        Assertions.assertEquals(speed, movement.getSpeed(), 0.00001d);
    }

    @Test
    void nonDefaultConstructorSetsDirection() {
        // Setup
        var speed = 37d;

        // Test
        var movement = new MovementVector(MovementVector.Direction.RIGHT, speed);

        // Verify
        Assertions.assertEquals(MovementVector.Direction.RIGHT, movement.getDirection(), 0.00001d);
    }

    @Test
    void directionRightGivesZeroAngleInRadians() {
        // Setup
        var direction = MovementVector.Direction.RIGHT;

        // Test
        var movement = new MovementVector(direction, 0d);

        // Verify
        Assertions.assertEquals(0d, movement.getAngleInRadians(), 0.00001d);
    }

    @Test
    void directionDownGivesHalfPiAngleInRadians() {
        // Setup
        var direction = MovementVector.Direction.DOWN;

        // Test
        var movement = new MovementVector(direction, 0d);

        // Verify
        Assertions.assertEquals(Math.PI / 2, movement.getAngleInRadians(), 0.00001d);
    }

    @Test
    void directionLeftGivesHalfPiAngleInRadians() {
        // Setup
        var direction = MovementVector.Direction.LEFT;

        // Test
        var movement = new MovementVector(direction, 0d);

        // Verify
        Assertions.assertEquals(Math.PI, movement.getAngleInRadians(), 0.00001d);
    }

    @Test
    void directionUpGivesHalfPiAngleInRadians() {
        // Setup
        var direction = MovementVector.Direction.UP;

        // Test
        var movement = new MovementVector(direction, 0d);

        // Verify
        Assertions.assertEquals(-1 * (Math.PI / 2), movement.getAngleInRadians(), 0.00001d);
    }

    @Test
    void speedZeroResultsInZeroVector() {
        // Setup
        var direction = MovementVector.Direction.RIGHT;
        var speed = 0d;

        // Test
        var movement = new MovementVector(direction, speed);

        // Verify
        Assertions.assertEquals(0, movement.getVector().getX(), 0.00001d);
        Assertions.assertEquals(0, movement.getVector().getY(), 0.00001d);
    }

    @Test
    void speedOneDirectionRightResultsInCorrectVector() {
        // Setup
        var direction = MovementVector.Direction.RIGHT;
        var speed = 1d;

        // Test
        var movement = new MovementVector(direction, speed);

        // Verify
        Assertions.assertEquals(1, movement.getVector().getX(), 0.00001d);
        Assertions.assertEquals(0, movement.getVector().getY(), 0.00001d);
    }
}
