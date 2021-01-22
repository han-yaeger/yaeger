package com.github.hanyaeger.api.engine.entities.tilemap;

import com.github.hanyaeger.api.engine.Size;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.sprite.SpriteEntity;
import javafx.scene.Node;

import java.util.Optional;

class YaegerEntityValidConstructorImpl extends YaegerEntity {

    private boolean preserveAspectRatio = true;

    public YaegerEntityValidConstructorImpl(Coordinate2D location, Size size) {
        super(location);
    }

    @Override
    public Optional<? extends Node> getNode() {
        return Optional.empty();
    }
}
