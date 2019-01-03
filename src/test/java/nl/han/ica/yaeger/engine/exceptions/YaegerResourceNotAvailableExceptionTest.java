package nl.han.ica.yaeger.engine.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class YaegerResourceNotAvailableExceptionTest {

    @Test
    void correctMessageIsContructed() {
        // Setup
        var resource = "testresource";
        // Test
        var exception = new YaegerResourceNotAvailableException(resource);

        // Verify
        String message = exception.getMessage();

        Assertions.assertEquals("Resource " + resource + " can not be found. Ensure that it is placed in resource/ folder.", message);
    }
}