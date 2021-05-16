package com.github.hanyaeger.core.scenes;

import com.github.hanyaeger.api.scenes.TileMap;

import java.util.List;

/**
 * Implementing the {@link TileMapListProvider} interface guarantees that a {@link List} of
 * instances of {@link TileMap} is available.
 */
public interface TileMapListProvider {

    /**
     * Return the {@link List} of {@link TileMap} instances.
     *
     * @return the {@link List} of {@link TileMap} instances.
     */
    List<TileMap> getTileMaps();
}
