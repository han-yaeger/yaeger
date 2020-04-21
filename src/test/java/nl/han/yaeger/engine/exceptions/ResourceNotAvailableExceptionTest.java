package nl.han.yaeger.engine.exceptions;

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
        assertEquals("Resource " + resource + " can not be found. Ensure that it is placed in resource/ folder.", message);
    }
}
