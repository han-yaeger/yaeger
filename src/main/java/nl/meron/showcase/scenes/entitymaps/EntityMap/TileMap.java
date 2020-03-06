package nl.meron.showcase.scenes.entitymaps.EntityMap;

import nl.meron.showcase.scenes.entitymaps.EntityMap.entities.TileMapEntity;
import nl.meron.yaeger.engine.entities.entitymap.EntityMap;

public class TileMap extends EntityMap {

    @Override
    public void setupEntities() {
        addEntity(1, TileMapEntity.class);
    }

    @Override
    public int[][] defineMap() {
        int[][] map = {
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };
        return map;
    }
}
