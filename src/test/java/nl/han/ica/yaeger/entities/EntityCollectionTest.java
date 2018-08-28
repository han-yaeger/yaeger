package nl.han.ica.yaeger.entities;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Node;
import nl.han.ica.yaeger.entities.events.EventTypes;
import nl.han.ica.yaeger.entities.interfaces.Updatable;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.*;

class EntityCollectionTest {

    private EntityCollection entityCollection;

    @Test
    void initWithANullInitialSetsGivesAnEmptyEntityCollection() {
        // Setup
        Group group = Mockito.mock(Group.class);

        // Test
        entityCollection = new EntityCollection(group, null);

        // Verify
        Assertions.assertEquals(0, entityCollection.getNumberOfDynamicEntities());
        Assertions.assertEquals(0, entityCollection.getNumberOfStaticEntities());
    }

    @Test
    void initWithAnEmptyInitialSetsGivesAnEmptyEntityCollection() {
        // Setup
        Set<Entity> emptySet = new HashSet<>();
        Group group = Mockito.mock(Group.class);

        // Test
        entityCollection = new EntityCollection(group, emptySet);

        // Verify
        Assertions.assertEquals(0, entityCollection.getNumberOfDynamicEntities());
        Assertions.assertEquals(0, entityCollection.getNumberOfStaticEntities());
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
