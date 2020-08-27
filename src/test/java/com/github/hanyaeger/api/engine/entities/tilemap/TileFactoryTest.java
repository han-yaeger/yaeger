package com.github.hanyaeger.api.engine.entities.tilemap;

import com.github.hanyaeger.api.engine.Size;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.exceptions.FailedToInstantiateEntityException;
import com.github.hanyaeger.api.engine.exceptions.InvalidConstructorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class TileFactoryTest {

    private TileFactory sut;

    public final static String DEFAULT_RESOURCE = "images/bubble.png";
    private final static Coordinate2D DEFAULT_LOCATION = new Coordinate2D(37, 42);
    private final static Size DEFAULT_SIZE = new Size(39, 45);

    @BeforeEach
    void setup() {
        sut = new TileFactory();
    }

    @Test
    void creatingEntityWithInvalidConstructorThrowsinvalidConstructorException() {
        // Arrange

        // Act
        var invalidConstructorException = assertThrows(InvalidConstructorException.class, () -> sut.create(SpriteEntityInvalidConstructorImpl.class, DEFAULT_LOCATION, DEFAULT_SIZE));

        // Assert
        assertTrue(invalidConstructorException.getCause() instanceof NoSuchMethodException);
    }

    @Test
    void creatingEntityWithCrashingConstructorThrowsInvalidConstructorException() {
        // Arrange

        // Act
        var failedToInstantiateEntityException = assertThrows(FailedToInstantiateEntityException.class, () -> sut.create(SpriteEntityCrashingConstructorImpl.class, DEFAULT_LOCATION, DEFAULT_SIZE));

        // Assert
        assertTrue(failedToInstantiateEntityException.getCause() instanceof InvocationTargetException);
    }

    @Test
    void onCreatedEntityCalledSetPreserveIsCalled() {
        // Arrange

        // Act
        var entity = sut.create(SpriteEntityValidConstructorImpl.class, new Coordinate2D(1, 1), new Size(1, 1));

        // Assert
        assertFalse(((SpriteEntityValidConstructorImpl) entity).isPreserveAspectRatio());
    }
}
