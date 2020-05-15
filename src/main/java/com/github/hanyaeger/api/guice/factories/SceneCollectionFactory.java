package com.github.hanyaeger.api.guice.factories;

import javafx.stage.Stage;
import com.github.hanyaeger.api.engine.scenes.SceneCollection;

/**
 * A {@link SceneCollectionFactory} can be used to create instances of {@link SceneCollection}.
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
