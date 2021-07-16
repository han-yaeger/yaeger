package com.github.hanyaeger.core.entities.motion;

import com.github.hanyaeger.core.entities.events.EventTypes;
import javafx.event.EventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InitializationBufferTest {

    private InitializationBuffer sut;

    @BeforeEach
    void setup(){
        sut = new InitializationBuffer();
    }

    @Test
    void rotationIsBuffered() {
        // Arrange
        var expected = 37D;

        // Act
        sut.setRotation(expected);

        // Assert
        assertEquals(expected, sut.getRotation());
    }

    @Test
    void eventHandlerisBuffered(){
        // Arrange
        EventHandler expected = (event) -> System.out.println(event.getSource());

        // Act
        sut.addRemoveHandler(expected);

        // Assert
        assertEquals(1, sut.getRemoveHandlers().size());
        assertEquals(expected, sut.getRemoveHandlers().remove(0));
    }
}
