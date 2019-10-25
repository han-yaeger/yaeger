package nl.meron.yaeger.engine.entities.entity;

import com.google.inject.Injector;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import nl.meron.yaeger.engine.userinput.MousePressedListener;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MousePressedListenerTest {

    @Test
    void attachMousePressedListenerAttachesMouseListener() {
        // Setup
        var node = mock(Node.class);
        var mouseListeningEntity = new MouseListeningEntity();
        mouseListeningEntity.setNode(node);

        // Test
        mouseListeningEntity.attachMousePressedListener();

        // Verify
        verify(node).setOnMousePressed(any());
    }

    private class MouseListeningEntity implements MousePressedListener, Entity {

        private Node node;

        @Override
        public Point getAnchorPoint() {
            return null;
        }

        @Override
        public double getRightSideX() {
            return 0;
        }

        @Override
        public double getLeftSideX() {
            return 0;
        }

        @Override
        public double getBottomY() {
            return 0;
        }

        @Override
        public double getTopY() {
            return 0;
        }

        @Override
        public void init(Injector injector) {

        }

        @Override
        public void onMousePressed(MouseButton button) {

        }

        @Override
        public void remove() {

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
