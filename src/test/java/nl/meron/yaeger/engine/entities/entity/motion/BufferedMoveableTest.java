package nl.meron.yaeger.engine.entities.entity.motion;

import javafx.scene.Node;
import nl.meron.yaeger.engine.entities.entity.AnchorPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class BufferedMoveableTest {

    public static final int SPEED = 37;
    public static final int DIRECTION = 42;
    private BufferedMoveableImpl sut;
    private EntityMotionInitBuffer buffer;
    private DefaultMotionApplier motionApplier;

    @BeforeEach
    void setup() {
        this.sut = new BufferedMoveableImpl();
        this.motionApplier = mock(DefaultMotionApplier.class);
        this.buffer = mock(EntityMotionInitBuffer.class);
        sut.setBuffer(Optional.of(buffer));
    }

    @Test
    void ifMotionApplierIsNotSetBufferIsUsedForSpeed() {
        // Setup

        // Test
        sut.setSpeedTo(SPEED);

        // Verify
        verify(buffer).setSpeedTo(SPEED);
    }

    @Test
    void ifMotionApplierIsNotSetBufferIsUsedForDirection() {
        // Setup

        // Test
        sut.setDirectionTo(DIRECTION);

        // Verify
        verify(buffer).setDirectionTo(DIRECTION);
    }

    @Test
    void ifMotionApplierIsNotSetBufferIsUsedForMotion() {
        // Setup

        // Test
        sut.setMotionTo(SPEED, DIRECTION);

        // Verify
        verify(buffer).setMotionTo(SPEED, DIRECTION);
    }

    @Test
    void ifMotionApplierIsSetMotionApplierIsUsedForSpeed() {
        // Setup
        sut.setBuffer(Optional.empty());
        sut.setMotionApplier(motionApplier);

        // Test
        sut.setSpeedTo(SPEED);

        // Verify
        verify(motionApplier).setSpeedTo(SPEED);
    }

    @Test
    void ifMotionApplierIsSetMotionApplierIsUsedForDirection() {
        // Setup
        sut.setBuffer(Optional.empty());
        sut.setMotionApplier(motionApplier);

        // Test
        sut.setDirectionTo(DIRECTION);

        // Verify
        verify(motionApplier).setDirectionTo(DIRECTION);
    }

    @Test
    void ifMotionApplierIsSetMotionApplierIsUsedForMotion() {
        // Setup
        sut.setBuffer(Optional.empty());
        sut.setMotionApplier(motionApplier);

        // Test
        sut.setMotionTo(SPEED, DIRECTION);

        // Verify
        verify(motionApplier).setMotionTo(SPEED, DIRECTION);
    }

    private class BufferedMoveableImpl implements BufferedMoveable {

        private Optional<EntityMotionInitBuffer> buffer;
        private MotionApplier motionApplier;

        @Override
        public Optional<EntityMotionInitBuffer> getBuffer() {
            return buffer;
        }

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
            return null;
        }

        public void setBuffer(Optional<EntityMotionInitBuffer> buffer) {
            this.buffer = buffer;
        }

        @Override
        public AnchorPoint getAnchorPoint() {
            return null;
        }

        @Override
        public void setX(double x) {
            // Not required here
        }

        @Override
        public void setY(double y) {
            // Not required here
        }

        @Override
        public void placeOnScene() {

        }
    }
}
