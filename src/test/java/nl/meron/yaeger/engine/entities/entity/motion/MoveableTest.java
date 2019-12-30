package nl.meron.yaeger.engine.entities.entity.motion;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import nl.meron.yaeger.engine.entities.entity.Updatable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockSettings;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MoveableTest {

    private DefaultMotionApplier motionApplier;
    private Moveable sut;

    private static final double DELTA = 0.00001d;
    private static final long TIMESTAMP = 0;
    private static final double SPEED = 3.7;
    private static final double DIRECTION = 42;

    @BeforeEach
    void setup() {
        motionApplier = Mockito.mock(DefaultMotionApplier.class);
        sut = new MoveableImpl();
        sut.setMotionApplier(motionApplier);
    }

    @Test
    void setMotionDelegatesToMotionApplier() {
        // Setup

        // Test
        sut.setMotion(SPEED, DIRECTION);

        // Verify
        verify(motionApplier).setMotion(SPEED, DIRECTION);
    }

    @Test
    void multiplySpeedDelegatesToMotionApplier() {
        // Setup

        // Test
        sut.multiplySpeed(SPEED);

        // Verify
        verify(motionApplier).multiplySpeed(SPEED);
    }

    @Test
    void setSpeedDelegatesToMotionApplier() {
        // Setup

        // Test
        sut.setSpeed(SPEED);

        // Verify
        verify(motionApplier).setSpeed(SPEED);
    }

    @Test
    void getSpeedDelegatesToMotionApplier() {
        // Setup
        when(motionApplier.getSpeed()).thenReturn(SPEED);

        // Test
        double speed = sut.getSpeed();

        // Verify
        Assertions.assertEquals(SPEED, speed, DELTA);
    }

    @Test
    void setDirectionDelegatesToMotionApplier() {
        // Setup

        // Test
        sut.setDirection(DIRECTION);

        // Verify
        verify(motionApplier).setDirection(DIRECTION);
    }

    @Test
    void alterSpeedDelegatesToMotionApplier() {
        // Setup

        // Test
        sut.alterSpeed(SPEED);

        // Verify
        verify(motionApplier).alterSpeed(SPEED);
    }

    @Test
    void changeDirectionDelegatesToMotionApplier() {
        // Setup

        // Test
        sut.changeDirection(DIRECTION);

        // Verify
        verify(motionApplier).changeDirection(DIRECTION);
    }

    @Test
    void getDirectionDelegatesToMotionApplier() {
        // Setup
        when(motionApplier.getDirection()).thenReturn(DIRECTION);

        // Test
        double direction = sut.getDirection();

        // Verify
        Assertions.assertEquals(DIRECTION, direction, DELTA);
    }

    @Test
    void updateLocationReturnsAnUpdatable() {
        // Setup

        // Test
        Updatable updatable = sut.updateLocation();

        // Verify
        Assertions.assertNotNull(updatable);
    }

    @Test
    void callingTheUpdatableModifiesPosition() {
        // Setup
        Updatable updatable = sut.updateLocation();
        Node node = mock(Node.class, withSettings().withoutAnnotations());
        Bounds bounds = new BoundingBox(0, 0, 10, 10);
        when(node.getBoundsInParent()).thenReturn(bounds);

        ((MoveableImpl) sut).setGameNode(node);
        // Test
        updatable.update(TIMESTAMP);

        // Verify
        verify(motionApplier).updateLocation(any(Point2D.class));
    }

    private class MoveableImpl implements Moveable {

        DefaultMotionApplier motionApplier;
        Node node;

        @Override
        public void setMotionApplier(DefaultMotionApplier motionApplier) {
            this.motionApplier = motionApplier;
        }

        @Override
        public MotionApplier getMotionApplier() {
            return motionApplier;
        }

        @Override
        public void configure() {
            // Not required here
        }

        @Override
        public void placeOnPosition(Point2D position) {
            // Not required here
        }

        @Override
        public Node getGameNode() {
            return node;
        }

        public void setGameNode(Node node) {
            this.node = node;
        }
    }
}

