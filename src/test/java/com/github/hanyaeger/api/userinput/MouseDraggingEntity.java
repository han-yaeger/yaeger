package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import javafx.scene.Node;

import java.util.Optional;

public class MouseDraggingEntity extends YaegerEntity implements MouseDraggedListener {

    private Node node;

    /**
     * Create a new {@link YaegerEntity} on the given {@link Coordinate2D}.
     *
     * @param initialLocation the initial {@link Coordinate2D} of this {@link YaegerEntity}
     */
    protected MouseDraggingEntity(Coordinate2D initialLocation) {
        super(initialLocation);
    }

    @Override
    public void onDragged(Coordinate2D coordinate2D) {

    }

    @Override
    public void onDropped(Coordinate2D coordinate2D) {

    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Optional<? extends Node> getNode() {
        return Optional.of(node);
    }
}
