package com.github.hanyaeger.api.engine.entities;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.annotations.AnnotationProcessor;
import com.github.hanyaeger.api.engine.debug.Debugger;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.events.EventTypes;
import com.github.hanyaeger.api.engine.entities.entity.events.userinput.KeyListener;
import com.google.inject.Injector;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntityCollectionTest {

    private EntityCollection sut;
    private Injector injector;
    private Pane pane;
    private AnnotationProcessor annotationProcessor;

    @BeforeEach
    void setup() {
        injector = mock(Injector.class);
        annotationProcessor = mock(AnnotationProcessor.class);
        pane = mock(Pane.class);
    }

    @Test
    void newInstanceIsEmpty() {
        // Arrange
        var debugger = mock(Debugger.class);

        // Act
        sut = new EntityCollection(pane);
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
        sut = new EntityCollection(pane);
        sut.setAnnotationProcessor(annotationProcessor);
        sut.registerSupplier(supplier);

        // Act
        sut.clear();

        // Assert
        verify(supplier).clear();
    }

    @Test
    void suppliersEntitiesAreTransferredAtUpdate() {
        // Arrange
        var updatableEntity = mock(UpdatableEntity.class);
        var node = mock(Node.class, withSettings().withoutAnnotations());
        when(updatableEntity.getNode()).thenReturn(Optional.of(node));

        List<YaegerEntity> updatables = new ArrayList<>();
        updatables.add(updatableEntity);
        var supplier = mock(EntitySupplier.class);
        when(supplier.get()).thenReturn(updatables);

        var children = mock(ObservableList.class);
        when(pane.getChildren()).thenReturn(children);

        sut = new EntityCollection(pane);
        sut.setAnnotationProcessor(annotationProcessor);
        sut.init(injector);

        // Act
        sut.registerSupplier(supplier);
        sut.initialUpdate();

        // Assert
        verify(supplier).get();
    }

    @Test
    void keyListeningEntityGetsNotifiedWhenKeyInputChangeAndSetIsEmpty() {
        // Arrange
        var keyListeningEntity = new KeyListeningEntityImpl(new Coordinate2D(0, 0));
        var node = mock(Node.class, withSettings().withoutAnnotations());
        keyListeningEntity.setNode(node);
        var scene = mock(Scene.class);
        when(node.getScene()).thenReturn(scene);

        var children = mock(ObservableList.class);
        when(pane.getChildren()).thenReturn(children);

        var entitySupplier = new EntitySupplier();
        entitySupplier.add(keyListeningEntity);

        Set<KeyCode> keycodes = new HashSet<>();

        // Act
        sut = new EntityCollection(pane);
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
        var keyListeningEntity = new KeyListeningEntityImpl(new Coordinate2D(0, 0));
        var node = mock(Node.class, withSettings().withoutAnnotations());
        keyListeningEntity.setNode(node);
        var scene = mock(Scene.class);
        when(node.getScene()).thenReturn(scene);

        var children = mock(ObservableList.class);
        when(pane.getChildren()).thenReturn(children);

        var entitySupplier = new EntitySupplier();
        entitySupplier.add(keyListeningEntity);

        Set<KeyCode> keycodes = new HashSet<>();
        keycodes.add(KeyCode.Y);
        keycodes.add(KeyCode.A);
        keycodes.add(KeyCode.E);
        keycodes.add(KeyCode.G);
        keycodes.add(KeyCode.E);
        keycodes.add(KeyCode.R);

        // Act
        sut = new EntityCollection(pane);
        sut.setAnnotationProcessor(annotationProcessor);
        sut.init(injector);
        sut.registerSupplier(entitySupplier);
        sut.update(0);
        sut.notifyGameObjectsOfPressedKeys(keycodes);

        // Assert
        assertEquals(keycodes, keyListeningEntity.getPressedKeys());
    }

    @Test
    void addDynamicEntityCallsAnnotationProcessor() {
        // Arrange
        var updatableEntity = mock(UpdatableEntity.class);

        var children = mock(ObservableList.class);
        when(pane.getChildren()).thenReturn(children);

        sut = new EntityCollection(pane);
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
        var updatableEntity = mock(UpdatableEntity.class);
        var node = mock(Node.class, withSettings().withoutAnnotations());
        when(updatableEntity.getNode()).thenReturn(Optional.of(node));

        List<YaegerEntity> updatables = new ArrayList<>();
        updatables.add(updatableEntity);
        var supplier = mock(EntitySupplier.class);
        when(supplier.get()).thenReturn(updatables);

        var children = mock(ObservableList.class);
        when(pane.getChildren()).thenReturn(children);

        sut = new EntityCollection(pane);
        sut.setAnnotationProcessor(annotationProcessor);
        sut.init(injector);

        // Act
        sut.registerSupplier(supplier);
        sut.initialUpdate();

        // Assert
        verify(updatableEntity).addToEntityCollection(sut);
    }

    @Test
    void attachEventListerIsCalledForEachEntity() {
        // Arrange
        var updatableEntity = mock(UpdatableEntity.class);
        var node = mock(Node.class, withSettings().withoutAnnotations());
        when(updatableEntity.getNode()).thenReturn(Optional.of(node));

        List<YaegerEntity> updatables = new ArrayList<>();
        updatables.add(updatableEntity);
        var supplier = mock(EntitySupplier.class);
        when(supplier.get()).thenReturn(updatables);

        var children = mock(ObservableList.class);
        when(pane.getChildren()).thenReturn(children);

        sut = new EntityCollection(pane);
        sut.setAnnotationProcessor(annotationProcessor);
        sut.init(injector);

        // Act
        sut.registerSupplier(supplier);
        sut.initialUpdate();

        // Assert
        verify(updatableEntity).attachEventListener(eq(EventTypes.REMOVE), any());
    }

    @Test
    void transferCoordinatesToNodeIsCalledForEachEntity() {
        // Arrange
        var updatableEntity = mock(UpdatableEntity.class);
        var node = mock(Node.class, withSettings().withoutAnnotations());
        when(updatableEntity.getNode()).thenReturn(Optional.of(node));

        List<YaegerEntity> updatables = new ArrayList<>();
        updatables.add(updatableEntity);
        var supplier = mock(EntitySupplier.class);
        when(supplier.get()).thenReturn(updatables);

        var children = mock(ObservableList.class);
        when(pane.getChildren()).thenReturn(children);

        sut = new EntityCollection(pane);
        sut.setAnnotationProcessor(annotationProcessor);
        sut.init(injector);

        // Act
        sut.registerSupplier(supplier);
        sut.initialUpdate();

        // Assert
        verify(updatableEntity).transferCoordinatesToNode();
    }

    @Test
    void applyTranslationsForAnchorPointIsCalledForEachEntity() {
        // Arrange
        var updatableEntity = mock(UpdatableEntity.class);
        var node = mock(Node.class, withSettings().withoutAnnotations());
        when(updatableEntity.getNode()).thenReturn(Optional.of(node));

        List<YaegerEntity> updatables = new ArrayList<>();
        updatables.add(updatableEntity);
        var supplier = mock(EntitySupplier.class);
        when(supplier.get()).thenReturn(updatables);

        var children = mock(ObservableList.class);
        when(pane.getChildren()).thenReturn(children);

        sut = new EntityCollection(pane);
        sut.setAnnotationProcessor(annotationProcessor);
        sut.init(injector);

        // Act
        sut.registerSupplier(supplier);
        sut.initialUpdate();

        // Assert
        verify(updatableEntity).applyTranslationsForAnchorPoint();
    }

    private abstract class UpdatableEntity extends YaegerEntity implements Updatable {

        /**
         * Instantiate a new {@link YaegerEntity} for the given {@link Coordinate2D} and textDelegate.
         *
         * @param initialPosition the initial {@link Coordinate2D} of this {@link YaegerEntity}
         */
        public UpdatableEntity(Coordinate2D initialPosition) {
            super(initialPosition);
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
        public void setReferenceX(double x) {
            // Not required here
        }

        @Override
        public void setReferenceY(double y) {
            // Not required here
        }

        @Override
        public Optional<Node> getNode() {
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
