package com.github.hanyaeger.api.engine.entities.entity.shape.text;

import com.github.hanyaeger.api.engine.Updater;
import com.github.hanyaeger.api.engine.entities.entity.Location;
import com.github.hanyaeger.api.engine.entities.entity.motion.DefaultMotionApplier;
import com.github.hanyaeger.api.engine.entities.entity.motion.EntityMotionInitBuffer;
import com.google.inject.Injector;
import javafx.scene.text.Text;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DynamicTextEntityTest {

    private static final String YAEGER = "Yaeger";
    private final static int X_POSITION = 37;
    private final static int Y_POSITION = 37;
    private final static Location DEFAULT_LOCATION = new Location(X_POSITION, Y_POSITION);
    public static final int ROTATION_SPEED = 37;
    public static final double SPEED = 37d;
    public static final double DIRECTION = 42d;

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
    void bufferIsSetInConstructor() {
        // Arrange

        // Act
        Optional<EntityMotionInitBuffer> buffer = sut.getBuffer();

        // Verify
        Assertions.assertTrue(buffer.isPresent());
    }

    @Test
    void bufferTransfersMotionOnInit() {
        // Arrange
        var motionApplier = mock(DefaultMotionApplier.class);
        sut.setMotionApplier(motionApplier);
        sut.setMotionTo(SPEED, DIRECTION);

        // Act
        sut.init(injector);

        // Verify
        verify(motionApplier).setMotionTo(SPEED, DIRECTION);
    }

    @Test
    void bufferIsEmptiedAfterInitIsCalled() {
        // Arrange
        var motionApplier = mock(DefaultMotionApplier.class);
        sut.setMotionApplier(motionApplier);

        // Act
        sut.init(injector);

        // Verify
        Assertions.assertFalse(sut.getBuffer().isPresent());
    }

    @Test
    void initSetsMotionToDesiredSpeed() {
        // Arrange
        sut.setSpeedTo(SPEED);
        var motionApplier = mock(DefaultMotionApplier.class);
        sut.setMotionApplier(motionApplier);

        // Act
        sut.init(injector);

        // Verify
        verify(motionApplier).setMotionTo(SPEED, 0d);
    }

    @Test
    void setMotionApplierIsUsed() {
        // Arrange
        var motionApplier = mock(DefaultMotionApplier.class);
        sut.setMotionApplier(motionApplier);

        // Act
        var mA = sut.getMotionApplier();

        // Verify
        Assertions.assertEquals(motionApplier, mA);
    }

    @Test
    void setUpdaterIsUsed() {
        // Arrange
        var updater = mock(Updater.class);
        sut.setUpdater(updater);

        // Act
        var u = sut.getUpdater();

        // Verify
        Assertions.assertEquals(updater, u);
    }

    @Test
    void setRotationSpeedIsUsed() {
        // Arrange
        sut.setRotationSpeed(ROTATION_SPEED);

        // Act
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


