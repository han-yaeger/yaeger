package nl.han.yaeger.engine.entities.tilemap;

import nl.han.yaeger.engine.Size;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.entity.sprite.SpriteEntity;

class SpriteEntityInvalidConstructorImpl extends SpriteEntity {

    protected SpriteEntityInvalidConstructorImpl(String resource, Location initialLocation, Size size) {
        super(resource, initialLocation, size);
    }
}
