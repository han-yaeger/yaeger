package nl.meron.yaeger.engine.exceptions;

import nl.meron.yaeger.engine.scenes.YaegerScene;
import nl.meron.yaeger.engine.scenes.StaticScene;

/**
 * A {@code YaegerSceneNotAvailableException} is thrown when a  {@link YaegerScene} is
 * requested that is not available.
 */
public class YaegerSceneNotAvailableException extends RuntimeException {

    private final Integer number;

    /**
     * Instantiate a new {@code YaegerSceneNotAvailableException} with the given {@link Integer}.
     *
     * @param number De type van de {@link StaticScene}
     */
    public YaegerSceneNotAvailableException(final Integer number) {
        super("Scene " + number + " is not available. Ensure the scene is added to the game.");
        this.number = number;
    }

    /**
     * Return the {@link Integer} that was requested, but not available.
     *
     * @return the {@link Integer} that was requested, but not available
     */
    public Integer getType() {
        return number;
    }
}
