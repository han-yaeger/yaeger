package nl.han.showcase.scenes.entitymaps.tilemaps.entities;

import nl.han.yaeger.engine.Size;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.entity.sprite.SpriteEntity;

public class ConcreteGrayCeramicTileMapEntity extends SpriteEntity {

    public ConcreteGrayCeramicTileMapEntity(Location location, Size size) {
        super("showcase/entities/concrete-gray-ceramic.jpeg", location, size);
    }
}
