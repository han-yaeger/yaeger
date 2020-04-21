package nl.han.showcase.scenes.entitymaps.tilemaps;

import nl.han.showcase.scenes.entitymaps.tilemaps.entities.WallnutTileMapEntity;
import nl.han.yaeger.engine.Size;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.tilemap.TileMap;

public class BoundedTileMap extends TileMap {

    public BoundedTileMap(final Location location, final Size size) {
        super(location, size);
    }

    @Override
    public void setupEntities() {
        addEntity(1, WallnutTileMapEntity.class);
    }

    @Override
    public int[][] defineMap() {
        int[][] map = {
                {1, 1, 1},
                {1, 0, 1},
                {1, 1, 1}
        };
        return map;
    }
}
