package com.github.hanyaeger.api.entities.impl;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.core.Updater;
import com.github.hanyaeger.core.entities.EntityCollection;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.core.entities.motion.EntityMotionInitBuffer;
import com.github.hanyaeger.core.entities.motion.MotionApplier;
import com.google.inject.Injector;
import javafx.scene.shape.Ellipse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DynamicEllipseEntityTest {

    private final long TIMESTAMP = 0L;

    private final static int X_POSITION = 37;
    private final static int Y_POSITION = 37;
    private final static Coordinate2D DEFAULT_LOCATION = new Coordinate2D(X_POSITION, Y_POSITION);
    public static final int ROTATION_SPEED = 37;
    public static final double SPEED = 37d;
    public static final double DIRECTION = 42d;

    private Injector injector;
    private Ellipse ellipse;

    @BeforeEach
    void setup() {
        ellipse = mock(Ellipse.class);
        injector = mock(Injector.class);
    }

    @Nested
    class OneArgumentConstructor {

        private DynamicEllipseEntity sut;

        @BeforeEach
        void setup() {
            sut = new DynamicEllipsEntityImpl(DEFAULT_LOCATION);
            sut.setShape(ellipse);
        }

        @Test
        void bufferIsSetInConstructor() {
            // Arrange

            // Act
            Optional<EntityMotionInitBuffer> buffer = sut.getBuffer();

            // Assert
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

            // Assert
            assertEquals(updater, u);
        }

        @Test
        void setRotationSpeedIsUsed() {
            // Arrange
            sut.setRotationSpeed(ROTATION_SPEED);

            // Act
            var rS = sut.getRotationSpeed();

            // Assert
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
            sut.update(TIMESTAMP);

            // Assert
            verify(updater).update(TIMESTAMP);
        }

        private class DynamicEllipsEntityImpl extends DynamicEllipseEntity {

            public DynamicEllipsEntityImpl(Coordinate2D initialPosition) {
                super(initialPosition);
            }
        }
    }

    @Nested
    class TwoArgumentConstructor {

        public static final double RADIUS_X = 37;
        public static final double RADIUS_Y = 42;

        private final Size SIZE = new Size(RADIUS_X * 2, RADIUS_Y * 2);

        private DynamicEllipseEntity sut;

        @BeforeEach
        void setup() {
            sut = new DynamicEllipsEntityImpl(DEFAULT_LOCATION, SIZE);
            sut.setShape(ellipse);
        }

        @Test
        void bufferIsSetInConstructor() {
            // Arrange

            // Act
            Optional<EntityMotionInitBuffer> buffer = sut.getBuffer();

            // Assert
            assertTrue(buffer.isPresent());
        }

        private class DynamicEllipsEntityImpl extends DynamicEllipseEntity {

            public DynamicEllipsEntityImpl(final Coordinate2D initialPosition, final Size size) {
                super(initialPosition, size);
            }
        }
    }


}
