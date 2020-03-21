package nl.meron.yaeger.engine.entities.tilemap;

import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.sprite.SpriteEntity;

class SpriteEntityCrashingConstructorImpl extends SpriteEntity {

    protected SpriteEntityCrashingConstructorImpl(Location initialLocation, Size size) {
        super(TileFactoryTest.DEFAULT_RESOURCE, initialLocation, size);

        throw new RuntimeException("Kaboom");
    }
}
