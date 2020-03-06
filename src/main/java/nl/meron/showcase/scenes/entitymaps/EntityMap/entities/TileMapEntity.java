package nl.meron.showcase.scenes.entitymaps.EntityMap.entities;

import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.sprite.SpriteEntity;

public class TileMapEntity extends SpriteEntity {

    public TileMapEntity(Size size) {
        super("showcase/entities/concrete-gray-ceramic.jpeg", new Location(0, 0), size);
    }
}
