package nl.han.yaeger.engine.scenes.splash.entities;

import nl.han.yaeger.engine.Size;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.entity.sprite.SpriteEntity;

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
        super("logo.png", location, size);
    }
}
