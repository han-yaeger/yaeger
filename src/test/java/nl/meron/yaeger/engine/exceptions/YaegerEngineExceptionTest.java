package nl.meron.yaeger.engine.exceptions;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class YaegerEngineExceptionTest {

    @Test
    void testMessageStaysAvailable() {
        // Setup
        var message = "testmessage";

        // Test
        var sut = new YaegerEngineException(message);

        // Verify
        assertEquals(message, sut.getMessage());
    }

    @Test
    void testCausingExceptionStaysAvailable() {
        // Setup
        var exception = Mockito.mock(Exception.class);

        // Test
        var sut = new YaegerEngineException(exception);

        // Verify
        assertEquals(exception, sut.getCause());
    }
}
