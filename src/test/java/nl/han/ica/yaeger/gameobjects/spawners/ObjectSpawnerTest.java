package nl.han.ica.yaeger.gameobjects.spawners;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectSpawnerTest {

    private ObjectSpawer objectSpawer;

    private boolean tickHasBeenCalled;

    @BeforeEach
    public void setup() {
        objectSpawer = new TestObjectSpawner(1000);
        tickHasBeenCalled = false;
    }

    @Test
    public void testTickIsNotCalledImmediatlyAfterCreation() {
        assertFalse(tickHasBeenCalled);
    }


    private class TestObjectSpawner extends ObjectSpawer {

        public TestObjectSpawner(long interval) {
            super(interval);
        }

        @Override
        public void tick() {
            tickHasBeenCalled = true;
        }
    }
}
