package nl.han.yaeger.engine.media;

import nl.han.yaeger.engine.exceptions.ResourceNotAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourceConsumerTest {

    static final String RESOURCE_EXISTING = "waterworld/images/sprite-with-two-frames.png";
    static final String RESOURCE_NOT_EXISTING = "unavailable.png";

    ResourceConsumer resourceConsumer;

    @BeforeEach
    void setup() {
        resourceConsumer = new ResourceConsumer() {
        };
    }

    @Test
    void unavailableResourceReturnsCorrectException() {
        assertThrows(ResourceNotAvailableException.class,
                () ->
                        resourceConsumer.createPathForResource(RESOURCE_NOT_EXISTING)
        );
    }

    @Test
    void thrownUnavailableResourceExceptionDisplaysCorrectMessage() {
        Throwable exception = assertThrows(ResourceNotAvailableException.class,
                () -> resourceConsumer.createPathForResource(RESOURCE_NOT_EXISTING));
    }

    @Test
    void emptyResourceParameterReturnsEmptyString() {
        var path = resourceConsumer.createPathForResource("");

        assertEquals("", path);
    }

    @Test
    void nullResourceParameterReturnsEmptyString() {
        var path = resourceConsumer.createPathForResource(null);

        assertEquals("", path);
    }

    @Test
    void availableResourceReturnsExistingPath() {
        var path = resourceConsumer.createPathForResource(RESOURCE_EXISTING);

        assertTrue(path.startsWith("file:/"));
        assertTrue(path.endsWith(RESOURCE_EXISTING));
    }
}
