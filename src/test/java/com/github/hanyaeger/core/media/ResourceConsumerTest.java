package com.github.hanyaeger.core.media;

import com.github.hanyaeger.core.ResourceConsumer;
import com.github.hanyaeger.core.exceptions.ResourceNotAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResourceConsumerTest {

    static final String RESOURCE_EXISTING = "waterworld/images/sprite-with-two-frames.png";
    static final String RESOURCE_NOT_EXISTING = "unavailable.png";

    ResourceConsumer sut;

    @BeforeEach
    void setup() {
        sut = new ResourceConsumer() {
        };
    }

    @Test
    void unavailableResourceReturnsCorrectException() {
        assertThrows(ResourceNotAvailableException.class,
                () ->
                        sut.createPathForResource(RESOURCE_NOT_EXISTING)
        );
    }

    @Test
    void thrownUnavailableResourceExceptionDisplaysCorrectMessage() {
        Throwable exception = assertThrows(ResourceNotAvailableException.class,
                () -> sut.createPathForResource(RESOURCE_NOT_EXISTING));
    }

    @Test
    void emptyResourceParameterReturnsEmptyString() {
        var path = sut.createPathForResource("");

        assertEquals("", path);
    }

    @Test
    void nullResourceParameterReturnsEmptyString() {
        var path = sut.createPathForResource(null);

        assertEquals("", path);
    }

    @Test
    void availableResourceReturnsExistingPath() {
        var path = sut.createPathForResource(RESOURCE_EXISTING);

        assertTrue(path.startsWith("file:/"));
        assertTrue(path.endsWith(RESOURCE_EXISTING));
    }
}
