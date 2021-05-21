package com.github.hanyaeger.core.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ResourceNotAvailableExceptionTest {

    @Test
    void correctMessageIsConstructed() {
        // Arrange
        var resource = "testresource";

        // Act
        var sut = new ResourceNotAvailableException(resource);

        // Assert
        var message = sut.getMessage();
        assertEquals(String.format(ResourceNotAvailableException.MESSAGE, resource), message);
    }
}
