package com.github.hanyaeger.core.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class YaegerLifecycleExceptionTest {

    @Test
    void correctMessageIsConstructed() {
        // Arrange
        var originalMessage = "Message related to the lifecycle exception.";

        // Act
        var sut = new YaegerLifecycleException(originalMessage);

        // Assert
        var message = sut.getMessage();
        assertEquals(originalMessage, message);
    }
}
