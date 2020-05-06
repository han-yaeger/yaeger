package nl.meron.yaeger.engine.entities.entity.events.userinput;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.Test;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class MouseMovedListenerTest {

    @Test
    void attachMouseMovedListenerAttachesMouseListener() {
        // Arrange
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var mouseMovedEntity = new MouseMovedListenerTest.MouseMotionListeningInstance();
        mouseMovedEntity.setNode(node);

        // Act
        // mouseMovedEntity.attachMouseMovedListener

        // Assert
        verify(node).setOnMousePressed(any());
    }

    private static class MouseMotionListeningInstance implements MouseMotionListener {

        private Node node;

        //@Override
        public Optional<Node> getGameNode() {
            return Optional.of(node);
        }

        void setNode(Node node) {
            this.node = node;
        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }
}
