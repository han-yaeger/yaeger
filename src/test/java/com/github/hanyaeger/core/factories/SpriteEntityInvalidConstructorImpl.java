package com.github.hanyaeger.core.factories;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;

class SpriteEntityInvalidConstructorImpl extends SpriteEntity {

    protected SpriteEntityInvalidConstructorImpl(String resource, Coordinate2D initialLocation, Size size) {
        super(resource, initialLocation, size);
    }
}
