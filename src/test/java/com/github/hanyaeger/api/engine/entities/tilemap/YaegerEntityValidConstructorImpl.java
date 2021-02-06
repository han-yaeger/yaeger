package com.github.hanyaeger.api.engine.entities.tilemap;

import com.github.hanyaeger.api.engine.Size;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
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
