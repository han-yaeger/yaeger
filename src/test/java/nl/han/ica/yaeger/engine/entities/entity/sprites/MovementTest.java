package nl.han.ica.yaeger.engine.entities.entity.sprites;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovementTest {

    @Test
    void defaultMovemenHasZeroSpeed() {
        // Setup
        var movement = new Movement();

        // Test
        double speed = movement.getSpeed();

        // Verify
        Assertions.assertEquals(0, speed, 0.00001d);
    }

    @Test
    void defaultMovemenHasZeroDirection() {
        // Setup
        var movement = new Movement();

        // Test
        double direction = movement.getDirection();

        // Verify
        Assertions.assertEquals(0, direction, 0.00001d);
    }

    @Test
    void setDirectionChangesDirection() {
        // Setup
        var movement = new Movement();

        // Test
        movement.setDirection(Movement.Direction.UP);

        // Verify
        Assertions.assertEquals(Movement.Direction.UP, movement.getDirection(), 0.00001d);
    }

    @Test
    void setSpeedChangesDirection() {
        // Setup
        var speed = 37d;
        var movement = new Movement();

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
        var movement = new Movement(Movement.Direction.DOWN, speed);

        // Verify
        Assertions.assertEquals(speed, movement.getSpeed(), 0.00001d);
    }

    @Test
    void nonDefaultConstructorSetsDirection() {
        // Setup
        var speed = 37d;

        // Test
        var movement = new Movement(Movement.Direction.RIGHT, speed);

        // Verify
        Assertions.assertEquals(Movement.Direction.RIGHT, movement.getDirection(), 0.00001d);
    }

    @Test
    void directionRightGivesZeroAngleInRadians() {
        // Setup
        var direction = Movement.Direction.RIGHT;

        // Test
        var movement = new Movement(direction, 0d);

        // Verify
        Assertions.assertEquals(0d, movement.getAngleInRadians(), 0.00001d);
    }

    @Test
    void directionDownGivesHalfPiAngleInRadians() {
        // Setup
        var direction = Movement.Direction.DOWN;

        // Test
        var movement = new Movement(direction, 0d);

        // Verify
        Assertions.assertEquals(Math.PI / 2, movement.getAngleInRadians(), 0.00001d);
    }

    @Test
    void directionLeftGivesHalfPiAngleInRadians() {
        // Setup
        var direction = Movement.Direction.LEFT;

        // Test
        var movement = new Movement(direction, 0d);

        // Verify
        Assertions.assertEquals(Math.PI, movement.getAngleInRadians(), 0.00001d);
    }

    @Test
    void directionUpGivesHalfPiAngleInRadians() {
        // Setup
        var direction = Movement.Direction.UP;

        // Test
        var movement = new Movement(direction, 0d);

        // Verify
        Assertions.assertEquals(-1 * (Math.PI / 2), movement.getAngleInRadians(), 0.00001d);
    }

    @Test
    void speedZeroResultsInZeroVector() {
        // Setup
        var direction = Movement.Direction.RIGHT;
        var speed = 0d;

        // Test
        var movement = new Movement(direction, speed);

        // Verify
        Assertions.assertEquals(0, movement.getVector().getX(), 0.00001d);
        Assertions.assertEquals(0, movement.getVector().getY(), 0.00001d);
    }

    @Test
    void speedOneDirectionRightResultsInCorrectVector() {
        // Setup
        var direction = Movement.Direction.RIGHT;
        var speed = 1d;

        // Test
        var movement = new Movement(direction, speed);

        // Verify
        Assertions.assertEquals(1, movement.getVector().getX(), 0.00001d);
        Assertions.assertEquals(0, movement.getVector().getY(), 0.00001d);
    }
}