package com.github.hanyaeger.api.engine.entities;

import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import javafx.scene.Node;
import com.github.hanyaeger.api.engine.entities.entity.AnchorPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

class EntitySupplierTest {

    private static final Coordinate2D DEFAULT_LOCATION = new Coordinate2D(0, 0);
    private EntitySupplier sut;

    @BeforeEach
    void setup() {
        sut = new EntitySupplier();
    }

    @Test
    void addEntitiesAddsEntity() {
        // Arrange
        var entity = new TestEntity(DEFAULT_LOCATION);

        // Act
        sut.add(entity);

        // Assert
        Assertions.assertEquals(1, sut.size());
    }

    @Test
    void clearClearsListOfSpawnedEntities() {
        // Arrange
        var entity = new TestEntity(DEFAULT_LOCATION);

        // Act
        sut.add(entity);
        sut.clear();

        // Assert
        Assertions.assertEquals(0, sut.size());
    }

    @Test
    void getOnEmptySetReturnsEmptySet() {
        // Arrange

        // Act
        var entities = sut.get();

        // Assert
        Assertions.assertEquals(0, entities.size());
    }

    @Test
    void twoDifferentSuppliersWithNoContentAreNotEqual() {
        // Arrange
        var otherSut = new EntitySupplier();

        // Act
        var equals = sut.equals(otherSut);

        // Assert
        Assertions.assertFalse(equals);
    }

    @Test
    void supplierIsEqualToSelf() {
        // Arrange

        // Act
        var equals = sut.equals(sut);

        // Assert
        Assertions.assertTrue(equals);
    }

    private class TestEntity extends YaegerEntity {
        /**
         * Instantiate a new {@link YaegerEntity} for the given {@link Coordinate2D} and textDelegate.
         *
         * @param initialPosition the initial {@link Coordinate2D} of this {@link YaegerEntity}
         */
        public TestEntity(Coordinate2D initialPosition) {
            super(initialPosition);
        }

        @Override
        public void remove() {
            // Not required here.
        }

        @Override
        public Optional<? extends Node> getNode() {
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
    }
}
