package com.github.hanyaeger.api.engine.entities.entity;

import javafx.scene.Node;

import java.util.Optional;

/**
 * Being a {@link NodeProvider} guarantees that a {@link Node}, if set, is accessible.
 */
public interface NodeProvider {

    /**
     * Return an {@link Optional} of the {@link Node} that is related to this {@link YaegerEntity}.
     *
     * @return an {@link Optional} of the {@link Node} that is related to this {@link YaegerEntity}
     */
    Optional<Node> getGameNode();
}
