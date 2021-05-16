package com.github.hanyaeger.core.entities;

import javafx.scene.Node;
import com.github.hanyaeger.core.entities.events.RemoveEntityEvent;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class RemovableTest {

    @Test
    void notifyRemoveCallsFireEventOnNode() {
        // Arrange
        var sut = new RemovableImpl();
        var node = mock(Node.class, withSettings().withoutAnnotations());
        sut.setNode(node);

        // Act
        sut.notifyRemove();

        // Assert
        verify(node).fireEvent(any(RemoveEntityEvent.class));
    }

    private static class RemovableImpl implements Removable {

        private Optional<Node> node;

        @Override
        public void remove() {
            // not required here
        }

        @Override
        public Optional<? extends Node> getNode() {
            return node;
        }

        public void setNode(Node node) {
            this.node = Optional.of(node);
        }
    }

}
