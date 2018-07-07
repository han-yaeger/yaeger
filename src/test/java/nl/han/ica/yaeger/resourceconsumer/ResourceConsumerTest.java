package nl.han.ica.yaeger.resourceconsumer;

import nl.han.ica.yaeger.exceptions.YaegerResourceNotAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourceConsumerTest {

    public static final String RESOURCE_EXISTING = "images/sprite-with-two-frames.png";
    public static final String RESOURCE_NOT_EXISTING = "unavailable.png";

    ResourceConsumer resourceConsumer;

    @BeforeEach
    public void setup() {
        resourceConsumer = new ResourceConsumer() {
        };
    }

    @Test
    public void unavailableResourceReturnsCorrectException() {
        assertThrows(YaegerResourceNotAvailableException.class,
                () ->
                        resourceConsumer.createPathForResource(RESOURCE_NOT_EXISTING)
        );
    }

    @Test
    public void thrownUnavailableResourceExceptionDisplaysCorrectMessage() {
        Throwable exception = assertThrows(YaegerResourceNotAvailableException.class,
                () -> resourceConsumer.createPathForResource(RESOURCE_NOT_EXISTING));

        assertEquals("Resource unavailable.png can not be found. Make sure this file is placed in the resource/ folder.", exception.getMessage());
    }

    @Test
    public void emptyResourceParameterReturnsEmptyString() {
        var path = resourceConsumer.createPathForResource("");

        assertEquals("", path);
    }

    @Test
    public void nullResourceParameterReturnsEmptyString() {
        var path = resourceConsumer.createPathForResource(null);

        assertEquals("", path);
    }

    @Test
    public void availableResourceReturnsExistingPath() {
        var path = resourceConsumer.createPathForResource(RESOURCE_EXISTING);

        assertTrue(path.startsWith("file:/"));
        assertTrue(path.endsWith(RESOURCE_EXISTING));
    }
}
