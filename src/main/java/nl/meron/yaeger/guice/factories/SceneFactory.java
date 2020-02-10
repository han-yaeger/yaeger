package nl.meron.yaeger.guice.factories;

import javafx.scene.Group;
import javafx.scene.Scene;

/**
 * A {@link SceneFactory} can be used to create instances of {@link Scene}.
 */
public class SceneFactory {
    /**
     * Create a {@link Scene}.
     *
     * @param group The {@link Group} for which a {@link Scene} must be created.
     * @return an instance of {@link Scene}
     */
    public Scene create(final Group group) {
        return new Scene(group);
    }
}
