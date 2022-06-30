package com.github.hanyaeger.core.factories;

import com.github.hanyaeger.api.entities.impl.SpriteEntity;
import com.github.hanyaeger.api.scenes.TileMap;
import com.github.hanyaeger.core.ViewOrders;
import com.github.hanyaeger.core.entities.EntityConfiguration;
import com.github.hanyaeger.core.exceptions.InvalidConstructorException;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.core.exceptions.FailedToInstantiateEntityException;

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
            " either two or three parameters, where the first parameter is an instance of Coordinate2D, the second an instance of Size. If needed, " +
            "a third parameter can be used to configure the created Entity.";
    private static final String MESSAGE_INVALID_CONFIGURABLE_ENTITY = "Configurable entity %s should also accept an instance of %s as third parameter.";
    private static final String MESSAGE_FAILED_TO_INSTANTIATE_ENTITY = "Unable to instantiate an Entity for the TileMap";

    /**
     * Create an instance of {@link YaegerEntity} with an {@link EntityConfiguration} of the given type {@link C} with the given parameters.
     *
     * @param entityConfiguration the {@link EntityConfiguration} to be used as a parameter for the constructor of the created Entity
     * @param location            the location as a {@link Coordinate2D} at which the new Entity should be created
     * @param size                the size as a {@link Size} of the Entity that will be created
     * @param <C>                 the type of the {@link EntityConfiguration} to be passed to the constructor of the created Entity
     * @return an instance of {@link C} on the given location, with the given size and with the given {@link EntityConfiguration}
     */
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

        entity.setViewOrder(ViewOrders.VIEW_ORDER_ENTITY_DEFAULT_BEHIND);

        return entity;
    }

    /**
     * Create an instance of {@link YaegerEntity} with the given parameters.
     *
     * @param entityClass the class
     * @param location    the location as a {@link Coordinate2D} at which the new Entity should be created
     * @param size        the size as a {@link Size} of the Entity that will be created
     * @return an instance of entityClass on the given location, with the given size
     */
    public YaegerEntity create(final Class<? extends YaegerEntity> entityClass, final Coordinate2D location, final Size size) {
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
