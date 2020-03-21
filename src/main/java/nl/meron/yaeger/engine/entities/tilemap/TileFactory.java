package nl.meron.yaeger.engine.entities.tilemap;

import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.sprite.SpriteEntity;
import nl.meron.yaeger.engine.exceptions.FailedToInstantiateEntityException;
import nl.meron.yaeger.engine.exceptions.InvalidConstructorException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * The {@link TileFactory} should be used for creating instances of {@link SpriteEntity} that will be part of a
 * {@link TileMap}. For such instances, only a {@link Class}, its {@link Location} and its {@link Size}
 * will be supplied, after which a {@link TileFactory} will be responsible for creating instances.
 * <p>
 * By default a {@link SpriteEntity} created by the {@link TileFactory} will not preserver the aspect ratio of
 * the sprite.
 */
public class TileFactory {

    private static final String MESSAGE_INVALID_CONSTRUCTOR_EXCEPTION = "An Entity used for a Tilemap should have a constructor that accepts" +
            " exactly two parameters: An instance of Location and of Size.";
    private static final String MESSAGE_FAILED_TO_INSTANTIATE_ENTITY = "Unable to instantiate an Entity for the entitymap";

    public Entity create(final Class<? extends SpriteEntity> entityClass, final Location location, final Size size) {
        SpriteEntity entity;

        Constructor<? extends SpriteEntity> declaredConstructor = null;

        try {
            declaredConstructor = entityClass.getDeclaredConstructor(Location.class, Size.class);
        } catch (NoSuchMethodException e) {
            throw new InvalidConstructorException(MESSAGE_INVALID_CONSTRUCTOR_EXCEPTION, e);
        }

        try {
            entity = declaredConstructor.newInstance(location, size);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new FailedToInstantiateEntityException(MESSAGE_FAILED_TO_INSTANTIATE_ENTITY, e);
        }

        entity.setPreserveAspectRatio(false);
        return entity;
    }
}
