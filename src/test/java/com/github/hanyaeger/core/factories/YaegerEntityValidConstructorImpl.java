package com.github.hanyaeger.core.factories;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import javafx.scene.Node;

import java.util.Optional;

class YaegerEntityValidConstructorImpl extends YaegerEntity {

    public YaegerEntityValidConstructorImpl(final Coordinate2D location, final Size size) {
        super(location);
    }

    @Override
    public Optional<? extends Node> getNode() {
        return Optional.empty();
    }
}
