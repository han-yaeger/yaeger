package nl.han.ica.yaeger.entities;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
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
    void initWithDynamicEntitiesAreUpdatedWhenUpdateIsCalled() {
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
