package nl.han.ica.yaeger.engine.exceptions;

import nl.han.ica.yaeger.engine.scenes.SceneType;
import nl.han.ica.yaeger.engine.scenes.impl.StaticScene;

/**
 * A {@code YaegerResourceNotAvailableException} is thrown when a {@code Resource} is requested, but not available on
 * the classpath.
 */
public class YaegerResourceNotAvailableException extends RuntimeException {

    /**
     * Instantiate a new {@code YaegerResourceNotAvailableException} for the given {@code resource}.
     *
     * @param resource the resource that is requested.
     */
    public YaegerResourceNotAvailableException(String resource) {

        super("Resource " + resource + " can not be found. Ensure that it is placed in resource/ folder.");
    }
}
