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
            " exactly two parameters: An instance of Coordinate2D and of Size.";
    private static final String MESSAGE_FAILED_TO_INSTANTIATE_ENTITY = "Unable to instantiate an Entity for the TileMap";

    public YaegerEntity create(final Class<? extends YaegerEntity> entityClass, final Coordinate2D location, final Size size) {
        YaegerEntity entity;

        Constructor<? extends YaegerEntity> declaredConstructor = null;

        try {
            declaredConstructor = entityClass.getDeclaredConstructor(Coordinate2D.class, Size.class);
        } catch (NoSuchMethodException e) {
            throw new InvalidConstructorException(MESSAGE_INVALID_CONSTRUCTOR_EXCEPTION, e);
        }

        try {
            entity = declaredConstructor.newInstance(location, size);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new FailedToInstantiateEntityException(MESSAGE_FAILED_TO_INSTANTIATE_ENTITY, e);
        }

        if (entity instanceof SpriteEntity) {
            ((SpriteEntity) entity).setPreserveAspectRatio(false);
        }

        return entity;
    }
}
