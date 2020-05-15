package com.github.hanyaeger.api.engine.scenes.splash.entities;

import com.github.hanyaeger.api.engine.Size;
import com.github.hanyaeger.api.engine.entities.entity.sprite.SpriteEntity;
import com.github.hanyaeger.api.engine.entities.entity.Location;

/**
 * {@link nl.han.yaeger.engine.entities.entity.Entity} that encapsulates the logo.
 */
public class Logo extends SpriteEntity {

    /**
     * Create a new instance of {@link Logo} with the given {@link Location} and {@link Size}.
     *
     * @param location The {@link Location} at which the {@link Logo} should be placed.
     * @param size     The {@link Size} of the {@link Logo}.
     */
    public Logo(final Location location, final Size size) {
        super("images/logo.png", location, size);
    }
}
