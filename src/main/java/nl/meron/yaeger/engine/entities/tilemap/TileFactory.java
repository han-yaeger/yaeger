package nl.meron.yaeger.engine.entities.tilemap;

import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.sprite.SpriteEntity;
import nl.meron.yaeger.engine.exceptions.YaegerEngineException;

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

    public Entity create(final Class<? extends SpriteEntity> entityClass, final Location location, final Size size) {
        SpriteEntity entity;
        try {
            entity = entityClass.getDeclaredConstructor(Location.class, Size.class).newInstance(location, size);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new YaegerEngineException("Unable to instantiate an Entity for the entitymap");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new YaegerEngineException("Unable to instantiate an Entity for the entitymap");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new YaegerEngineException("Unable to instantiate an Entity for the entitymap");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new YaegerEngineException("Unable to instantiate an Entity for the entitymap");
        }

        entity.setPreserveAspectRatio(false);
        return entity;
    }
}
