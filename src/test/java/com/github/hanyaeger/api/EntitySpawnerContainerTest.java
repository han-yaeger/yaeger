package com.github.hanyaeger.api;

import com.github.hanyaeger.core.Updatable;
import com.github.hanyaeger.core.entities.EntityCollection;
import com.github.hanyaeger.api.entities.EntitySpawner;
import com.github.hanyaeger.core.entities.EntitySupplier;
import com.github.hanyaeger.core.exceptions.YaegerEngineException;
import com.google.inject.Injector;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntitySpawnerContainerTest {

    public static final Long TIMESTAMP = 0L;
    private EntitySpawnerContainerImpl sut;
    private List<EntitySpawner> spawners;
    private Injector injector;
    private Pane pane;

    @BeforeEach
    void setup() {
        sut = new EntitySpawnerContainerImpl();
        spawners = mock(ArrayList.class);
        injector = mock(Injector.class);
        pane = mock(Pane.class);
        sut.setSpawners(spawners);
        sut.setInjector(injector);
        sut.setRootPane(pane);
    }

    @Test
    void initFirstClearsListOfSpawners() {
        // Arrange

        // Act
        sut.initSpawners();

        // Assert
        verify(spawners).clear();
    }

    @Test
    void initCallsSetupSpawners() {
        // Arrange

        // Act
        sut.initSpawners();

        // Assert
        assertTrue(sut.isSetupSpawnersCalled());
    }

    @Test
    void addSpawnerAddTheSpawnerToTheSpawners() {
        // Arrange
        sut.setSpawners(new ArrayList<>());
        var spawner = mock(EntitySpawner.class);
        var supplier = mock(EntitySupplier.class);

        when(spawner.getSupplier()).thenReturn(supplier);

        // Act
        sut.addEntitySpawner(spawner);

        // Assert
        assertEquals(spawner, sut.getSpawners().get(0));
    }

    @Test
    void addSpawnerSetsRootPaneOnProvider() {
        // Arrange
        sut.setSpawners(new ArrayList<>());
        var spawner = mock(EntitySpawner.class);

        var supplier = mock(EntitySupplier.class);
        when(spawner.getSupplier()).thenReturn(supplier);

        // Act
        sut.addEntitySpawner(spawner);

        // Assert
        verify(supplier).setPane(pane);
    }

    @Test
    void addSpawnerAddTheSupplierToEntityCollectionIfActivationComplete() {
        // Arrange
        var entityCollection = mock(EntityCollection.class);
        sut.setActivationComplete(true);
        sut.setSpawners(new ArrayList<>());
        sut.setEntityCollection(entityCollection);

        var spawner = mock(EntitySpawner.class);
        var supplier = mock(EntitySupplier.class);
        when(spawner.getSupplier()).thenReturn(supplier);

        // Act
        sut.addEntitySpawner(spawner);

        // Assert
        verify(entityCollection).registerSupplier(spawner.getSupplier());
    }

    @Test
    void callSpawnersDoesNotBreakIfSpawnersIsNull() {
        // Arrange

        // Act
        var updatable = sut.callEntitySpawners();

        // Assert
        assertTrue(updatable instanceof Updatable);
    }

    @Test
    void callSpawnersDoesNotBreakIfSpawnersIsEmpty() {
        // Arrange
        sut.setSpawners(new ArrayList<>());

        // Act
        var updatable = sut.callEntitySpawners();

        // Assert
        assertTrue(updatable instanceof Updatable);
    }

    @Test
    void addSpawnerThrowsExceptionIfGetSpawnersReturnNull() {
        // Arrange
        var spawner1 = mock(EntitySpawner.class);
        sut.setSpawners(null);

        // Act

        // Assert
        assertThrows(YaegerEngineException.class, () -> sut.addEntitySpawner(spawner1));
    }

    @Test
    void invokingTheUpdatableCallsHandleOnEachSpawner() {
        // Arrange
        sut.setSpawners(new ArrayList<>());
        var spawner1 = mock(EntitySpawner.class);
        var spawner2 = mock(EntitySpawner.class);
        var supplier = mock(EntitySupplier.class);

        when(spawner1.getSupplier()).thenReturn(supplier);
        when(spawner2.getSupplier()).thenReturn(supplier);

        sut.addEntitySpawner(spawner1);
        sut.addEntitySpawner(spawner2);
        var updatable = sut.callEntitySpawners();

        // Act
        updatable.update(TIMESTAMP);

        // Assert
        verify(spawner1).handle(TIMESTAMP);
        verify(spawner2).handle(TIMESTAMP);
    }

    @Test
    void invokingTheUpdatableRemovesGarbageFromEntityCollection() {
        // Arrange
        sut.setSpawners(new ArrayList<>());
        var spawner1 = mock(EntitySpawner.class);
        var spawner2 = mock(EntitySpawner.class);
        var supplier = mock(EntitySupplier.class);

        when(spawner1.getSupplier()).thenReturn(supplier);
        when(spawner2.getSupplier()).thenReturn(supplier);

        when(spawner2.isGarbage()).thenReturn(true);
        when(spawner2.getSupplier()).thenReturn(supplier);

        sut.addEntitySpawner(spawner1);
        sut.addEntitySpawner(spawner2);

        var entityCollection = mock(EntityCollection.class);
        sut.setEntityCollection(entityCollection);

        var updatable = sut.callEntitySpawners();

        // Act
        updatable.update(TIMESTAMP);

        // Assert
        verify(entityCollection).removeSupplier(supplier);
    }

    @Test
    void invokingTheUpdatableRemovesGarbageFromSpawners() {
        // Arrange
        sut.setSpawners(new ArrayList<>());
        var spawner1 = mock(EntitySpawner.class);
        var spawner2 = mock(EntitySpawner.class);
        var supplier = mock(EntitySupplier.class);

        when(spawner1.getSupplier()).thenReturn(supplier);
        when(spawner2.getSupplier()).thenReturn(supplier);

        when(spawner2.isGarbage()).thenReturn(true);
        when(spawner2.getSupplier()).thenReturn(supplier);

        sut.addEntitySpawner(spawner1);
        sut.addEntitySpawner(spawner2);

        var entityCollection = mock(EntityCollection.class);
        sut.setEntityCollection(entityCollection);

        var updatable = sut.callEntitySpawners();

        // Act
        updatable.update(TIMESTAMP);

        // Assert
        assertEquals(1, sut.getSpawners().size());
    }

    private static class EntitySpawnerContainerImpl implements EntitySpawnerContainer {

        private List<EntitySpawner> spawners;
        private boolean setupSpawnersCalled = false;
        private Injector injector;
        private boolean activationComplete = false;
        private EntityCollection entityCollection;
        private Pane rootPane;

        @Override
        public void setupEntitySpawners() {
            setupSpawnersCalled = true;
        }

        @Override
        public Injector getInjector() {
            return injector;
        }

        @Override
        public EntityCollection getEntityCollection() {
            return entityCollection;
        }

        @Override
        public List<EntitySpawner> getSpawners() {
            return spawners;
        }

        public void setSpawners(List<EntitySpawner> spawners) {
            this.spawners = spawners;
        }

        public boolean isSetupSpawnersCalled() {
            return setupSpawnersCalled;
        }

        public void setInjector(Injector injector) {
            this.injector = injector;
        }

        @Override
        public boolean isActivationComplete() {
            return activationComplete;
        }

        public void setActivationComplete(boolean activationComplete) {
            this.activationComplete = activationComplete;
        }

        public void setEntityCollection(EntityCollection entityCollection) {
            this.entityCollection = entityCollection;
        }

        @Override
        public Pane getRootPane() {
            return rootPane;
        }

        public void setRootPane(Pane rootPane) {
            this.rootPane = rootPane;
        }
    }
}
