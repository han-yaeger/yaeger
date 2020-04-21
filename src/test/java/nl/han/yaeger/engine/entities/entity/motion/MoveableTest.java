package nl.han.yaeger.engine.entities.entity.motion;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import nl.han.yaeger.engine.Updatable;
import nl.han.yaeger.engine.entities.entity.AnchorPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

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
        // Arrange

        // Act
        sut.setMotionTo(SPEED, DIRECTION);

        // Assert
        verify(motionApplier).setMotionTo(SPEED, DIRECTION);
    }

    @Test
    void multiplySpeedDelegatesToMotionApplier() {
        // Arrange

        // Act
        sut.multiplySpeedWith(SPEED);

        // Assert
        verify(motionApplier).multiplySpeedWith(SPEED);
    }

    @Test
    void setSpeedDelegatesToMotionApplier() {
        // Arrange

        // Act
        sut.setSpeedTo(SPEED);

        // Assert
        verify(motionApplier).setSpeedTo(SPEED);
    }

    @Test
    void getSpeedDelegatesToMotionApplier() {
        // Arrange
        when(motionApplier.getSpeed()).thenReturn(SPEED);

        // Act
        double speed = sut.getSpeed();

        // Assert
        Assertions.assertEquals(SPEED, speed, DELTA);
    }

    @Test
    void setDirectionDelegatesToMotionApplier() {
        // Arrange

        // Act
        sut.setDirectionTo(DIRECTION);

        // Assert
        verify(motionApplier).setDirectionTo(DIRECTION);
    }

    @Test
    void alterSpeedDelegatesToMotionApplier() {
        // Arrange

        // Act
        sut.alterSpeedBy(SPEED);

        // Assert
        verify(motionApplier).alterSpeedBy(SPEED);
    }

    @Test
    void changeDirectionDelegatesToMotionApplier() {
        // Arrange

        // Act
        sut.changeDirectionBy(DIRECTION);

        // Assert
        verify(motionApplier).changeDirectionBy(DIRECTION);
    }

    @Test
    void getDirectionDelegatesToMotionApplier() {
        // Arrange
        when(motionApplier.getDirection()).thenReturn(DIRECTION);

        // Act
        double direction = sut.getDirection();

        // Assert
        Assertions.assertEquals(DIRECTION, direction, DELTA);
    }

    @Test
    void updateLocationReturnsAnUpdatable() {
        // Arrange

        // Act
        Updatable updatable = sut.updateLocation();

        // Assert
        Assertions.assertNotNull(updatable);
    }

    @Test
    void callingTheUpdatableModifiesPosition() {
        // Arrange
        var UPDATED_LOCATION = new Point2D(37, 42);
        Updatable updatable = sut.updateLocation();
        Node node = mock(Node.class, withSettings().withoutAnnotations());
        Bounds bounds = new BoundingBox(0, 0, 10, 10);
        when(node.getBoundsInLocal()).thenReturn(bounds);
        when(motionApplier.updateLocation(any(Point2D.class))).thenReturn(UPDATED_LOCATION);
        when(motionApplier.getSpeed()).thenReturn(1d);

        ((MoveableImpl) sut).setGameNode(node);
        // Act
        updatable.update(TIMESTAMP);

        // Assert
        verify(motionApplier).updateLocation(any(Point2D.class));
    }

    @Test
    void callingTheUpdatableWithZeroSpeedDoesNotDoAnything() {
        // Arrange
        Updatable updatable = sut.updateLocation();
        Node node = mock(Node.class, withSettings().withoutAnnotations());
        Bounds bounds = new BoundingBox(0, 0, 10, 10);
        when(node.getBoundsInParent()).thenReturn(bounds);
        when(motionApplier.getSpeed()).thenReturn(0d);

        ((MoveableImpl) sut).setGameNode(node);

        // Act
        updatable.update(TIMESTAMP);

        // Assert
        verify(motionApplier, never()).updateLocation(any(Point2D.class));
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
        public Optional<Node> getGameNode() {
            return Optional.of(node);
        }

        public void setGameNode(Node node) {
            this.node = node;
        }

        @Override
        public void setAnchorPoint(AnchorPoint anchorPoint) {
            // Not required here.
        }

        @Override
        public AnchorPoint getAnchorPoint() {
            return null;
        }

        @Override
        public void setOriginX(double x) {
            // Not required here.
        }

        @Override
        public void setOriginY(double y) {
            // Not required here.
        }

        @Override
        public void placeOnScene() {

        }
    }
}

