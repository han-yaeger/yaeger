package com.github.hanyaeger.api.engine.entities.tilemap;

import com.github.hanyaeger.api.engine.Size;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.sprite.SpriteEntity;

class SpriteEntityCrashingConstructorImpl extends SpriteEntity {

    protected SpriteEntityCrashingConstructorImpl(Coordinate2D initialLocation, Size size) {
        super(TileFactoryTest.DEFAULT_RESOURCE, initialLocation, size);

        throw new RuntimeException("Kaboom");
    }
}
