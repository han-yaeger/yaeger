package nl.meron.yaeger.engine.entities;

import com.google.inject.Injector;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import nl.meron.yaeger.engine.debug.Debugger;
import nl.meron.yaeger.engine.entities.events.listeners.KeyListener;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.Updatable;
import nl.meron.yaeger.engine.entities.events.listeners.MousePressedListener;
import nl.meron.yaeger.engine.entities.events.listeners.MouseReleasedListener;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.*;

class EntityCollectionTest {

    private EntityCollection entityCollection;
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
        entityCollection = new EntityCollection(group);
        entityCollection.addStatisticsObserver(debugger);

        // Verify
        Assertions.assertEquals(0, entityCollection.getStatistics().getStatics());
        Assertions.assertEquals(0, entityCollection.getStatistics().getUpdatables());
        Assertions.assertEquals(0, entityCollection.getStatistics().getGarbage());
        Assertions.assertEquals(0, entityCollection.getStatistics().getKeyListeners());
        Assertions.assertEquals(0, entityCollection.getStatistics().getSuppliers());
    }

    @Test
    void clearClearsSupplier() {
        // Setup
        var supplier = mock(EntitySupplier.class);
        var group = mock(Group.class);
        entityCollection = new EntityCollection(group);
        entityCollection.registerSupplier(supplier);

        // Test
        entityCollection.clear();

        // Verify
        verify(supplier).clear();
    }

    @Test
    void suppliersEntitiesAreTransferredAtUpdate() {
        // Setup
        var updatableEntity = mock(UpdatableEntity.class);
        var node = mock(Node.class);
        when(updatableEntity.getGameNode()).thenReturn(node);

        Set<Entity> updatables = new HashSet<>();
        updatables.add(updatableEntity);
        var supplier = mock(EntitySupplier.class);
        when(supplier.get()).thenReturn(updatables);
        supplier.add(updatableEntity);

        var group = mock(Group.class);
        var children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        entityCollection = new EntityCollection(group);
        entityCollection.init(injector);

        // Test
        entityCollection.registerSupplier(supplier);
        entityCollection.initialUpdate();

        // Verify
        verify(supplier).get();
    }

    @Test
    void keyListeningEntityGetsNotifiedWhenKeyInputChangeAndSetIsEmpty() {
        // Setup
        var keyListeningEntity = mock(KeyListeningEntity.class);
        var node = mock(Node.class);
        when(keyListeningEntity.getGameNode()).thenReturn(node);

        var group = mock(Group.class);
        var children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        var entitySupplier = new EntitySupplier();
        entitySupplier.add(keyListeningEntity);

        Set<KeyCode> keycodes = new HashSet<>();

        // Test
        entityCollection = new EntityCollection(group);
        entityCollection.init(injector);
        entityCollection.registerSupplier(entitySupplier);
        entityCollection.update(0);
        entityCollection.notifyGameObjectsOfPressedKeys(keycodes);

        // Verify
        verify(keyListeningEntity).onPressedKeysChange(keycodes);
    }

    @Test
    void keyListeningEntityGetsNotifiedWhenKeyInputChangeAndSetIsFilled() {
        // Setup
        var keyListeningEntity = mock(KeyListeningEntity.class);
        var node = mock(Node.class);
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
        entityCollection = new EntityCollection(group);
        entityCollection.init(injector);
        entityCollection.registerSupplier(entitySupplier);
        entityCollection.update(0);
        entityCollection.notifyGameObjectsOfPressedKeys(keycodes);

        // Verify
        verify(keyListeningEntity).onPressedKeysChange(keycodes);
    }

    @Test
    void mousePressedListeningEntityAttachesListenerOnMousePressedEvent() {
        // Setup
        var mousePressedListeningEntity = mock(MousePressedListeningEntity.class);
        var node = mock(Node.class);
        when(mousePressedListeningEntity.getGameNode()).thenReturn(node);

        var group = mock(Group.class);
        var children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        var entitySupplier = new EntitySupplier();
        entitySupplier.add(mousePressedListeningEntity);

        // Test
        entityCollection = new EntityCollection(group);
        entityCollection.init(injector);
        entityCollection.registerSupplier(entitySupplier);
        entityCollection.update(0);

        // Verify
        verify(mousePressedListeningEntity).attachMousePressedListener();
    }

    @Test
    void mouseReleasedListeningEntityAttachesListenerOnMouseReleasedEvent() {
        // Setup
        var mousePressedListeningEntity = mock(MouseReleasedListeningEntity.class);
        var node = mock(Node.class);
        when(mousePressedListeningEntity.getGameNode()).thenReturn(node);

        var group = mock(Group.class);
        var children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        var entitySupplier = new EntitySupplier();
        entitySupplier.add(mousePressedListeningEntity);

        // Test
        entityCollection = new EntityCollection(group);
        entityCollection.init(injector);
        entityCollection.registerSupplier(entitySupplier);
        entityCollection.update(0);

        // Verify
        verify(mousePressedListeningEntity).attachMouseReleasedListener();
    }
}

class UpdatableEntity implements Entity, Updatable {

    @Override
    public void remove() {
        // Not required here.
    }

    @Override
    public Node getGameNode() {
        return null;
    }

    @Override
    public Point getAnchorPoint() {
        return null;
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

class MousePressedListeningEntity implements Entity, MousePressedListener {

    @Override
    public Point getAnchorPoint() {
        return null;
    }

    @Override
    public void init(Injector injector) {
    }

    @Override
    public void remove() {
    }

    @Override
    public Node getGameNode() {
        return null;
    }

    @Override
    public void onMousePressed(MouseButton button) {

    }
}

class MouseReleasedListeningEntity implements Entity, MouseReleasedListener {

    @Override
    public Point getAnchorPoint() {
        return null;
    }

    @Override
    public void init(Injector injector) {
    }

    @Override
    public void remove() {
    }

    @Override
    public Node getGameNode() {
        return null;
    }

    @Override
    public void onMouseReleased(MouseButton button) {

    }
}

class KeyListeningEntity implements Entity, KeyListener {

    @Override
    public void remove() {
        // Not required here.
    }

    @Override
    public Node getGameNode() {
        return null;
    }

    @Override
    public Point getAnchorPoint() {
        return null;
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
