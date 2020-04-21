package nl.han.yaeger.engine.entities.tilemap;

import nl.han.yaeger.engine.Size;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.entity.sprite.SpriteEntity;

class SpriteEntityThree extends SpriteEntity {

    public SpriteEntityThree(Location location, Size size) {
        super(TileFactoryTest.DEFAULT_RESOURCE, location, size);
    }
}
