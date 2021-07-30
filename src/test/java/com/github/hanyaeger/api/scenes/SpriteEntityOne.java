package com.github.hanyaeger.api.scenes;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;

class SpriteEntityOne extends SpriteEntity {

    public SpriteEntityOne(Coordinate2D location, Size size) {
        super(TileMapTest.DEFAULT_RESOURCE, location, size);
    }
}
