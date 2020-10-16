package com.github.hanyaeger.api.engine.entities.entity.motion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RotationBufferTest {

    @Test
    void rotationIsBuffered() {
        // Arrange
        var expected = 37D;
        var sut = new RotationBuffer();

        // Act
        sut.setRotation(expected);

        // Assert
        assertEquals(expected, sut.getRotation());
    }
}