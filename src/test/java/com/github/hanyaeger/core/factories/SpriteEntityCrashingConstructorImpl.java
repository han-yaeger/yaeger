package com.github.hanyaeger.core.factories;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;

class SpriteEntityCrashingConstructorImpl extends SpriteEntity {

    protected SpriteEntityCrashingConstructorImpl(Coordinate2D initialLocation, Size size) {
        super(TileFactoryTest.DEFAULT_RESOURCE, initialLocation, size);

        throw new RuntimeException("Kaboom");
    }
}
