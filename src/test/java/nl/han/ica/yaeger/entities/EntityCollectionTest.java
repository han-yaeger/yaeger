package nl.han.ica.yaeger.entities;

import javafx.scene.Group;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

class EntityCollectionTest {

    private EntityCollection entityCollection;

    @BeforeEach
    void setup() {
        entityCollection = new EntityCollection();
    }

    @Test
    void initWithANullInitialSetsGivesAnEmptyEntitiyCollection() {
        // Setup
        Group group = Mockito.mock(Group.class);

        // Test
        entityCollection.init(group, null);

        // Verify
        Assertions.assertEquals(0, entityCollection.getNumberOfDynamicEntities());
        Assertions.assertEquals(0, entityCollection.getNumberOfStaticEntities());
    }

    @Test
    public void initWithAnEmptyInitialSetsGivesAnEmptyEntitiyCollection() {
        // Setup
        Set<Entity> emptySet = new HashSet<>();
        Group group = Mockito.mock(Group.class);

        // Test
        entityCollection.init(group, emptySet);

        // Verify
        Assertions.assertEquals(0, entityCollection.getNumberOfDynamicEntities());
        Assertions.assertEquals(0, entityCollection.getNumberOfStaticEntities());
    }
}
