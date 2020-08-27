package com.github.hanyaeger.api.engine.scenes.splash.entities;

import com.github.hanyaeger.api.engine.Size;
import com.github.hanyaeger.api.engine.entities.entity.sprite.SpriteEntity;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;

/**
 * A {@link SpriteEntity} that encapsulates the logo.
 */
public class Logo extends SpriteEntity {

    /**
     * Create a new instance of {@link Logo} with the given {@link Coordinate2D} and {@link Size}.
     *
     * @param location The {@link Coordinate2D} at which the {@link Logo} should be placed.
     * @param size     The {@link Size} of the {@link Logo}.
     */
    public Logo(final Coordinate2D location, final Size size) {
        super("yaegerimages/logo.png", location, size);
    }
}
