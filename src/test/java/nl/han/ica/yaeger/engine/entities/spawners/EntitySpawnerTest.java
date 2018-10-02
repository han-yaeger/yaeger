package nl.han.ica.yaeger.engine.entities.spawners;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntitySpawnerTest {

    private EntitySpawner entitySpawner;

    private boolean tickHasBeenCalled;

    @BeforeEach
     void setup() {
        entitySpawner = new TestEntitySpawner(1000);
        tickHasBeenCalled = false;
    }

    @Test
     void testTickIsNotCalledImmediatlyAfterCreation() {
        assertFalse(tickHasBeenCalled);
    }


    private class TestEntitySpawner extends EntitySpawner {

         TestEntitySpawner(long interval) {
            super(interval);
        }

        @Override
        public void tick() {
            tickHasBeenCalled = true;
        }
    }
}
