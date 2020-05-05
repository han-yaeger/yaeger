package nl.meron.yaeger.engine.entities.entity.events.userinput;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class MousePressedListenerTest {

    @Test
    void attachMousePressedListenerAttachesMouseListener() {
        // Arrange
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var mouseListeningEntity = new MousePressedListeningInstance();
        mouseListeningEntity.setNode(node);

        // Act
        mouseListeningEntity.attachMousePressedListener();

        // Assert
        verify(node).setOnMousePressed(any());
    }

    private class MousePressedListeningInstance implements MousePressedListener {

        private Node node;

        @Override
        public void onMousePressed(MouseEvent event, Double xCoordinates, Double yCoordinates) {
        }

        @Override
        public Optional<Node> getGameNode() {
            return Optional.of(node);
        }

        void setNode(Node node) {
            this.node = node;
        }
    }
}
