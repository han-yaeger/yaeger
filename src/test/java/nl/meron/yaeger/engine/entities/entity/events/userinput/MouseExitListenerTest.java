package nl.meron.yaeger.engine.entities.entity.events.userinput;

import javafx.scene.Node;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MouseExitListenerTest {

    @Test
    void attachMouseExitListenerAttachesMouseListener() {
        // Setup
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var mouseListeningEntity = new MouseExitListeningInstancee();
        mouseListeningEntity.setNode(node);

        // Test
        mouseListeningEntity.attachMouseExitListener();

        // Verify
        verify(node).setOnMouseExited(any());
    }

    private class MouseExitListeningInstancee implements MouseExitListener {

        private Node node;

        @Override
        public void onMouseExited() {
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
