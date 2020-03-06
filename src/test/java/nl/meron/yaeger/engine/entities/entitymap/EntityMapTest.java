package nl.meron.yaeger.engine.entities.entitymap;

import org.junit.jupiter.api.BeforeEach;

class EntityMapTest {

    private EntityMap sut;

    @BeforeEach
    void setup() {
        sut = new EntityMapImpl();
    }

    private class EntityMapImpl extends EntityMap {

        @Override
        public void setupEntities() {

        }

        @Override
        public int[][] defineMap() {
            return new int[0][];
        }
    }
}
