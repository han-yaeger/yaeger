package nl.han.ica.yaeger.entities.spawners;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntitySpawnerTest {

    private EntitySpawner entitySpawner;

    private boolean tickHasBeenCalled;

    @BeforeEach
    public void setup() {
        entitySpawner = new TestEntitySpawner(1000);
        tickHasBeenCalled = false;
    }

    @Test
    public void testTickIsNotCalledImmediatlyAfterCreation() {
        assertFalse(tickHasBeenCalled);
    }


    private class TestEntitySpawner extends EntitySpawner {

        public TestEntitySpawner(long interval) {
            super(interval);
        }

        @Override
        public void tick() {
            tickHasBeenCalled = true;
        }
    }
}
