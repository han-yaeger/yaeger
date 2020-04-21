package nl.han.showcase.scenes.entitymaps.tilemaps.entities;

import nl.han.yaeger.engine.Size;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.entity.sprite.SpriteEntity;

public class WallnutTileMapEntity extends SpriteEntity {

    public WallnutTileMapEntity(Location location, Size size) {
        super("showcase/entities/american-walnut.jpeg", location, size);
    }
}
