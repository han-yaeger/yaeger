package com.github.hanyaeger.core.factories;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.core.ViewOrders;
import com.github.hanyaeger.core.entities.EntityConfiguration;
import com.github.hanyaeger.core.exceptions.FailedToInstantiateEntityException;
import com.github.hanyaeger.core.exceptions.InvalidConstructorException;
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
    void creatingEntityWithInvalidConstructorThrowsInvalidConstructorException() {
        // Arrange

        // Act
        var invalidConstructorException = assertThrows(InvalidConstructorException.class, () -> sut.create(SpriteEntityInvalidConstructorImpl.class, DEFAULT_LOCATION, DEFAULT_SIZE));

        // Assert
        assertTrue(invalidConstructorException.getCause() instanceof NoSuchMethodException);
    }

    @Test
    void creatingEntityWithCrashingConstructorThrowsFailedToInstantiate() {
        // Arrange

        // Act
        var failedToInstantiateEntityException = assertThrows(FailedToInstantiateEntityException.class,
                () -> sut.create(SpriteEntityCrashingConstructorImpl.class, DEFAULT_LOCATION, DEFAULT_SIZE));

        // Assert
        assertTrue(failedToInstantiateEntityException.getCause() instanceof InvocationTargetException);
    }

    @Test
    void creatingConfigurableEntityWithInvalidConfigurationTypeThrowsInvalidConstructorException() {
        // Arrange
        var entityConfiguration = new EntityConfiguration<Integer>(YaegerEntityConfigurableConstructorImpl.class, 1); // Type should be String
        var location = new Coordinate2D(1, 1);
        var size = new Size(1, 1);

        // Act & Assert
        assertThrows(InvalidConstructorException.class,
                () -> sut.create(entityConfiguration, location, size));
    }

    @Test
    void createdEntityIsInstanceOfRequestedClass() {
        // Arrange

        // Act
        var entity = sut.create(YaegerEntityValidConstructorImpl.class, new Coordinate2D(1, 1), new Size(1, 1));

        //  Assert
        assertTrue(entity instanceof YaegerEntity);
    }

    @Test
    void onCreatedSpriteEntityCalledSetPreserveIsCalled() {
        // Arrange

        // Act
        var entity = sut.create(SpriteEntityValidConstructorImpl.class, new Coordinate2D(1, 1), new Size(1, 1));

        // Assert
        assertFalse(((SpriteEntityValidConstructorImpl) entity).isPreserveAspectRatio());
    }

    @Test
    void onCreatedEntityViewOrderIsSetToDefaultBehind() {
        // Arrange

        // Act
        var entity = sut.create(SpriteEntityValidConstructorImpl.class, new Coordinate2D(1, 1), new Size(1, 1));

        // Assert
        assertEquals(ViewOrders.VIEW_ORDER_ENTITY_DEFAULT_BEHIND, entity.getViewOrder());
    }

    @Test
    void entityWithConfigurationObjectShouldCallOverloadedConstructor() {
        // Arrange
        var entityConfiguration = new EntityConfiguration<String>(YaegerEntityConfigurableConstructorImpl.class, "sprite");

        // Act
        var entity = sut.create(entityConfiguration, new Coordinate2D(1, 1), new Size(1, 1));

        // Assert
        assertTrue(((YaegerEntityConfigurableConstructorImpl) entity).configurableConstructorCalled);
    }

    @Test
    void entityWithoutConfigurationObjectShouldNotCallOverloadedConstructor() {
        // Arrange
        var entityConfiguration = new EntityConfiguration<String>(YaegerEntityConfigurableConstructorImpl.class);

        // Act
        var entity = sut.create(entityConfiguration, new Coordinate2D(1, 1), new Size(1, 1));

        // Assert
        assertFalse(((YaegerEntityConfigurableConstructorImpl) entity).configurableConstructorCalled);
    }
}
