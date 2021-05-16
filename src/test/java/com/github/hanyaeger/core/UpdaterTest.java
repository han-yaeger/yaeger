package com.github.hanyaeger.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

class UpdaterTest {

    private Updater sut;
    private Updatable updatable;
    private static final long TIMESTAMP = 0;

    @BeforeEach
    void setup() {
        sut = new Updater();
        updatable = mock(Updatable.class);
    }

    @Test
    void asFirstUpdatableIsCalledFirstWhenAddedAsSecond() {
        // Arrange
        var firstUpdatable = mock(Updatable.class);
        sut.addUpdatable(updatable);
        sut.addUpdatable(firstUpdatable, true);

        // Act
        sut.update(TIMESTAMP);

        // Assert
        InOrder inOrder = inOrder(firstUpdatable, updatable);
        inOrder.verify(firstUpdatable).update(TIMESTAMP);
        inOrder.verify(updatable).update(TIMESTAMP);
    }

    @Test
    void asFirstUpdatableIsCalledFirstWhenAddedAsFirst() {
        // Arrange
        var firstUpdatable = mock(Updatable.class);
        sut.addUpdatable(firstUpdatable, true);
        sut.addUpdatable(updatable);

        // Act
        sut.update(TIMESTAMP);

        // Assert
        InOrder inOrder = inOrder(firstUpdatable, updatable);
        inOrder.verify(firstUpdatable).update(TIMESTAMP);
        inOrder.verify(updatable).update(TIMESTAMP);
    }

    @Test
    void clearEmptiesUpdatables() {
        // Arrange
        sut.addUpdatable(updatable);

        // Act
        sut.clear();
        sut.update(TIMESTAMP);

        // Assert
        verify(updatable, never()).update(TIMESTAMP);

    }

    @Test
    void testUpdaterDelegatesUpdateToAddedUpdatable() {
        // Arrange
        sut.addUpdatable(updatable);

        // Act
        sut.update(TIMESTAMP);

        // Assert
        verify(updatable).update(TIMESTAMP);
    }
}
