package nl.meron.yaeger.engine.entities.tilemap;

import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.sprite.SpriteEntity;

class SpriteEntityInvalidConstructorImpl extends SpriteEntity {

    protected SpriteEntityInvalidConstructorImpl(String resource, Location initialLocation, Size size) {
        super(resource, initialLocation, size);
    }
}
