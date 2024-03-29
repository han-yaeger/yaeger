package com.github.hanyaeger.api.entities.impl;

import com.github.hanyaeger.core.Updater;
import com.github.hanyaeger.core.entities.EntityCollection;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.core.entities.motion.EntityMotionInitBuffer;
import com.github.hanyaeger.core.entities.motion.MotionApplier;
import com.google.inject.Injector;
import javafx.scene.text.Text;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DynamicTextEntityTest {

    private static final String YAEGER = "Yaeger";
    private final static int X_POSITION = 37;
    private final static int Y_POSITION = 37;
    private final static Coordinate2D DEFAULT_LOCATION = new Coordinate2D(X_POSITION, Y_POSITION);
    public static final int ROTATION_SPEED = 37;
    public static final double SPEED = 37d;
    public static final double DIRECTION = 42d;

    private DynamicTextEntityImpl sut;
    private Injector injector;

    @BeforeEach
    void setup() {
        sut = new DynamicTextEntityImpl(DEFAULT_LOCATION);
        var text = mock(Text.class);
        sut.setText(YAEGER);
        sut.setShape(text);
        injector = mock(Injector.class);
    }

    @Test
    void bufferIsSetInConstructor() {
        // Arrange

        // Act
        var buffer = sut.getBuffer();

        // Verify
        assertTrue(buffer.isPresent());
    }

    @Nested
    class WithMotionApplierSet {

        private MotionApplier motionApplier;

        @BeforeEach
        void setup() {
            motionApplier = mock(MotionApplier.class);
        }

        @Test
        void bufferIsEmptiedAfterInitIsCalled() {
            // Arrange
            sut.setMotionApplier(motionApplier);

            // Act
            sut.init(injector);

            // Assert
            assertFalse(sut.getBuffer().isPresent());
        }

        @Test
        void bufferTransfersMotionOnInit() {
            // Arrange
            sut.setMotion(SPEED, DIRECTION);
            sut.setMotionApplier(motionApplier);

            // Act
            sut.init(injector);

            // Assert
            verify(motionApplier).setMotion(SPEED, DIRECTION);
        }

        @Test
        void initSetsMotionToDesiredSpeed() {
            // Arrange
            sut.setSpeed(SPEED);
            sut.setMotionApplier(motionApplier);

            // Act
            sut.init(injector);

            // Assert
            verify(motionApplier).setMotion(SPEED, 0d);
        }

        @Test
        void setMotionApplierIsUsed() {
            // Arrange
            sut.setMotionApplier(motionApplier);

            // Act
            var mA = sut.getMotionApplier();

            // Assert
            assertEquals(motionApplier, mA);
        }
    }

    @Test
    void setUpdaterIsUsed() {
        // Arrange
        var updater = mock(Updater.class);
        sut.setUpdater(updater);

        // Act
        var u = sut.getUpdater();

        // Verify
        assertEquals(updater, u);
    }

    @Test
    void setRotationSpeedIsUsed() {
        // Arrange
        sut.setRotationSpeed(ROTATION_SPEED);

        // Act
        var rS = sut.getRotationSpeed();

        // Verify
        assertEquals(ROTATION_SPEED, rS);
    }

    @Test
    void addToEntityCollectionCallsAddDynamicEntity() {
        // Arrange
        var entityCollection = mock(EntityCollection.class);

        // Act
        sut.addToEntityCollection(entityCollection);

        // Assert
        verify(entityCollection).addDynamicEntity(sut);
    }

    @Test
    void updateGetsDelegated() {
        // Arrange
        var updater = mock(Updater.class);
        sut.setUpdater(updater);

        // Act
        var TIMESTAMP = 0L;
        sut.update(TIMESTAMP);

        // Assert
        verify(updater).update(TIMESTAMP);
    }

    private static class DynamicTextEntityImpl extends DynamicTextEntity {
        public DynamicTextEntityImpl(Coordinate2D location) {
            super(location);
        }
    }
}


