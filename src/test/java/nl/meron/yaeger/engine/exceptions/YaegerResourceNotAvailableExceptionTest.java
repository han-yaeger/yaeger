package nl.meron.yaeger.engine.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class YaegerResourceNotAvailableExceptionTest {

    @Test
    void correctMessageIsConstructed() {
        // Setup
        var resource = "testresource";

        // Test
        var sut = new YaegerResourceNotAvailableException(resource);

        // Verify
        var message = sut.getMessage();

        assertEquals("Resource " + resource + " can not be found. Ensure that it is placed in resource/ folder.", message);
    }
}
