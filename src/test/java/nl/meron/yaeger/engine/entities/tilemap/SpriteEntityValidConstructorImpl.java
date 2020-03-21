package nl.meron.yaeger.engine.entities.tilemap;

import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.sprite.SpriteEntity;

class SpriteEntityValidConstructorImpl extends SpriteEntity {

    private boolean preserveAspectRatio = true;

    public SpriteEntityValidConstructorImpl(Location location, Size size) {
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
