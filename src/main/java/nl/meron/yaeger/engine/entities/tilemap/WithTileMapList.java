package nl.meron.yaeger.engine.entities.tilemap;

import java.util.List;

/**
 * Implementing the {@link WithTileMapList} interface guarantees that a {@link List} of
 * instances of {@link TileMap} is available.
 */
public interface WithTileMapList {

    /**
     * Return the {@link List} of {@link TileMap} instances.
     *
     * @return the {@link List} of {@link TileMap} instances
     */
    List<TileMap> getTileMaps();
}
