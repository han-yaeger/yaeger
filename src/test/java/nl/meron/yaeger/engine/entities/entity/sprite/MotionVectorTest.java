package nl.meron.yaeger.engine.entities.entity.sprite;

import nl.meron.yaeger.engine.entities.entity.motion.MotionVector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MotionVectorTest {

    @Test
    void defaultMovemenHasZeroSpeed() {
        // Setup
        var movement = new MotionVector();

        // Test
        double speed = movement.getSpeed();

        // Verify
        Assertions.assertEquals(0, speed, 0.00001d);
    }

    @Test
    void defaultMovemenHasZeroDirection() {
        // Setup
        var movement = new MotionVector();

        // Test
        double direction = movement.getDirection();

        // Verify
        Assertions.assertEquals(0, direction, 0.00001d);
    }

    @Test
    void setDirectionChangesDirection() {
        // Setup
        var movement = new MotionVector();

        // Test
        movement.setDirection(MotionVector.Direction.UP);

        // Verify
        Assertions.assertEquals(MotionVector.Direction.UP, movement.getDirection(), 0.00001d);
    }

    @Test
    void setSpeedChangesDirection() {
        // Setup
        var speed = 37d;
        var movement = new MotionVector();

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
        var movement = new MotionVector(MotionVector.Direction.DOWN, speed);

        // Verify
        Assertions.assertEquals(speed, movement.getSpeed(), 0.00001d);
    }

    @Test
    void nonDefaultConstructorSetsDirection() {
        // Setup
        var speed = 37d;

        // Test
        var movement = new MotionVector(MotionVector.Direction.RIGHT, speed);

        // Verify
        Assertions.assertEquals(MotionVector.Direction.RIGHT, movement.getDirection(), 0.00001d);
    }

    @Test
    void directionRightGivesZeroAngleInRadians() {
        // Setup
        var direction = MotionVector.Direction.RIGHT;

        // Test
        var movement = new MotionVector(direction, 0d);

        // Verify
        Assertions.assertEquals(0d, movement.getAngleInRadians(), 0.00001d);
    }

    @Test
    void directionDownGivesHalfPiAngleInRadians() {
        // Setup
        var direction = MotionVector.Direction.DOWN;

        // Test
        var movement = new MotionVector(direction, 0d);

        // Verify
        Assertions.assertEquals(Math.PI / 2, movement.getAngleInRadians(), 0.00001d);
    }

    @Test
    void directionLeftGivesHalfPiAngleInRadians() {
        // Setup
        var direction = MotionVector.Direction.LEFT;

        // Test
        var movement = new MotionVector(direction, 0d);

        // Verify
        Assertions.assertEquals(Math.PI, movement.getAngleInRadians(), 0.00001d);
    }

    @Test
    void directionUpGivesHalfPiAngleInRadians() {
        // Setup
        var direction = MotionVector.Direction.UP;

        // Test
        var movement = new MotionVector(direction, 0d);

        // Verify
        Assertions.assertEquals(-1 * (Math.PI / 2), movement.getAngleInRadians(), 0.00001d);
    }

    @Test
    void speedZeroResultsInZeroVector() {
        // Setup
        var direction = MotionVector.Direction.RIGHT;
        var speed = 0d;

        // Test
        var movement = new MotionVector(direction, speed);

        // Verify
        Assertions.assertEquals(0, movement.getVector().getX(), 0.00001d);
        Assertions.assertEquals(0, movement.getVector().getY(), 0.00001d);
    }

    @Test
    void speedOneDirectionRightResultsInCorrectVector() {
        // Setup
        var direction = MotionVector.Direction.RIGHT;
        var speed = 1d;

        // Test
        var movement = new MotionVector(direction, speed);

        // Verify
        Assertions.assertEquals(1, movement.getVector().getX(), 0.00001d);
        Assertions.assertEquals(0, movement.getVector().getY(), 0.00001d);
    }
}
