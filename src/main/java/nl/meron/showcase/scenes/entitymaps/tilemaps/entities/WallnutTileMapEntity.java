package nl.meron.showcase.scenes.entitymaps.tilemaps.entities;

import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.sprite.SpriteEntity;

public class WallnutTileMapEntity extends SpriteEntity {

    public WallnutTileMapEntity(Location location, Size size) {
        super("showcase/entities/american-walnut.jpeg", location, size);
    }
}
