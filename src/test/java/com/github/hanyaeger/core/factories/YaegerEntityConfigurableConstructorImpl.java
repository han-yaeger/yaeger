package com.github.hanyaeger.core.factories;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import javafx.scene.Node;

import java.util.Optional;

class YaegerEntityConfigurableConstructorImpl extends YaegerEntity {

    boolean configurableConstructorCalled = false;

    YaegerEntityConfigurableConstructorImpl(final Coordinate2D location, final Size size) {
        super(location);
    }

    YaegerEntityConfigurableConstructorImpl(final Coordinate2D location, final Size size, String spriteName) {
        super(location);
        configurableConstructorCalled = true;
    }

    @Override
    public Optional<? extends Node> getNode() {
        return Optional.empty();
    }
}
