package nl.han.ica.yaeger.entities;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import nl.han.ica.yaeger.debug.Debugger;
import nl.han.ica.yaeger.entities.interfaces.KeyListener;
import nl.han.ica.yaeger.entities.interfaces.Updatable;
import nl.han.ica.yaeger.scene.SceneStatistics;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.*;

class EntityCollectionTest {

    private EntityCollection entityCollection;

    @Test
    void initWithANullInitialSetsGivesAnEmptyStaticEntityCollection() {
        // Setup
        Group group = Mockito.mock(Group.class);
        Debugger debugger = Mockito.mock(Debugger.class);

        // Test
        entityCollection = new EntityCollection(group, null);
        entityCollection.addStatisticsObserver(debugger);


        // Verify
        Assertions.assertEquals(0, entityCollection.getStatistics().getStatics());
    }

    @Test
    void initWithAnEmptyInitialSetsGivesAnEmptyDynamicEntityCollection() {
        // Setup
        Set<Entity> emptySet = new HashSet<>();
        Group group = Mockito.mock(Group.class);
        Debugger debugger = Mockito.mock(Debugger.class);

        // Test
        entityCollection = new EntityCollection(group, emptySet);

        // Verify
        Assertions.assertEquals(0, entityCollection.getStatistics().getUpdatables());
        Assertions.assertEquals(0, entityCollection.getStatistics().getStatics());
    }

    @Test
    void initWithStaticEntityIsAddedToTheGroup() {
        // Setup
        Entity entity = Mockito.mock(Entity.class);
        Node node = Mockito.mock(Node.class);
        Mockito.when(entity.getGameNode()).thenReturn(node);

        Set<Entity> set = new HashSet<>();
        set.add(entity);

        Group group = Mockito.mock(Group.class);
        ObservableList<Node> children = Mockito.mock(ObservableList.class);
        Mockito.when(group.getChildren()).thenReturn(children);

        var debugger = Mockito.mock(Debugger.class);

        // Test
        entityCollection = new EntityCollection(group, set);
        entityCollection.update();

        // Verify
        Mockito.verify(children).add(node);
    }

    @Test
    void initWithDynamicEntityIsAddedToTheGroupAndUpdateIsCalled() {
        // Setup
        UpdatableEntity updatableEntity = Mockito.mock(UpdatableEntity.class);
        Node node = Mockito.mock(Node.class);
        Mockito.when(updatableEntity.getGameNode()).thenReturn(node);

        Set<Entity> dynamicSet = new HashSet<>();
        dynamicSet.add(updatableEntity);

        Group group = Mockito.mock(Group.class);
        ObservableList<Node> children = Mockito.mock(ObservableList.class);
        Mockito.when(group.getChildren()).thenReturn(children);

        // Test
        entityCollection = new EntityCollection(group, dynamicSet);
        entityCollection.update();

        // Verify
        Mockito.verify(children).add(node);
        Mockito.verify(updatableEntity).update();
    }

    @Test
    void keyListeningEntityGetsNotifiedWhenKeyInputChangeAndSetIsEmpty() {
        // Setup
        KeyListeningEntity keyListeningEntity = Mockito.mock(KeyListeningEntity.class);
        Node node = Mockito.mock(Node.class);
        Mockito.when(keyListeningEntity.getGameNode()).thenReturn(node);

        Group group = Mockito.mock(Group.class);
        ObservableList<Node> children = Mockito.mock(ObservableList.class);
        Mockito.when(group.getChildren()).thenReturn(children);
        Set<Entity> entitySet = new HashSet<>();
        entitySet.add(keyListeningEntity);

        Set<KeyCode> keycodes = new HashSet<>();

        // Test
        entityCollection = new EntityCollection(group, entitySet);
        entityCollection.notifyGameObjectsOfPressedKeys(keycodes);

        // Verify
        Mockito.verify(keyListeningEntity).onPressedKeysChange(keycodes);
    }

    @Test
    void keyListeningEntityGetsNotifiedWhenKeyInputChangeAndSetIsFilled() {
        // Setup
        KeyListeningEntity keyListeningEntity = Mockito.mock(KeyListeningEntity.class);
        Node node = Mockito.mock(Node.class);
        Mockito.when(keyListeningEntity.getGameNode()).thenReturn(node);

        Group group = Mockito.mock(Group.class);
        ObservableList<Node> children = Mockito.mock(ObservableList.class);
        Mockito.when(group.getChildren()).thenReturn(children);
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
        entityCollection = new EntityCollection(group, entitySet);
        entityCollection.notifyGameObjectsOfPressedKeys(keycodes);

        // Verify
        Mockito.verify(keyListeningEntity).onPressedKeysChange(keycodes);
    }
}

class UpdatableEntity extends Entity implements Updatable {

    @Override
    public Node getGameNode() {
        return null;
    }

    @Override
    public void update() {
        // Not required here.
    }
}

class KeyListeningEntity extends Entity implements KeyListener {

    @Override
    public Node getGameNode() {
        return null;
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
        // Not required here.
    }
}
