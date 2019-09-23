package nl.meron.yaeger.engine.exceptions;

import nl.meron.yaeger.engine.scenes.SceneType;
import nl.meron.yaeger.engine.scenes.YaegerScene;
import nl.meron.yaeger.engine.scenes.impl.StaticScene;

/**
 * A {@code YaegerSceneNotAvailableException} is thrown when a  {@link YaegerScene} is
 * requested that is not available.
 */
public class YaegerSceneNotAvailableException extends RuntimeException {

    /**
     * Instantiate a new {@code YaegerSceneNotAvailableException} with the given {@link SceneType}.
     *
     * @param type De type van de {@link StaticScene}
     */
    public YaegerSceneNotAvailableException(SceneType type) {

        super("Scene " + type + " is not available. Ensure the scene is added to the game.");
    }
}
