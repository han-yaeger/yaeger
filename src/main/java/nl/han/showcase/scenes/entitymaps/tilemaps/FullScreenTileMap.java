package nl.han.showcase.scenes.entitymaps.tilemaps;

import nl.han.showcase.scenes.entitymaps.tilemaps.entities.ConcreteGrayCeramicTileMapEntity;
import nl.han.yaeger.engine.entities.tilemap.TileMap;

public class FullScreenTileMap extends TileMap {

    @Override
    public void setupEntities() {
        addEntity(1, ConcreteGrayCeramicTileMapEntity.class);
    }

    @Override
    public int[][] defineMap() {
        int[][] map = {
                {0, 1, 1, 1, 0, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 0, 1, 1, 1, 0}
        };
        return map;
    }
}
