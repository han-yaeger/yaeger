package nl.meron.yaeger.module.factories;

import javafx.scene.Group;
import nl.meron.yaeger.engine.entities.EntityCollection;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.entities.EntityCollection;
import nl.meron.yaeger.engine.entities.entity.Entity;

/**
 * A {@link EntityCollectionFactory} can be used to create instances of {@link EntityCollection}.
 */
public class EntityCollectionFactory {

    /**
     * Create a {@link EntityCollection}.
     *
     * @param group The {@link Group} to which all instances of {@link Entity}s should be added.
     * @return An instance of {@link EntityCollection}
     */
    public EntityCollection create(Group group) {
        return new EntityCollection(group);
    }
}
