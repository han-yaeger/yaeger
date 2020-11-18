package com.github.hanyaeger.api.engine.exceptions;

/**
 * An {@link FailedToInstantiateEntityException} should be thrown if Yaeger failes to instantiate an
 * {@link com.github.hanyaeger.api.engine.entities.entity.YaegerEntity} through reflection.
 */
public class FailedToInstantiateEntityException extends RuntimeException {

    /**
     * Create a new instance of an {@link FailedToInstantiateEntityException}.
     *
     * @param message a message that explains the cause of this exception
     * @param cause   the {@link Throwable} that is the cause that this {@link FailedToInstantiateEntityException}
     *                is thrown
     */
    public FailedToInstantiateEntityException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
