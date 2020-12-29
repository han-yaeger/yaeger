package com.github.hanyaeger.api.engine.entities;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.YaegerConfig;
import com.github.hanyaeger.api.engine.annotations.AnnotationProcessor;
import com.github.hanyaeger.api.engine.debug.Debugger;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.events.userinput.KeyListener;
import com.google.inject.Injector;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntityCollectionTest {

    private static final Coordinate2D LOCATION = new Coordinate2D(0, 0);

    private EntityCollection sut;
    private Injector injector;
    private Pane pane;
    private YaegerConfig config;
    private AnnotationProcessor annotationProcessor;

    @BeforeEach
    void setup() {
        injector = mock(Injector.class);
        annotationProcessor = mock(AnnotationProcessor.class);
        pane = mock(Pane.class);
        config = mock(YaegerConfig.class);
    }

    @Test
    void newInstanceIsEmpty() {
        // Arrange
        var debugger = mock(Debugger.class);

        // Act
        sut = new EntityCollection(pane, config);
        sut.addStatisticsObserver(debugger);
        sut.setAnnotationProcessor(annotationProcessor);

        // Assert
        assertEquals(0, sut.getStatistics().getStatics());
        assertEquals(0, sut.getStatistics().getUpdatables());
        assertEquals(0, sut.getStatistics().getGarbage());
        assertEquals(0, sut.getStatistics().getKeyListeners());
        assertEquals(0, sut.getStatistics().getSuppliers());
    }

    @Test
    void clearClearsSupplier() {
        // Arrange
        var supplier = mock(EntitySupplier.class);
        sut = new EntityCollection(pane, config);
        sut.setAnnotationProcessor(annotationProcessor);
        sut.registerSupplier(supplier);

        // Act
        sut.clear();

        // Assert
        verify(supplier).clear();
    }

    @Nested
    class TestsWithKeyListeningEntites {

        private KeyListeningEntityImpl keyListeningEntity;
        private EntitySupplier entitySupplier;

        @BeforeEach
        void setup() {
            keyListeningEntity = new KeyListeningEntityImpl(new Coordinate2D(0, 0));
            var node = mock(Node.class, withSettings().withoutAnnotations());
            keyListeningEntity.setNode(node);
            var scene = mock(Scene.class);
            when(node.getScene()).thenReturn(scene);

            var children = mock(ObservableList.class);
            when(pane.getChildren()).thenReturn(children);

            entitySupplier = new EntitySupplier();
            entitySupplier.add(keyListeningEntity);
        }

        @Test
        void keyListeningEntityGetsNotifiedWhenKeyInputChangeAndSetIsEmpty() {
            // Arrange
            Set<KeyCode> keycodes = new HashSet<>();

            // Act
            sut = new EntityCollection(pane, config);
            sut.setAnnotationProcessor(annotationProcessor);
            sut.init(injector);
            sut.registerSupplier(entitySupplier);
            sut.update(0);
            sut.notifyGameObjectsOfPressedKeys(keycodes);

            // Assert
            assertEquals(keycodes, keyListeningEntity.getPressedKeys());
        }

        @Test
        void keyListeningEntityGetsNotifiedWhenKeyInputChangeAndSetIsFilled() {
            // Arrange
            Set<KeyCode> keycodes = new HashSet<>();
            keycodes.add(KeyCode.Y);
            keycodes.add(KeyCode.A);
            keycodes.add(KeyCode.E);
            keycodes.add(KeyCode.G);
            keycodes.add(KeyCode.E);
            keycodes.add(KeyCode.R);

            // Act
            sut = new EntityCollection(pane, config);
            sut.setAnnotationProcessor(annotationProcessor);
            sut.init(injector);
            sut.registerSupplier(entitySupplier);
            sut.update(0);
            sut.notifyGameObjectsOfPressedKeys(keycodes);

            // Assert
            assertEquals(keycodes, keyListeningEntity.getPressedKeys());
        }
    }

    @Nested
    class TestsWithUpdatableEntities {

        private UpdatableEntity updatableEntity;

        @BeforeEach
        void setup() {
            updatableEntity = new UpdatableEntity(LOCATION);
            var node = mock(Node.class, withSettings().withoutAnnotations());
            updatableEntity.setNode(node);
        }

        @Test
        void suppliersEntitiesAreTransferredAtUpdate() {
            // Arrange
            List<YaegerEntity> updatables = new ArrayList<>();
            updatables.add(updatableEntity);
            var supplier = mock(EntitySupplier.class);
            when(supplier.get()).thenReturn(updatables);

            var children = mock(ObservableList.class);
            when(pane.getChildren()).thenReturn(children);

            sut = new EntityCollection(pane, config);
            sut.setAnnotationProcessor(annotationProcessor);
            sut.init(injector);

            // Act
            sut.registerSupplier(supplier);
            sut.initialUpdate();

            // Assert
            verify(supplier).get();
        }

        @Test
        void addDynamicEntityCallsAnnotationProcessor() {
            // Arrange
            var children = mock(ObservableList.class);
            when(pane.getChildren()).thenReturn(children);

            sut = new EntityCollection(pane, config);
            sut.setAnnotationProcessor(annotationProcessor);
            sut.init(injector);

            // Act
            sut.addDynamicEntity(updatableEntity);

            // Assert
            verify(annotationProcessor).configureUpdateDelegators(updatableEntity);
        }

        @Test
        void addToEntityCollectionIsCalledForEachEntity() {
            // Arrange
            List<YaegerEntity> updatables = new ArrayList<>();
            updatables.add(updatableEntity);
            var supplier = mock(EntitySupplier.class);
            when(supplier.get()).thenReturn(updatables);

            var children = mock(ObservableList.class);
            when(pane.getChildren()).thenReturn(children);

            sut = new EntityCollection(pane, config);
            sut.setAnnotationProcessor(annotationProcessor);
            sut.init(injector);

            // Act
            sut.registerSupplier(supplier);
            sut.initialUpdate();

            // Assert
            assertTrue(updatableEntity.isAddToEntityCollectionCalled());
        }

        @Test
        void attachEventListerIsCalledForEachEntity() {
            // Arrange
            List<YaegerEntity> updatables = new ArrayList<>();
            updatables.add(updatableEntity);
            var supplier = mock(EntitySupplier.class);
            when(supplier.get()).thenReturn(updatables);

            var children = mock(ObservableList.class);
            when(pane.getChildren()).thenReturn(children);

            sut = new EntityCollection(pane, config);
            sut.setAnnotationProcessor(annotationProcessor);
            sut.init(injector);

            // Act
            sut.registerSupplier(supplier);
            sut.initialUpdate();

            // Assert
            assertTrue(updatableEntity.isAttachEventListenerCalled());
        }

        @Test
        void transferCoordinatesToNodeIsCalledForEachEntity() {
            // Arrange
            List<YaegerEntity> updatables = new ArrayList<>();
            updatables.add(updatableEntity);
            var supplier = mock(EntitySupplier.class);
            when(supplier.get()).thenReturn(updatables);

            var children = mock(ObservableList.class);
            when(pane.getChildren()).thenReturn(children);

            sut = new EntityCollection(pane, config);
            sut.setAnnotationProcessor(annotationProcessor);
            sut.init(injector);

            // Act
            sut.registerSupplier(supplier);
            sut.initialUpdate();

            // Assert
            assertTrue(updatableEntity.isTransferCoordinatesToNodeCalled());
        }

        @Test
        void applyTranslationsForAnchorPointIsCalledForEachEntity() {
            // Arrange
            List<YaegerEntity> updatables = new ArrayList<>();
            updatables.add(updatableEntity);
            var supplier = mock(EntitySupplier.class);
            when(supplier.get()).thenReturn(updatables);

            var children = mock(ObservableList.class);
            when(pane.getChildren()).thenReturn(children);

            sut = new EntityCollection(pane, config);
            sut.setAnnotationProcessor(annotationProcessor);
            sut.init(injector);

            // Act
            sut.registerSupplier(supplier);
            sut.initialUpdate();

            // Assert
            assertTrue(updatableEntity.isApplyTranslationsForAnchorPointCalled());
        }

        private class UpdatableEntity extends YaegerEntity implements Updatable {

            private Node node;
            private boolean transferCoordinatesToNodeCalled = false;
            private boolean applyTranslationsForAnchorPointCalled = false;
            private boolean attachEventListenerCalled = false;
            private boolean addToEntityCollectionCalled = false;

            public UpdatableEntity(Coordinate2D initialPosition) {
                super(initialPosition);
            }

            @Override
            public void update(long timestamp) {
                // Not required here
            }

            @Override
            public Optional<? extends Node> getNode() {
                return Optional.of(node);
            }

            public void setNode(Node node) {
                this.node = node;
            }

            @Override
            public void addToEntityCollection(EntityCollection collection) {
                super.addToEntityCollection(collection);

                this.addToEntityCollectionCalled = true;
            }

            @Override
            public void attachEventListener(EventType eventType, EventHandler eventHandler) {
                super.attachEventListener(eventType, eventHandler);

                this.attachEventListenerCalled = true;
            }

            @Override
            public void applyTranslationsForAnchorPoint() {
                super.applyTranslationsForAnchorPoint();

                this.applyTranslationsForAnchorPointCalled = true;
            }

            @Override
            public void transferCoordinatesToNode() {
                super.transferCoordinatesToNode();

                this.transferCoordinatesToNodeCalled = true;
            }

            public boolean isTransferCoordinatesToNodeCalled() {
                return transferCoordinatesToNodeCalled;
            }

            public boolean isApplyTranslationsForAnchorPointCalled() {
                return applyTranslationsForAnchorPointCalled;
            }

            public boolean isAttachEventListenerCalled() {
                return attachEventListenerCalled;
            }

            public boolean isAddToEntityCollectionCalled() {
                return addToEntityCollectionCalled;
            }
        }
    }


    private class KeyListeningEntityImpl extends YaegerEntity implements KeyListener {

        public KeyListeningEntityImpl(Coordinate2D initialPosition) {
            super(initialPosition);
        }

        private Node node;
        private Set<KeyCode> pressedKeys;

        @Override
        public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
            this.pressedKeys = pressedKeys;
        }

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.of(node);
        }

        public void setNode(Node node) {
            this.node = node;
        }

        public Set<KeyCode> getPressedKeys() {
            return pressedKeys;
        }
    }
}
