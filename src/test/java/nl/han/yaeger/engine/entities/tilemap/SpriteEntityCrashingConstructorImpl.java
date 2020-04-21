package nl.han.yaeger.engine.entities.tilemap;

import nl.han.yaeger.engine.Size;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.entity.sprite.SpriteEntity;

class SpriteEntityCrashingConstructorImpl extends SpriteEntity {

    protected SpriteEntityCrashingConstructorImpl(Location initialLocation, Size size) {
        super(TileFactoryTest.DEFAULT_RESOURCE, initialLocation, size);

        throw new RuntimeException("Kaboom");
    }
}
