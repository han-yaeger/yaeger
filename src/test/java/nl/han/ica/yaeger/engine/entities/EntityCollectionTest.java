package nl.han.ica.yaeger.engine.entities;

import com.google.inject.Injector;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import nl.han.ica.yaeger.engine.debug.Debugger;
import nl.han.ica.yaeger.engine.userinput.KeyListener;
import nl.han.ica.yaeger.engine.entities.entity.Entity;
import nl.han.ica.yaeger.engine.entities.entity.Position;
import nl.han.ica.yaeger.engine.entities.entity.Updatable;
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
        Group group = mock(Group.class);
        Debugger debugger = mock(Debugger.class);

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
        EntitySupplier supplier = mock(EntitySupplier.class);
        Group group = mock(Group.class);
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
        UpdatableEntity updatableEntity = mock(UpdatableEntity.class);
        Node node = mock(Node.class);
        when(updatableEntity.getGameNode()).thenReturn(node);

        Set<Entity> updatables = new HashSet<>();
        updatables.add(updatableEntity);
        EntitySupplier supplier = mock(EntitySupplier.class);
        when(supplier.get()).thenReturn(updatables);
        supplier.add(updatableEntity);

        Group group = mock(Group.class);
        ObservableList<Node> children = mock(ObservableList.class);
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
        KeyListeningEntity keyListeningEntity = mock(KeyListeningEntity.class);
        Node node = mock(Node.class);
        when(keyListeningEntity.getGameNode()).thenReturn(node);

        Group group = mock(Group.class);
        ObservableList<Node> children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        EntitySupplier entitySupplier = new EntitySupplier();
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
        KeyListeningEntity keyListeningEntity = mock(KeyListeningEntity.class);
        Node node = mock(Node.class);
        when(keyListeningEntity.getGameNode()).thenReturn(node);

        Group group = mock(Group.class);
        ObservableList<Node> children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        EntitySupplier entitySupplier = new EntitySupplier();
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
    public Position getPosition() {
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
    public Position getPosition() {
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
