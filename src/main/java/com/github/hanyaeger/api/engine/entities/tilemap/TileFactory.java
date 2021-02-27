package com.github.hanyaeger.api.engine.entities.tilemap;

import com.github.hanyaeger.api.engine.entities.entity.sprite.SpriteEntity;
import com.github.hanyaeger.api.engine.exceptions.InvalidConstructorException;
import com.github.hanyaeger.api.engine.Size;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.exceptions.FailedToInstantiateEntityException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * The {@link TileFactory} should be used for creating instances of {@link YaegerEntity} that will be part of a
 * {@link TileMap}. For such instances, only a {@link Class}, its {@link Coordinate2D} and its {@link Size}
 * will be supplied, after which a {@link TileFactory} will be responsible for creating instances.
 * <p>
 * A {@link YaegerEntity} created by the {@link TileFactory} will most likely not be square shaped. In case of
 * a {@link SpriteEntity}, its aspect ratio will not be preserved.
 */
public class TileFactory {

    private static final String MESSAGE_INVALID_CONSTRUCTOR_EXCEPTION = "An Entity used for a TileMap should have a constructor that accepts" +
            " exactly two parameters: An instance of Coordinate2D and of Size.";
    private static final String MESSAGE_INVALID_CONFIGURABLE_ENTITY = "Configurable entity %s should also accept an instance of %s as third parameter.";
    private static final String MESSAGE_FAILED_TO_INSTANTIATE_ENTITY = "Unable to instantiate an Entity for the TileMap";

    public <C> YaegerEntity create(final EntityConfiguration<C> entityConfiguration, final Coordinate2D location, final Size size) {
        YaegerEntity entity;

        var declaredConstructor = getDeclaredConstructor(entityConfiguration);
        var configuration = entityConfiguration.getConfiguration();

        try {
            if (configuration != null) {
                entity = declaredConstructor.newInstance(location, size, configuration);
            } else {
                entity = declaredConstructor.newInstance(location, size);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new FailedToInstantiateEntityException(MESSAGE_FAILED_TO_INSTANTIATE_ENTITY, e);
        }

        if (entity instanceof SpriteEntity) {
            ((SpriteEntity) entity).setPreserveAspectRatio(false);
        }

        return entity;
    }

    YaegerEntity create(final Class<? extends YaegerEntity> entityClass, final Coordinate2D location, final Size size) {
        return create(new EntityConfiguration<>(entityClass), location, size);
    }

    private <C> Constructor<? extends YaegerEntity> getDeclaredConstructor(EntityConfiguration<C> entityConfiguration) {
        var entityClass = entityConfiguration.getEntityClass();
        var configuration = entityConfiguration.getConfiguration();

        Constructor<? extends YaegerEntity> declaredConstructor;
        try {
            if (configuration != null) {
                declaredConstructor = entityClass.getDeclaredConstructor(Coordinate2D.class, Size.class, configuration.getClass());
            } else {
                declaredConstructor = entityClass.getDeclaredConstructor(Coordinate2D.class, Size.class);
            }
        } catch (NoSuchMethodException e) {
            throw new InvalidConstructorException(getInvalidConstructorMessage(entityConfiguration), e);
        }

        return declaredConstructor;
    }

    private <C> String getInvalidConstructorMessage(EntityConfiguration<C> ec) {
        var message = MESSAGE_INVALID_CONSTRUCTOR_EXCEPTION;
        if (ec.getConfiguration() != null) {
            message += String.format("\n" + MESSAGE_INVALID_CONFIGURABLE_ENTITY,
                    ec.getClass().getSimpleName(), ec.getConfiguration().getClass().getSimpleName());
        }
        return message;
    }
}
