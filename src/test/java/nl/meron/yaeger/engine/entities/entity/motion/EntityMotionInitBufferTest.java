package nl.meron.yaeger.engine.entities.entity.motion;

import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class EntityMotionInitBufferTest {

    public static final int SPEED = 4;
    public static final int DIRECTION = 37;
    private EntityMotionInitBuffer sut;
    private DefaultMotionApplier motionApplier;
    private Injector injector;

    @BeforeEach
    void setup() {
        sut = new EntityMotionInitBuffer();
        injector = mock(Injector.class);
        motionApplier = mock(DefaultMotionApplier.class);
        sut.setMotionApplier(motionApplier);
    }

    @Test
    void settingSpeedBeforeInitAndCallingInitSetsSpeedOnMotionApplier() {
        // Setup
        sut.setSpeedTo(SPEED);

        // Test
        sut.init(injector);

        // Verify
        verify(motionApplier).setMotionTo(SPEED, 0d);
    }

    @Test
    void settingDirectionBeforeInitAndCallingInitSetsDirectionOnMotionApplier() {
        // Setup
        sut.setDirectionTo(DIRECTION);

        // Test
        sut.init(injector);

        // Verify
        verify(motionApplier).setMotionTo(0d, DIRECTION);
    }

    @Test
    void settingMotionBeforeInitAndCallingInitSetsMotionOnMotionApplier() {
        // Setup
        sut.setMotionTo(SPEED, DIRECTION);

        // Test
        sut.init(injector);

        // Verify
        verify(motionApplier).setMotionTo(SPEED, DIRECTION);
    }
}
