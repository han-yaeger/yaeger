package nl.meron.yaeger.engine.entities.entity.events.userinput;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MousePressedListenerTest {

    @Test
    void attachMousePressedListenerAttachesMouseListener() {
        // Setup
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var mouseListeningEntity = new MousePressedListeningInstancee();
        mouseListeningEntity.setNode(node);

        // Test
        mouseListeningEntity.attachMousePressedListener();

        // Verify
        verify(node).setOnMousePressed(any());
    }

    private class MousePressedListeningInstancee implements MousePressedListener {

        private Node node;

        @Override
        public void onMousePressed(MouseButton button) {
        }

        @Override
        public Node getGameNode() {
            return node;
        }

        void setNode(Node node) {
            this.node = node;
        }
    }
}
