package com.github.hanyaeger.api.engine.entities.entity.motion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewtonianMotionApplierTest {

    private NewtonianMotionApplier sut;

    @BeforeEach
    void setup() {
        sut = new NewtonianMotionApplier();
    }

    @Test
    void gravitationalPullDefaultToTrue() {
        // Arrange

        // Act
        var actual = sut.isGravitationalPull();

        // Assert
        assertTrue(actual);
    }
}