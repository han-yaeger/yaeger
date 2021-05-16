package com.github.hanyaeger.core.entities.motion;

import com.github.hanyaeger.api.entities.Direction;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class EntityMotionInitBufferTest {

    private static final int SPEED = 4;
    private static final int DIRECTION = 37;
    private static final double FRICTION_CONSTANT = 0.37;
    private static final double GRAVITATIONAL_CONSTANT = 0.42;
    private static final double GRAVITATIONAL_DIRECTION = Direction.DOWN.getValue();

    private EntityMotionInitBuffer sut;
    private MotionApplier motionApplier;
    private Injector injector;

    @BeforeEach
    void setup() {
        sut = new EntityMotionInitBuffer();
        injector = mock(Injector.class);
        motionApplier = mock(MotionApplier.class);
        sut.setMotionApplier(motionApplier);
    }

    @Test
    void settingGravityConstantBeforeInitAndCallingInitSetsGravityConstantOnMotionApplier() {
        // Arrange
        sut.setGravityConstant(GRAVITATIONAL_CONSTANT);

        // Act
        sut.init(injector);

        // Assert
        verify(motionApplier).setGravityConstant(GRAVITATIONAL_CONSTANT);
    }

    @Test
    void settingGravityDirectionBeforeInitAndCallingInitSetsGravityDirectionOnMotionApplier() {
        // Arrange
        sut.setGravityDirection(GRAVITATIONAL_DIRECTION);

        // Act
        sut.init(injector);

        // Assert
        verify(motionApplier).setGravityDirection(GRAVITATIONAL_DIRECTION);
    }

    @Test
    void settingFrictionConstantBeforeInitAndCallingInitSetsFrictionConstantOnMotionApplier() {
        // Arrange
        sut.setFrictionConstant(FRICTION_CONSTANT);

        // Act
        sut.init(injector);

        // Assert
        verify(motionApplier).setFrictionConstant(FRICTION_CONSTANT);
    }

    @Test
    void settingSpeedBeforeInitAndCallingInitSetsSpeedOnMotionApplier() {
        // Arrange
        sut.setSpeed(SPEED);

        // Act
        sut.init(injector);

        // Assert
        verify(motionApplier).setMotion(SPEED, 0d);
    }

    @Test
    void settingDirectionBeforeInitAndCallingInitSetsDirectionOnMotionApplier() {
        // Arrange
        sut.setDirection(DIRECTION);

        // Act
        sut.init(injector);

        // Assert
        verify(motionApplier).setMotion(0d, DIRECTION);
    }

    @Test
    void settingMotionBeforeInitAndCallingInitSetsMotionOnMotionApplier() {
        // Arrange
        sut.setMotion(SPEED, DIRECTION);

        // Act
        sut.init(injector);

        // Assert
        verify(motionApplier).setMotion(SPEED, DIRECTION);
    }
}
