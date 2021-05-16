package com.github.hanyaeger.core.exceptions;

/**
 * When an error occurs within Yaeger, the error of thrown Exception will be encapsulated
 * by a {@link YaegerEngineException}.
 */
public class YaegerEngineException extends RuntimeException {

    /**
     * Create a new {@link YaegerEngineException} with the given message. In this case, the
     * {@link YaegerEngineException} is not caused by a caught {@link Exception}, but by a known
     * situation. Therefore, the message should clarify its cause.
     *
     * @param message the cause of this exception as a {@link String}
     */
    public YaegerEngineException(final String message) {
        super(message);
    }

    /**
     * Create a new {@link YaegerEngineException} that was caused by a caught {@link Exception}.
     *
     * @param e the {@link Exception} that caused this {@link YaegerEngineException}
     */
    public YaegerEngineException(final Exception e) {
        super(e);
    }
}
