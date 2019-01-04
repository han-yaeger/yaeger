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

}