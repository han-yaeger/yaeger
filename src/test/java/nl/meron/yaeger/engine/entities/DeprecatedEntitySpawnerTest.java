package nl.meron.yaeger.engine.entities;

import com.google.inject.Injector;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import nl.meron.yaeger.engine.Timer;
import nl.meron.yaeger.engine.entities.entity.AnchorPoint;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.javafx.animationtimer.AnimationTimerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeprecatedEntitySpawnerTest {

    private DeprecatedEntitySpawner deprecatedEntitySpawner;
    private AnimationTimer animationTimer;
    private AnimationTimerFactory animationTimerFactory;

    private boolean tickHasBeenCalled;

    @BeforeEach
    void setup() {
        deprecatedEntitySpawner = new TestDeprecatedEntitySpawner(1000);
        animationTimer = mock(AnimationTimer.class);
        animationTimerFactory = mock(AnimationTimerFactory.class);

        deprecatedEntitySpawner.setAnimationTimerFactory(animationTimerFactory);

        when(animationTimerFactory.createTimeableAnimationTimer(any(), anyLong())).thenReturn(animationTimer);

        tickHasBeenCalled = false;
    }

    @Test
    void testTickIsNotCalledImmediatlyAfterCreation() {
        // Arrange
        deprecatedEntitySpawner.init(null);

        // Act

        // Assert
        assertFalse(tickHasBeenCalled);
    }

    @Test
    void intervalIsDelegatedToSpawner() {
        // Arrange
        deprecatedEntitySpawner.init(null);

        // Act

        // Assert
        verify(animationTimerFactory).createTimeableAnimationTimer(any(), eq(1000L));
    }

    @Test
    void spawnAddsEntitiesToSpawnedEntities() {
        // Arrange
        deprecatedEntitySpawner.init(null);
        Entity entity = new TestEntity();

        // Act
        deprecatedEntitySpawner.spawn(entity);

        // Assert
        assertEquals(1, deprecatedEntitySpawner.size());
    }

    @Test
    void destroyClearsListOfSpawnedEntities() {
        // Arrange
        deprecatedEntitySpawner.init(null);
        Entity entity = new TestEntity();

        // Act
        deprecatedEntitySpawner.spawn(entity);
        deprecatedEntitySpawner.destroy();

        // Assert
        assertEquals(0, deprecatedEntitySpawner.size());
    }

    @Test
    void nullIsNotEqual() {
        // Arrange
        deprecatedEntitySpawner.init(null);

        // Act

        // Assert
        assertNotEquals(null, deprecatedEntitySpawner);
    }

    @Test
    void differentIntervalIsNotEqual() {
        // Arrange
        deprecatedEntitySpawner.init(null);
        var otherEntitySpawner = new TestDeprecatedEntitySpawner(37);

        // Act

        // Assert
        assertNotEquals(deprecatedEntitySpawner, otherEntitySpawner);
    }

    @Test
    void sameObjectIsEqual() {
        // Arrange
        deprecatedEntitySpawner.init(null);

        // Act

        // Assert
        assertEquals(deprecatedEntitySpawner, deprecatedEntitySpawner);
    }

    @Test
    void sameObjectIsSameHashCode() {
        // Arrange
        deprecatedEntitySpawner.init(null);

        // Act

        // Assert
        assertEquals(deprecatedEntitySpawner.hashCode(), deprecatedEntitySpawner.hashCode());
    }

    @Test
    void differentIntervalIsDifferentHashCode() {
        // Arrange
        var otherEntitySpawner = new TestDeprecatedEntitySpawner(37);

        // Act

        // Assert
        assertNotEquals(deprecatedEntitySpawner.hashCode(), otherEntitySpawner.hashCode());
    }

    private class TestDeprecatedEntitySpawner extends DeprecatedEntitySpawner {

        TestDeprecatedEntitySpawner(long interval) {
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
        public Optional<Node> getGameNode() {
            return null;
        }

        @Override
        public void setAnchorPoint(AnchorPoint anchorPoint) {
            // Not required here.
        }

        @Override
        public AnchorPoint getAnchorPoint() {
            return null;
        }

        @Override
        public void setOriginX(double x) {
            // Not required here.
        }

        @Override
        public void setOriginY(double y) {
            // Not required here.
        }

        @Override
        public void placeOnScene() {

        }

        @Override
        public void init(Injector injector) {
            // Not required here.
        }

        @Override
        public List<Timer> getTimers() {
            return null;
            // Not required here.
        }
    }
}
