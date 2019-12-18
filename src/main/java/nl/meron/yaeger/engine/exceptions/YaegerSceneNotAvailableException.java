package nl.meron.yaeger.engine.exceptions;

import nl.meron.yaeger.engine.scenes.SceneType;
import nl.meron.yaeger.engine.scenes.YaegerScene;
import nl.meron.yaeger.engine.scenes.impl.StaticScene;

/**
 * A {@code YaegerSceneNotAvailableException} is thrown when a  {@link YaegerScene} is
 * requested that is not available.
 */
public class YaegerSceneNotAvailableException extends RuntimeException {

    private final SceneType type;

    /**
     * Instantiate a new {@code YaegerSceneNotAvailableException} with the given {@link SceneType}.
     *
     * @param type De type van de {@link StaticScene}
     */
    public YaegerSceneNotAvailableException(final SceneType type) {
        super("Scene " + type + " is not available. Ensure the scene is added to the game.");
        this.type = type;
    }

    /**
     * Return the {@link SceneType} that was requested, but not available.
     *
     * @return the {@link SceneType} that was requested, but not available
     */
    public SceneType getType() {
        return type;
    }
}
