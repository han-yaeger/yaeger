package nl.meron.yaeger.guice.factories;

import javafx.stage.Stage;
import nl.meron.yaeger.engine.scenes.SceneCollection;

/**
 * A {@link SceneCollectionFactory} can be used to create instances of {@link nl.meron.yaeger.engine.scenes.SceneCollection}.
 */
public class SceneCollectionFactory {

    /**
     * Create a {@link SceneCollection}.
     *
     * @param stage The {@link Stage} to which all instances of {@link javafx.scene.Scene} should be added.
     * @return An instance of {@link SceneCollection}
     */
    public SceneCollection create(final Stage stage) {
        return new SceneCollection(stage);
    }
}
