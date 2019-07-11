package nl.han.ica.yaeger.engine.entities;

import com.google.inject.Injector;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import nl.han.ica.yaeger.engine.entities.entity.Entity;
import nl.han.ica.yaeger.engine.entities.entity.Position;
import nl.han.ica.yaeger.javafx.animationtimer.AnimationTimerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntitySpawnerTest {

    private EntitySpawner entitySpawner;
    private AnimationTimer animationTimer;
    private AnimationTimerFactory animationTimerFactory;


    private boolean tickHasBeenCalled;

    @BeforeEach
    void setup() {
        entitySpawner = new TestEntitySpawner(1000);
        animationTimer = mock(AnimationTimer.class);
        animationTimerFactory = mock(AnimationTimerFactory.class);

        entitySpawner.setAnimationTimerFactory(animationTimerFactory);

        when(animationTimerFactory.createTimeableAnimationTimer(any(), anyLong())).thenReturn(animationTimer);

        tickHasBeenCalled = false;
    }

    @Test
    void testTickIsNotCalledImmediatlyAfterCreation() {
        // Setup
        entitySpawner.init(null);

        // Test

        // Verify
        assertFalse(tickHasBeenCalled);
    }

    @Test
    void intervalIsDelegatedToSpawner(){
        // Setup
        entitySpawner.init(null);

        // Test

        // Verify
        verify(animationTimerFactory).createTimeableAnimationTimer(any(), eq(1000L));
    }

    @Test
    void spawnAddsEntitiesToSpawnedEntities() {
        // Setup
        entitySpawner.init(null);
        Entity entity = new TestEntity();

        // Test
        entitySpawner.spawn(entity);

        // Verify
        Assertions.assertEquals(1, entitySpawner.size());
    }

    @Test
    void destroyClearsListOfSpawnedEntities() {
        // Setup
        entitySpawner.init(null);
        Entity entity = new TestEntity();

        // Test
        entitySpawner.spawn(entity);
        entitySpawner.destroy();

        // Verify
        Assertions.assertEquals(0, entitySpawner.size());
    }

    @Test
    void nullIsNotEqual() {
        // Setup
        entitySpawner.init(null);

        // Test

        // Verify
        Assertions.assertNotEquals(null, entitySpawner);
    }

    @Test
    void differentIntervalIsNotEqual() {
        // Setup
        entitySpawner.init(null);
        var otherEntitySpawner = new TestEntitySpawner(37);

        // Test

        // Verify
        Assertions.assertNotEquals(entitySpawner, otherEntitySpawner);
    }

    @Test
    void sameObjectIsEqual() {
        // Setup
        entitySpawner.init(null);

        // Test

        // Verify
        Assertions.assertEquals(entitySpawner, entitySpawner);
    }

    @Test
    void sameObjectIsSameHashCode() {
        // Setup
        entitySpawner.init(null);

        // Test

        // Verify
        Assertions.assertEquals(entitySpawner.hashCode(), entitySpawner.hashCode());
    }

    @Test
    void differentIntervalIsDifferentHashCode() {
        // Setup
        var otherEntitySpawner = new TestEntitySpawner(37);

        // Test

        // Verify
        Assertions.assertNotEquals(entitySpawner.hashCode(), otherEntitySpawner.hashCode());
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

    private class TestEntity implements Entity {
        @Override
        public void remove() {
            // Not required here.
        }

        @Override
        public Node getGameNode() {
            return null;
        }

        @Override
        public Position getPosition() {
            return null;
        }

        @Override
        public void init(Injector injector) {
            // Not required here.
        }
    }
}
