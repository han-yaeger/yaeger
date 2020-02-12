package nl.meron.yaeger.engine.entities.entity.shape.rectangle;

import com.google.inject.Injector;
import javafx.scene.shape.Rectangle;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.motion.DefaultMotionApplier;
import nl.meron.yaeger.engine.entities.entity.motion.EntityMotionInitBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DynamicRectangleEntityTest {

    private final static int X_POSITION = 37;
    private final static int Y_POSITION = 37;
    private final static Location DEFAULT_LOCATION = new Location(X_POSITION, Y_POSITION);
    public static final int ROTATION_SPEED = 37;
    public static final double SPEED = 37d;
    public static final double DIRECTION = 42d;

    private DynamicRectangleEntityImpl sut;
    private Injector injector;
    private Rectangle rectangle;

    @BeforeEach
    void setup() {
        sut = new DynamicRectangleEntityImpl(DEFAULT_LOCATION);
        rectangle = mock(Rectangle.class);
        sut.setRectangle(rectangle);
        injector = mock(Injector.class);
    }

    @Test
    void bufferIsSetInConstructor() {
        // Setup

        // Test
        Optional<EntityMotionInitBuffer> buffer = sut.getBuffer();

        // Verify
        Assertions.assertTrue(buffer.isPresent());
    }

    @Test
    void bufferIsEmptiedAfterInitIsCalled() {
        // Setup
        var motionApplier = mock(DefaultMotionApplier.class);
        sut.setMotionApplier(motionApplier);

        // Test
        sut.init(injector);

        // Verify
        Assertions.assertFalse(sut.getBuffer().isPresent());
    }

    @Test
    void bufferTransfersMotionOnInit(){
        // Setup
        var motionApplier = mock(DefaultMotionApplier.class);
        sut.setMotionApplier(motionApplier);
        sut.setMotionTo(SPEED, DIRECTION);

        // Test
        sut.init(injector);

        // Verify
        verify(motionApplier).setMotionTo(SPEED, DIRECTION);
    }

    private class DynamicRectangleEntityImpl extends DynamicRectangleEntity {

        /**
         * Create a new {@link DynamicRectangleEntity} on the given {@code initialPosition}.
         *
         * @param initialPosition The initial position at which this {@link DynamicRectangleEntity} should be placed
         */
        public DynamicRectangleEntityImpl(Location initialPosition) {
            super(initialPosition);
        }
    }
}
