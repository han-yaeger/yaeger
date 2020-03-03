package nl.meron.yaeger.engine.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class YaegerLifecycleExceptionTest {

    @Test
    void correctMessageIsContructed() {
        // Arrange
        var originalMessage = "Message related to the lifecycle exception.";

        // Act
        var sut = new YaegerLifecycleException(originalMessage);

        // Assert
        var message = sut.getMessage();
        Assertions.assertEquals(originalMessage, message);
    }
}
