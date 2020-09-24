package com.github.hanyaeger.api.guice.factories;

import com.github.hanyaeger.api.engine.YaegerConfig;
import javafx.stage.Stage;
import com.github.hanyaeger.api.engine.scenes.SceneCollection;

/**
 * A {@link SceneCollectionFactory} can be used to create instances of {@link SceneCollection}.
 */
public class SceneCollectionFactory {

    /**
     * Create a {@link SceneCollection}.
     *
     * @param stage        The {@link Stage} to which all instances of {@link javafx.scene.Scene} should be added.
     * @param yaegerConfig The {@link YaegerConfig} that should be used for creating the {@link SceneCollection}.
     * @return An instance of {@link SceneCollection}
     */
    public SceneCollection create(final Stage stage, final YaegerConfig yaegerConfig) {
        return new SceneCollection(stage, yaegerConfig);
    }
}
