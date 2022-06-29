package com.github.hanyaeger.core.factories;

import com.github.hanyaeger.api.Size;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * A {@link SceneFactory} can be used to create instances of {@link Scene}.
 */
public class SceneFactory {

    /**
     * Create a {@link Scene}.
     *
     * @param parent the {@link Parent} for which a {@link Scene} must be created
     * @return an instance of {@link Scene}
     */
    public Scene create(final Parent parent) {
        return new Scene(parent);
    }

    /**
     * Create an empty {@link Scene} with the given {@link Size}
     *
     * @param size the requested {@link Size} of the {@link Scene}
     * @return an instance of {@link Scene} of the requested {@link Size}
     */
    public Scene createEmptyForSize(final Size size) {
        return new Scene(new Group(), size.width(), size.height());
    }
}
