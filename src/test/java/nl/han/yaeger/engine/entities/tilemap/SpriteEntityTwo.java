package nl.han.yaeger.engine.entities.tilemap;

import nl.han.yaeger.engine.Size;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.entity.sprite.SpriteEntity;

class SpriteEntityTwo extends SpriteEntity {

    public SpriteEntityTwo(Location location, Size size) {
        super(TileFactoryTest.DEFAULT_RESOURCE, location, size);
    }
}
