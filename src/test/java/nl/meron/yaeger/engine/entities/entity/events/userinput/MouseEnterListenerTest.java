package nl.meron.yaeger.engine.entities.entity.events.userinput;

import javafx.scene.Node;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class MouseEnterListenerTest {

    @Test
    void attachMouseEnterListenerAttachesMouseListener() {
        // Setup
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var mouseListeningEntity = new MouseEnterListeningInstancee();
        mouseListeningEntity.setNode(node);

        // Test
        mouseListeningEntity.attachMouseEnterListener();

        // Verify
        verify(node).setOnMouseEntered(any());
    }

    private class MouseEnterListeningInstancee implements MouseEnterListener {

        private Node node;

        @Override
        public void onMouseEntered() {
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
