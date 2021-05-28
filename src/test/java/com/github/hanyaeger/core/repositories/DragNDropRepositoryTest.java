package com.github.hanyaeger.core.repositories;

import com.github.hanyaeger.api.userinput.MouseDraggedListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DragNDropRepositoryTest {

    private DragNDropRepository sut;

    @BeforeEach
    void setup() {
        sut = new DragNDropRepository();
    }

    @Test
    void getReturnsSetObject() {
        // Arrange
        var expected = mock(MouseDraggedListener.class);
        sut.setDraggedObject(expected);

        // Act
        var actual = sut.getDraggedObject();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void clearClearsObject() {
        // Arrange
        var expected = mock(MouseDraggedListener.class);
        sut.setDraggedObject(expected);

        // Act
        sut.clear();
        var actual = sut.getDraggedObject();

        // Assert
        assertNull(actual);
    }

}
