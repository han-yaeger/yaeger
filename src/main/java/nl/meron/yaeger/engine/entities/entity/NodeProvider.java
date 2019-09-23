package nl.meron.yaeger.engine.entities.entity;

import javafx.scene.Node;

/**
 * Being a {@code NodeProvider} guarantees that a {@link Node} can be provided.
 */
@FunctionalInterface
public interface NodeProvider {

    /**
     * Return the {@link Node} that is related to this {@link Entity}.
     *
     * @return Node the {@link Node} that is related to this {@link Entity}
     */
    Node getGameNode();
}
