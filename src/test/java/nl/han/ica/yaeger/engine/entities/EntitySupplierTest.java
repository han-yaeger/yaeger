package nl.han.ica.yaeger.engine.entities;

import com.google.inject.Injector;
import javafx.scene.Node;
import nl.han.ica.yaeger.engine.entities.entity.Entity;
import nl.han.ica.yaeger.engine.entities.entity.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class EntitySupplierTest {

    private EntitySupplier entitySupplier;


    @BeforeEach
    void setup() {
        entitySupplier = new EntitySupplier();
    }

    @Test
    void spawnAddsEntitiesToSpawnedEntities() {
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
