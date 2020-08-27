package com.github.hanyaeger.api.engine.entities.tilemap;

import com.github.hanyaeger.api.engine.Size;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.sprite.SpriteEntity;

class SpriteEntityInvalidConstructorImpl extends SpriteEntity {

    protected SpriteEntityInvalidConstructorImpl(String resource, Coordinate2D initialLocation, Size size) {
        super(resource, initialLocation, size);
    }
}
