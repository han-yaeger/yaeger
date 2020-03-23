package nl.meron.yaeger.engine.entities.tilemap;

import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.sprite.SpriteEntity;

class SpriteEntityOne extends SpriteEntity {

    public SpriteEntityOne(Location location, Size size) {
        super(TileFactoryTest.DEFAULT_RESOURCE, location, size);
    }
}
