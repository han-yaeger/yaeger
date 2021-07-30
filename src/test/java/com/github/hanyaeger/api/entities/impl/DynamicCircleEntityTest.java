package com.github.hanyaeger.api.entities.impl;

import com.github.hanyaeger.core.Updater;
import com.github.hanyaeger.core.entities.EntityCollection;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.core.entities.motion.EntityMotionInitBuffer;
import com.github.hanyaeger.core.entities.motion.MotionApplier;
import com.google.inject.Injector;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class DynamicCircleEntityTest {

    private final static int X_POSITION = 37;
    private final static int Y_POSITION = 37;
    private final static Coordinate2D DEFAULT_LOCATION = new Coordinate2D(X_POSITION, Y_POSITION);
    public static final int ROTATION_SPEED = 37;
    public static final double SPEED = 37d;
    public static final double DIRECTION = 42d;

    private DynamicCircleEntityImpl sut;
    private Injector injector;
    private Circle circle;

    @BeforeEach
    void setup() {
        sut = new DynamicCircleEntityImpl(DEFAULT_LOCATION);
        circle = mock(Circle.class);
        sut.setShape(circle);
        injector = mock(Injector.class);
    }

    @Test
    void bufferIsSetInConstructor() {
        // Arrange

        // Act
        Optional<EntityMotionInitBuffer> buffer = sut.getBuffer();

        // Assert
        Assertions.assertTrue(buffer.isPresent());
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
            Assertions.assertFalse(sut.getBuffer().isPresent());
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
            Assertions.assertEquals(motionApplier, mA);
        }
    }

    @Test
    void setUpdaterIsUsed() {
        // Arrange
        var updater = mock(Updater.class);
        sut.setUpdater(updater);

        // Act
        var u = sut.getUpdater();

        // Assert
        Assertions.assertEquals(updater, u);
    }

    @Test
    void setRotationSpeedIsUsed() {
        // Arrange
        sut.setRotationSpeed(ROTATION_SPEED);

        // Act
        var rS = sut.getRotationSpeed();

        // Assert
        Assertions.assertEquals(ROTATION_SPEED, rS);
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
        long TIMESTAMP = 0L;
        sut.update(TIMESTAMP);

        // Assert
        verify(updater).update(TIMESTAMP);
    }

    private class DynamicCircleEntityImpl extends DynamicCircleEntity {

        /**
         * Create a new {@link DynamicRectangleEntity} on the given {@code initialPosition}.
         *
         * @param initialPosition The initial position at which this {@link DynamicRectangleEntity} should be placed
         */
        public DynamicCircleEntityImpl(Coordinate2D initialPosition) {
            super(initialPosition);
        }
    }
}
