package nl.meron.yaeger.engine.entities;

import com.google.inject.Injector;
import javafx.scene.Node;
import nl.meron.yaeger.engine.Timer;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.entities.entity.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

class EntitySupplierTest {

    private EntitySupplier entitySupplier;


    @BeforeEach
    void setup() {
        entitySupplier = new EntitySupplier();
    }

    @Test
    void addEntitiesAddsEntity() {
        // Setup
        Entity entity = new TestEntity();

        // Test
        entitySupplier.add(entity);

        // Verify
        Assertions.assertEquals(1, entitySupplier.size());
    }

    @Test
    void clearClearsListOfSpawnedEntities() {
        // Setup
        Entity entity = new TestEntity();

        // Test
        entitySupplier.add(entity);
        entitySupplier.clear();

        // Verify
        Assertions.assertEquals(0, entitySupplier.size());
    }

    @Test
    void getOnEmptySetReturnsEmptySet() {
        // Setup

        // Test
        Set<Entity> entities = entitySupplier.get();

        // Verify
        Assertions.assertEquals(0, entities.size());
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
        public void setX(double x) {
            // Not required here.
        }

        @Override
        public void setY(double y) {
            // Not required here.
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
