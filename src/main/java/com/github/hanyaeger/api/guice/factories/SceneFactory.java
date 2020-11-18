package com.github.hanyaeger.api.guice.factories;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * A {@link SceneFactory} can be used to create instances of {@link Scene}.
 */
public class SceneFactory {
    /**
     * Create a {@link Scene}.
     *
     * @param pane the {@link Pane} for which a {@link Scene} must be created
     * @return an instance of {@link Scene}
     */
    public Scene create(final Pane pane) {
        return new Scene(pane);
    }
}
