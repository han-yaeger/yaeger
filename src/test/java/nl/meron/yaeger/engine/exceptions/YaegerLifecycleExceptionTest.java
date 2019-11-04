package nl.meron.yaeger.engine.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class YaegerLifecycleExceptionTest {

    @Test
    void correctMessageIsContructed() {
        // Setup
        var originalMessage = "Message related to the lifecycle exception.";
        // Test
        var sut = new YaegerLifecycleException(originalMessage);

        // Verify
        var message = sut.getMessage();

        Assertions.assertEquals(originalMessage, message);
    }
}
