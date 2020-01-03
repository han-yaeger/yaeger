package nl.meron.yaeger.engine.entities;

import com.google.inject.Injector;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import nl.meron.yaeger.engine.debug.Debugger;
import nl.meron.yaeger.engine.entities.entity.*;
import nl.meron.yaeger.engine.entities.entity.events.userinput.KeyListener;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MousePressedListener;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MouseReleasedListener;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class EntityCollectionTest {

    private EntityCollection sut;
    private Injector injector;

    @BeforeEach
    void setup() {
        injector = mock(Injector.class);
    }

    @Test
    void newInstanceIsEmtpy() {
        // Setup
        var group = mock(Group.class);
        var debugger = mock(Debugger.class);

        // Test
        sut = new EntityCollection(group);
        sut.addStatisticsObserver(debugger);

        // Verify
        Assertions.assertEquals(0, sut.getStatistics().getStatics());
        Assertions.assertEquals(0, sut.getStatistics().getUpdatables());
        Assertions.assertEquals(0, sut.getStatistics().getGarbage());
        Assertions.assertEquals(0, sut.getStatistics().getKeyListeners());
        Assertions.assertEquals(0, sut.getStatistics().getSuppliers());
    }

    @Test
    void clearClearsSupplier() {
        // Setup
        var supplier = mock(EntitySupplier.class);
        var group = mock(Group.class);
        sut = new EntityCollection(group);
        sut.registerSupplier(supplier);

        // Test
        sut.clear();

        // Verify
        verify(supplier).clear();
    }

    @Test
    void suppliersEntitiesAreTransferredAtUpdate() {
        // Setup
        var updatableEntity = mock(UpdatableEntity.class);
        var node = mock(Node.class, withSettings().withoutAnnotations());
        when(updatableEntity.getGameNode()).thenReturn(node);

        Set<Entity> updatables = new HashSet<>();
        updatables.add(updatableEntity);
        var supplier = mock(EntitySupplier.class);
        when(supplier.get()).thenReturn(updatables);

        var group = mock(Group.class);
        var children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        sut = new EntityCollection(group);
        sut.init(injector);

        // Test
        sut.registerSupplier(supplier);
        sut.initialUpdate();

        // Verify
        verify(supplier).get();
    }

    @Test
    void entityWithExtraUpdateGetsAddedToTheUpdaters() {
        var updateDelegatingEntity = new UpdateDelegatingEntity();
        var node = mock(Node.class, withSettings().withoutAnnotations());

        updateDelegatingEntity.setNode(node);

        Set<Entity> updatables = new HashSet<>();
        updatables.add(updateDelegatingEntity);
        var supplier = mock(EntitySupplier.class);
        when(supplier.get()).thenReturn(updatables);

        var group = mock(Group.class);
        var children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        sut = new EntityCollection(group);
        sut.init(injector);

        sut.registerSupplier(supplier);
        sut.initialUpdate();

        // Test
        sut.update(0);

        // Verify
        assertTrue(updateDelegatingEntity.extraUpdateCalled);
    }

    @Test
    void entityWithAsFirstUpdateGetsAddedToTheUpdatersAsFirst() {
        var firstUpdateDelegatingEntity = new FirstUpdateDelegatingEntity();
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var updater = mock(Updater.class);
        firstUpdateDelegatingEntity.setUpdater(updater);
        firstUpdateDelegatingEntity.setNode(node);

        Set<Entity> updatables = new HashSet<>();
        updatables.add(firstUpdateDelegatingEntity);
        var supplier = mock(EntitySupplier.class);
        when(supplier.get()).thenReturn(updatables);

        var group = mock(Group.class);
        var children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        sut = new EntityCollection(group);
        sut.init(injector);

        sut.registerSupplier(supplier);

        // Test
        sut.initialUpdate();

        // Verify
        verify(updater).addUpdatable(any(Updatable.class), eq(true));
    }

    @Test
    void keyListeningEntityGetsNotifiedWhenKeyInputChangeAndSetIsEmpty() {
        // Setup
        var keyListeningEntity = mock(KeyListeningEntity.class);
        var node = mock(Node.class, withSettings().withoutAnnotations());
        when(keyListeningEntity.getGameNode()).thenReturn(node);

        var group = mock(Group.class);
        var children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        var entitySupplier = new EntitySupplier();
        entitySupplier.add(keyListeningEntity);

        Set<KeyCode> keycodes = new HashSet<>();

        // Test
        sut = new EntityCollection(group);
        sut.init(injector);
        sut.registerSupplier(entitySupplier);
        sut.update(0);
        sut.notifyGameObjectsOfPressedKeys(keycodes);

        // Verify
        verify(keyListeningEntity).onPressedKeysChange(keycodes);
    }

    @Test
    void keyListeningEntityGetsNotifiedWhenKeyInputChangeAndSetIsFilled() {
        // Setup
        var keyListeningEntity = mock(KeyListeningEntity.class);
        var node = mock(Node.class, withSettings().withoutAnnotations());
        when(keyListeningEntity.getGameNode()).thenReturn(node);

        var group = mock(Group.class);
        var children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        var entitySupplier = new EntitySupplier();
        entitySupplier.add(keyListeningEntity);

        Set<KeyCode> keycodes = new HashSet<>();
        keycodes.add(KeyCode.Y);
        keycodes.add(KeyCode.A);
        keycodes.add(KeyCode.E);
        keycodes.add(KeyCode.G);
        keycodes.add(KeyCode.E);
        keycodes.add(KeyCode.R);

        // Test
        sut = new EntityCollection(group);
        sut.init(injector);
        sut.registerSupplier(entitySupplier);
        sut.update(0);
        sut.notifyGameObjectsOfPressedKeys(keycodes);

        // Verify
        verify(keyListeningEntity).onPressedKeysChange(keycodes);
    }

    @Test
    void mousePressedListeningEntityAttachesListenerOnMousePressedEvent() {
        // Setup
        var mousePressedListeningEntity = mock(MousePressedListeningEntity.class);
        var node = mock(Node.class, withSettings().withoutAnnotations());
        when(mousePressedListeningEntity.getGameNode()).thenReturn(node);

        var group = mock(Group.class);
        var children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        var entitySupplier = new EntitySupplier();
        entitySupplier.add(mousePressedListeningEntity);

        // Test
        sut = new EntityCollection(group);
        sut.init(injector);
        sut.registerSupplier(entitySupplier);
        sut.update(0);

        // Verify
        verify(mousePressedListeningEntity).attachMousePressedListener();
    }

    @Test
    void mouseReleasedListeningEntityAttachesListenerOnMouseReleasedEvent() {
        // Setup
        var mousePressedListeningEntity = mock(MouseReleasedListeningEntity.class);
        var node = mock(Node.class, withSettings().withoutAnnotations());
        when(mousePressedListeningEntity.getGameNode()).thenReturn(node);

        var group = mock(Group.class);
        var children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        var entitySupplier = new EntitySupplier();
        entitySupplier.add(mousePressedListeningEntity);

        // Test
        sut = new EntityCollection(group);
        sut.init(injector);
        sut.registerSupplier(entitySupplier);
        sut.update(0);

        // Verify
        verify(mousePressedListeningEntity).attachMouseReleasedListener();
    }

    private class FirstUpdateDelegatingEntity extends UpdatableEntity implements UpdateDelegator {

        boolean extraUpdateCalled = false;

        private Updater updater;
        private Node node;

        @UpdatableProvider(asFirst = true)
        public Updatable provideUpdate() {
            return timestamp -> {
                setTrue();
            };
        }

        void setTrue() {
            this.extraUpdateCalled = true;
        }

        public void setNode(Node node) {
            this.node = node;
        }

        @Override
        public Node getGameNode() {
            return this.node;
        }

        @Override
        public Updater getUpdater() {
            return updater;
        }

        public void setUpdater(Updater updater) {
            this.updater = updater;
        }

        @Override
        public void update(long timestamp) {
            updater.update(timestamp);
        }
    }

    private class UpdateDelegatingEntity extends UpdatableEntity implements UpdateDelegator {

        boolean extraUpdateCalled = false;

        private Updater updater = new Updater();
        private Node node;

        @UpdatableProvider
        public Updatable provideUpdate() {
            return timestamp -> {
                setTrue();
            };
        }

        void setTrue() {
            this.extraUpdateCalled = true;
        }

        public void setNode(Node node) {
            this.node = node;
        }

        @Override
        public Node getGameNode() {
            return this.node;
        }

        @Override
        public Updater getUpdater() {
            return updater;
        }

        @Override
        public void update(long timestamp) {
            updater.update(timestamp);
        }
    }

    private class UpdatableEntity implements Entity, Updatable {

        @Override
        public void remove() {
            // Not required here.
        }

        @Override
        public Node getGameNode() {
            return null;
        }

        @Override
        public Point getPosition() {
            return null;
        }

        @Override
        public void placeOnPosition(double x, double y) {
            // Not required here.
        }

        @Override
        public void update(long timestamp) {
            // Not required here.
        }

        @Override
        public void init(Injector injector) {
            // Not required here.
        }
    }

    private class MousePressedListeningEntity implements Entity, MousePressedListener {

        @Override
        public Point getPosition() {
            return null;
        }

        @Override
        public void placeOnPosition(double x, double y) {
            // Not required here.
        }

        @Override
        public void init(Injector injector) {
            // Not required here.
        }

        @Override
        public void remove() {
            // Not required here.
        }

        @Override
        public Node getGameNode() {
            return null;
        }

        @Override
        public void onMousePressed(MouseButton button) {
            // Not required here.
        }
    }

    private class MouseReleasedListeningEntity implements Entity, MouseReleasedListener {

        @Override
        public Point getPosition() {
            return null;
        }

        @Override
        public void placeOnPosition(double x, double y) {
            // Not required here.
        }

        @Override
        public void init(Injector injector) {
            // Not required here.
        }

        @Override
        public void remove() {
            // Not required here.
        }

        @Override
        public Node getGameNode() {
            return null;
        }

        @Override
        public void onMouseReleased(MouseButton button) {
            // Not required here.
        }
    }

    private class KeyListeningEntity implements Entity, KeyListener {

        @Override
        public void remove() {
            // Not required here.
        }

        @Override
        public Node getGameNode() {
            return null;
        }

        @Override
        public Point getPosition() {
            return null;
        }

        @Override
        public void placeOnPosition(double x, double y) {
            // Not required here.
        }

        @Override
        public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
            // Not required here.
        }

        @Override
        public void init(Injector injector) {
            // Not required here.
        }
    }

}
