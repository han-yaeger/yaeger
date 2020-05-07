package com.github.hanyaeger.api.engine.entities.tilemap;

import com.github.hanyaeger.api.engine.Size;
import com.github.hanyaeger.api.engine.entities.entity.Location;
import com.github.hanyaeger.api.engine.entities.entity.sprite.SpriteEntity;

class SpriteEntityInvalidConstructorImpl extends SpriteEntity {

    protected SpriteEntityInvalidConstructorImpl(String resource, Location initialLocation, Size size) {
        super(resource, initialLocation, size);
    }
}
