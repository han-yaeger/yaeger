package com.github.hanyaeger.api.engine.exceptions;

/**
 * An {@link InvalidConstructorException} should be thrown if an object with an invalid
 * constructor is instantiated through relfection.
 */
public class InvalidConstructorException extends RuntimeException {

    /**
     * Create a new instance of an {@link InvalidConstructorException}.
     *
     * @param message A message that explains the cause of this exception.
     * @param cause   The {@link Throwable} that is the cause that this {@link InvalidConstructorException}
     *                is thrown.
     */
    public InvalidConstructorException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
