package nl.meron.yaeger.engine.entities.entity.shape.text;

import com.google.inject.Injector;
import javafx.scene.text.Text;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.Updater;
import nl.meron.yaeger.engine.entities.entity.motion.DefaultMotionApplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DynamicTextEntityTest {

    private static final String YAEGER = "Yaeger";
    private final static int X_POSITION = 37;
    private final static int Y_POSITION = 37;
    private final static Location DEFAULT_LOCATION = new Location(X_POSITION, Y_POSITION);
    public static final int ROTATION_SPEED = 37;
    public static final double SPEED = 37d;

    private DynamicTextEntityImpl sut;
    private Injector injector;
    private Text text;

    @BeforeEach
    void setup() {
        sut = new DynamicTextEntityImpl(DEFAULT_LOCATION);
        text = mock(Text.class);
        sut.setText(YAEGER);
        sut.setTextDelegate(text);
        injector = mock(Injector.class);
    }

    @Test
    void initSetsMotionToDesiredSpeed(){
        // Setup
        sut.setSpeedTo(SPEED);
        var motionApplier = mock(DefaultMotionApplier.class);
        sut.setMotionApplier(motionApplier);

        // Test
        sut.init(injector);

        // Verify
        verify(motionApplier).setMotionTo(SPEED, 0d);
    }

    @Test
    void setMotionApplierIsUsed() {
        // Setup
        var motionApplier = mock(DefaultMotionApplier.class);
        sut.setMotionApplier(motionApplier);

        // Test
        var mA = sut.getMotionApplier();

        // Verify
        Assertions.assertEquals(motionApplier, mA);
    }

    @Test
    void setUpdaterIsUsed() {
        // Setup
        var updater = mock(Updater.class);
        sut.setUpdater(updater);

        // Test
        var u = sut.getUpdater();

        // Verify
        Assertions.assertEquals(updater, u);
    }

    @Test
    void setRotationAngleIsUsed() {
        // Setup
        sut.setRotationSpeed(ROTATION_SPEED);

        // Test
        var rS = sut.getRotationSpeed();

        // Verify
        Assertions.assertEquals(ROTATION_SPEED, rS);
    }

    private class DynamicTextEntityImpl extends DynamicTextEntity {
        public DynamicTextEntityImpl(Location location) {
            super(location);
        }
    }
}


