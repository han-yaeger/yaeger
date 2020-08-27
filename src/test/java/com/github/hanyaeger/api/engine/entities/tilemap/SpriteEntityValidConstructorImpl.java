package com.github.hanyaeger.api.engine.entities.tilemap;

import com.github.hanyaeger.api.engine.Size;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.sprite.SpriteEntity;

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
