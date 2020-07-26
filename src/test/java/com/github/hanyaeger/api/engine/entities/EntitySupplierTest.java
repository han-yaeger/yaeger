package com.github.hanyaeger.api.engine.entities;

import com.github.hanyaeger.api.engine.entities.entity.Location;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import javafx.scene.Node;
import com.github.hanyaeger.api.engine.entities.entity.AnchorPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

class EntitySupplierTest {

    private static final Location DEFAULT_LOCATION = new Location(0, 0);
    private EntitySupplier entitySupplier;


    @BeforeEach
    void setup() {
        entitySupplier = new EntitySupplier();
    }

    @Test
    void addEntitiesAddsEntity() {
        // Arrange
        YaegerEntity entity = new TestEntity(DEFAULT_LOCATION);

        // Act
        entitySupplier.add(entity);

        // Assert
        Assertions.assertEquals(1, entitySupplier.size());
    }

    @Test
    void clearClearsListOfSpawnedEntities() {
        // Arrange
        YaegerEntity entity = new TestEntity(DEFAULT_LOCATION);

        // Act
        entitySupplier.add(entity);
        entitySupplier.clear();

        // Assert
        Assertions.assertEquals(0, entitySupplier.size());
    }

    @Test
    void getOnEmptySetReturnsEmptySet() {
        // Arrange

        // Act
        Set<YaegerEntity> entities = entitySupplier.get();

        // Assert
        Assertions.assertEquals(0, entities.size());
    }

    @Test
    void twoDifferentSuppliersWithNoContentAreNotEqual() {
        // Arrange
        var sut1 = new EntitySupplier();
        var sut2 = new EntitySupplier();

        // Act
        boolean equals = sut1.equals(sut2);

        // Assert
        Assertions.assertFalse(equals);
    }

    private class TestEntity extends YaegerEntity {
        /**
         * Instantiate a new {@link YaegerEntity} for the given {@link Location} and textDelegate.
         *
         * @param initialPosition the initial {@link Location} of this {@link YaegerEntity}
         */
        public TestEntity(Location initialPosition) {
            super(initialPosition);
        }

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
        public void setReferenceX(double x) {
            // Not required here.
        }

        @Override
        public void setReferenceY(double y) {
            // Not required here.
        }

    }
}
