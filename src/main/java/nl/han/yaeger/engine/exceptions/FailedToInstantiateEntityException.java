package nl.han.yaeger.engine.exceptions;

/**
 * An {@link FailedToInstantiateEntityException} should be thrown if Yaeger failes to instantiate an
 * {@link nl.han.yaeger.engine.entities.entity.Entity} through reflection.
 */
public class FailedToInstantiateEntityException extends RuntimeException {

    /**
     * Create a new instance of an {@link FailedToInstantiateEntityException}.
     *
     * @param message A message that explains the cause of this exception.
     * @param cause   The {@link Throwable} that is the cause that this {@link FailedToInstantiateEntityException}
     *                is thrown.
     */
    public FailedToInstantiateEntityException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
