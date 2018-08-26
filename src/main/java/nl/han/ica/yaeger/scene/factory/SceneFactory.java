package nl.han.ica.yaeger.scene.factory;

import javafx.scene.Group;
import javafx.scene.Scene;

/**
 * Een factory voor een {@link Scene}.
 */
public class SceneFactory {

    /**
     * Maak een instantie voor een {@link Scene}.
     *
     * @param group De {@link Group} waar een {@link Scene} voor moet worden gemaakt.
     * @return Een instantie voor een {@link Scene}
     */
    public Scene getInstance(Group group) {
        return new Scene(group);
    }
}
