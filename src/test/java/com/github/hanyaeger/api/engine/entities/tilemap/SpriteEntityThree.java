package com.github.hanyaeger.api.engine.entities.tilemap;

import com.github.hanyaeger.api.engine.Size;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.sprite.SpriteEntity;

class SpriteEntityThree extends SpriteEntity {

    public SpriteEntityThree(Coordinate2D location, Size size) {
        super(TileFactoryTest.DEFAULT_RESOURCE, location, size);
    }
}
