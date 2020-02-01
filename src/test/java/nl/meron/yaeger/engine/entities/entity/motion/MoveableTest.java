package nl.meron.yaeger.engine.entities.entity.motion;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import nl.meron.yaeger.engine.entities.entity.Updatable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        sut.setMotionTo(SPEED, DIRECTION);

        // Verify
        verify(motionApplier).setMotionTo(SPEED, DIRECTION);
    }

    @Test
    void multiplySpeedDelegatesToMotionApplier() {
        // Setup

        // Test
        sut.multiplySpeedWith(SPEED);

        // Verify
        verify(motionApplier).multiplySpeedWith(SPEED);
    }

    @Test
    void setSpeedDelegatesToMotionApplier() {
        // Setup

        // Test
        sut.setSpeedTo(SPEED);

        // Verify
        verify(motionApplier).setSpeedTo(SPEED);
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
        sut.setDirectionTo(DIRECTION);

        // Verify
        verify(motionApplier).setDirectionTo(DIRECTION);
    }

    @Test
    void alterSpeedDelegatesToMotionApplier() {
        // Setup

        // Test
        sut.alterSpeedBy(SPEED);

        // Verify
        verify(motionApplier).alterSpeedBy(SPEED);
    }

    @Test
    void changeDirectionDelegatesToMotionApplier() {
        // Setup

        // Test
        sut.changeDirectionBy(DIRECTION);

        // Verify
        verify(motionApplier).changeDirectionBy(DIRECTION);
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
        var UPDATED_LOCATION = new Point2D(37,42);
        Updatable updatable = sut.updateLocation();
        Node node = mock(Node.class, withSettings().withoutAnnotations());
        Bounds bounds = new BoundingBox(0, 0, 10, 10);
        when(node.getBoundsInParent()).thenReturn(bounds);
        when(motionApplier.updateLocation(any(Point2D.class))).thenReturn(UPDATED_LOCATION);

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
        public void placeOnPosition(double x, double y) {
            // Not required here.
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

