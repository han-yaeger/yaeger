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
 * A {@link YaegerEntity} created by the {@link TileFactory} will most likely n not be square shaped. In case of
 * a {@link SpriteEntity}, its aspect ratio will not be preserver.
 */
public class TileFactory {

    private static final String MESSAGE_INVALID_CONSTRUCTOR_EXCEPTION = "An Entity used for a Tilemap should have a constructor that accepts" +
            " two parameters: An instance of Coordinate2D and of Size. Configurable entities should have a third parameter for the" +
            " configuration object.";
    private static final String MESSAGE_FAILED_TO_INSTANTIATE_ENTITY = "Unable to instantiate an Entity for the TileMap";

    public <C extends Object> YaegerEntity create(final EntityConfiguration<C> entityConfiguration, final Coordinate2D location, final Size size) {
        YaegerEntity entity;

        Constructor<? extends YaegerEntity> declaredConstructor = getDeclaredConstructor(entityConfiguration);
        C configuration = entityConfiguration.getConfiguration();
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

    private <C extends Object> Constructor<? extends YaegerEntity> getDeclaredConstructor(EntityConfiguration<C> entityConfiguration) {
        Class<? extends YaegerEntity> entityClass = entityConfiguration.getEntityClass();
        C configuration = entityConfiguration.getConfiguration();

        Constructor<? extends YaegerEntity> declaredConstructor = null;
        try {
            if (configuration != null) {
                declaredConstructor = entityClass.getDeclaredConstructor(Coordinate2D.class, Size.class, configuration.getClass());
            } else {
                declaredConstructor = entityClass.getDeclaredConstructor(Coordinate2D.class, Size.class);
            }
        } catch (NoSuchMethodException e) {
            throw new InvalidConstructorException(MESSAGE_INVALID_CONSTRUCTOR_EXCEPTION, e);
        }

        return declaredConstructor;
    }
}
