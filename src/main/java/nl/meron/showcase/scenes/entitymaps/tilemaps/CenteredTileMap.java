package nl.meron.showcase.scenes.entitymaps.tilemaps;

import nl.meron.showcase.scenes.entitymaps.tilemaps.entities.WallnutTileMapEntity;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.tilemap.TileMap;

public class CenteredTileMap extends TileMap {

    public CenteredTileMap(final Location location, final Size size) {
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
