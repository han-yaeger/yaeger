package nl.meron.yaeger.engine.entities.entity;

import javafx.scene.Node;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class ContinuousRotatableTest {

    public static final int ROTATION_ANGLE = 37;

    @Test
    void setRotationAngleIsUsedForRotationIncrement() {
        // Setup
        var rotatable = new ContinuousRotatableImpl();
        var node = mock(Node.class, withSettings().withoutAnnotations());
        rotatable.setNode(node);
        rotatable.setRotationSpeed(ROTATION_ANGLE);
        when(node.getRotate()).thenReturn(0d);

        // Test
        var updatable = rotatable.applyRotation();
        updatable.update(1l);

        // Verify
        verify(node).setRotate(37d);
    }

    private class ContinuousRotatableImpl implements ContinuousRotatable {

        private double rotationAngle;

        @Override
        public double getRotationSpeed() {
            return rotationAngle;
        }

        private Node node;

        @Override
        public Optional<Node> getGameNode() {
            return Optional.of(node);
        }

        public void setNode(Node node) {
            this.node = node;
        }

        @Override
        public void setRotationSpeed(double rotationAngle) {
            this.rotationAngle = rotationAngle;
        }
    }

}
