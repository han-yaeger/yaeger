package nl.meron.yaeger.engine.entities.entity;

import javafx.scene.Node;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RotatableTest {

    public static final int DEGREES = 37;

    @Test
    void setRotateDelegatesToNode() {
        // Setup
        var rotatable = new RotatableImpl();
        var node = mock(Node.class, withSettings().withoutAnnotations());
        rotatable.setNode(node);

        // Test
        rotatable.setRotate(DEGREES);

        // Verify
        verify(node).setRotate(DEGREES);

    }

    private class RotatableImpl implements Rotatable {

        private Node node;

        @Override
        public Node getGameNode() {
            return node;
        }

        public void setNode(Node node) {
            this.node = node;
        }
    }

}
