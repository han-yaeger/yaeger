package com.github.hanyaeger.api.engine.entities.tilemap;

import com.github.hanyaeger.api.engine.Size;
import com.github.hanyaeger.api.engine.entities.entity.Location;
import com.github.hanyaeger.api.engine.entities.entity.sprite.SpriteEntity;

class SpriteEntityTwo extends SpriteEntity {

    public SpriteEntityTwo(Location location, Size size) {
        super(TileFactoryTest.DEFAULT_RESOURCE, location, size);
    }
}
