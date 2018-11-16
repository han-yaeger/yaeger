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
    void initWithANullInitialSetsGivesAnEmptyStaticEntityCollection() {
        // Setup
        Group group = mock(Group.class);
        Debugger debugger = mock(Debugger.class);

        // Test
        entityCollection = new EntityCollection(group, null, injector);
        entityCollection.addStatisticsObserver(debugger);

        // Verify
        Assertions.assertEquals(0, entityCollection.getStatistics().getStatics());
    }

    @Test
    void initWithAnEmptyInitialSetsGivesAnEmptyDynamicEntityCollection() {
        // Setup
        Set<Entity> emptySet = new HashSet<>();
        Group group = mock(Group.class);
        Debugger debugger = mock(Debugger.class);

        // Test
        entityCollection = new EntityCollection(group, emptySet, injector);

        // Verify
        Assertions.assertEquals(0, entityCollection.getStatistics().getUpdatables());
        Assertions.assertEquals(0, entityCollection.getStatistics().getStatics());
    }

    @Test
    void initWithStaticEntityIsAddedToTheGroup() {
        // Setup
        Entity entity = mock(Entity.class);
        Node node = mock(Node.class);
        when(entity.getGameNode()).thenReturn(node);

        Set<Entity> set = new HashSet<>();
        set.add(entity);

        Group group = mock(Group.class);
        ObservableList<Node> children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        // Test
        entityCollection = new EntityCollection(group, set, injector);
        entityCollection.update(0);

        // Verify
        verify(children).add(node);
    }

    @Test
    void initWithDynamicEntityIsAddedToTheGroupAndUpdateIsCalled() {
        // Setup
        UpdatableEntity updatableEntity = mock(UpdatableEntity.class);
        Node node = mock(Node.class);
        when(updatableEntity.getGameNode()).thenReturn(node);

        Set<Entity> dynamicSet = new HashSet<>();
        dynamicSet.add(updatableEntity);

        Group group = mock(Group.class);
        ObservableList<Node> children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        // Test
        entityCollection = new EntityCollection(group, dynamicSet, injector);
        entityCollection.update(0);

        // Verify
        verify(children).add(node);
        verify(updatableEntity).update(0);
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
        Set<Entity> entitySet = new HashSet<>();
        entitySet.add(keyListeningEntity);

        Set<KeyCode> keycodes = new HashSet<>();

        // Test
        entityCollection = new EntityCollection(group, entitySet, injector);
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
        Set<Entity> entitySet = new HashSet<>();
        entitySet.add(keyListeningEntity);

        Set<KeyCode> keycodes = new HashSet<>();
        keycodes.add(KeyCode.Y);
        keycodes.add(KeyCode.A);
        keycodes.add(KeyCode.E);
        keycodes.add(KeyCode.G);
        keycodes.add(KeyCode.E);
        keycodes.add(KeyCode.R);

        // Test
        entityCollection = new EntityCollection(group, entitySet, injector);
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
    public void init() {
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
    public void init() {
        // Not required here.
    }
}
