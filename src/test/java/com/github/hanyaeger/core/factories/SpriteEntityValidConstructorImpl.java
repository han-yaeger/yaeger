package com.github.hanyaeger.core.factories;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;

class SpriteEntityValidConstructorImpl extends SpriteEntity {

    private boolean preserveAspectRatio = true;

    public SpriteEntityValidConstructorImpl(Coordinate2D location, Size size) {
        super(TileFactoryTest.DEFAULT_RESOURCE, location, size);
    }

    @Override
    public void setPreserveAspectRatio(boolean preserveAspectRatio) {
        super.setPreserveAspectRatio(preserveAspectRatio);

        this.preserveAspectRatio = preserveAspectRatio;
    }

    public boolean isPreserveAspectRatio() {
        return preserveAspectRatio;
    }
}
