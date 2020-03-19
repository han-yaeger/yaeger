package nl.meron.yaeger.engine.entities.tilemap;

import org.junit.jupiter.api.BeforeEach;

class TileMapTest {

    private TileMap sut;

    @BeforeEach
    void setup() {
        sut = new TileMapImpl();
    }

    private class TileMapImpl extends TileMap {

        @Override
        public void setupEntities() {

        }

        @Override
        public int[][] defineMap() {
            return new int[0][];
        }
    }
}
