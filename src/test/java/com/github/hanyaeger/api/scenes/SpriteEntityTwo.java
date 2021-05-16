package com.github.hanyaeger.api.scenes;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.sprite.SpriteEntity;

class SpriteEntityTwo extends SpriteEntity {

    public SpriteEntityTwo(Coordinate2D location, Size size) {
        super(TileMapTest.DEFAULT_RESOURCE, location, size);
    }
}
