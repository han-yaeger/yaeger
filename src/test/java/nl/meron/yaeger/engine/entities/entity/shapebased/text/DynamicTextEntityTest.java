package nl.meron.yaeger.engine.entities.entity.shapebased.text;

import com.google.inject.Injector;
import javafx.scene.text.Text;
import nl.meron.yaeger.engine.entities.entity.Updater;
import nl.meron.yaeger.engine.entities.entity.motion.DefaultMotionApplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class DynamicTextEntityTest {

    private static final String YAEGER = "Yaeger";

    private DynamicTextEntityImpl sut;
    private Injector injector;
    private Text text;

    @BeforeEach
    void setup() {
        sut = new DynamicTextEntityImpl();
        text = mock(Text.class);
        sut.setText(YAEGER);
        sut.setTextDelegate(text);
        injector = mock(Injector.class);
    }

    @Test
    void configureIsCalledOnInitialization() {
        // Setup

        // Test
        sut.init(injector);

        // Verify
        Assertions.assertTrue(sut.configureCalled);
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

    private class DynamicTextEntityImpl extends DynamicTextEntity {

        boolean configureCalled = false;

        @Override
        public void configure() {
            configureCalled = true;
        }
    }
}


