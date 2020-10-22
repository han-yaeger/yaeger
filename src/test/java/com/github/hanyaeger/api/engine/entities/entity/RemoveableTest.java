package com.github.hanyaeger.api.engine.entities.entity;

import javafx.scene.Node;
import com.github.hanyaeger.api.engine.entities.entity.events.system.RemoveEntityEvent;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class RemoveableTest {

    @Test
    void notifyRemoveCallsFireEventOnNode() {
        // Arrange
        var sut = new RemoveableImpl();
        var node = mock(Node.class, withSettings().withoutAnnotations());
        sut.setNode(node);

        // Act
        sut.notifyRemove();

        // Assert
        verify(node).fireEvent(any(RemoveEntityEvent.class));
    }

    private class RemoveableImpl implements Removeable {

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
