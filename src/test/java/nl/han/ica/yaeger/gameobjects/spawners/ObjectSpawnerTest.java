package nl.han.ica.yaeger.gameobjects.spawners;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectSpawnerTest {

    private ObjectSpawner objectSpawner;

    private boolean tickHasBeenCalled;

    @BeforeEach
    public void setup() {
        objectSpawner = new TestObjectSpawner(1000);
        tickHasBeenCalled = false;
    }

    @Test
    public void testTickIsNotCalledImmediatlyAfterCreation() {
        assertFalse(tickHasBeenCalled);
    }


    private class TestObjectSpawner extends ObjectSpawner {

        public TestObjectSpawner(long interval) {
            super(interval);
        }

        @Override
        public void tick() {
            tickHasBeenCalled = true;
        }
    }
}
