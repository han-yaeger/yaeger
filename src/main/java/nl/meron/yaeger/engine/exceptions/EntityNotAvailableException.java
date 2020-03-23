package nl.meron.yaeger.engine.exceptions;

/**
 * A {@link EntityNotAvailableException} should be thrown if an {@link nl.meron.yaeger.engine.entities.entity.Entity}
 * is requested that is not available. Most likely place for this to occur is an {@link nl.meron.yaeger.engine.entities.tilemap.TileMap}.
 */
public class EntityNotAvailableException extends RuntimeException {

    /**
     * Create a new {@link EntityNotAvailableException} with the given message.
     *
     * @param message The message that should be shown when the exception is thrown.
     */
    public EntityNotAvailableException(final String message) {
        super(message);
    }
}
