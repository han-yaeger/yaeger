package com.github.hanyaeger.core.entities;

import javafx.scene.Node;

import java.util.Optional;

/**
 * Being a {@link GameNode} guarantees that a {@link Node}, if set, is accessible.
 */
public interface GameNode {

    /**
     * Return an {@link Optional} of the {@link Node} that is related to this {@link GameNode}.
     *
     * @return an {@link Optional} of the {@link Node} that is related to this {@link GameNode}
     */
    Optional<? extends Node> getNode();
}
