package nl.han.ica.yaeger.engine.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class YaegerLifecycleExceptionTest {

    @Test
    void correctMessageIsContructed() {
        // Setup
        var originalMessage = "Message related to the lifecycle exception.";
        // Test
        var exception = new YaegerLifecycleException(originalMessage);

        // Verify
        String message = exception.getMessage();

        Assertions.assertEquals(originalMessage, message);
    }
}