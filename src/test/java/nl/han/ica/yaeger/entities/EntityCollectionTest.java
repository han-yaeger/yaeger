package nl.han.ica.yaeger.entities;

import javafx.scene.Group;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

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
}
