package nl.han.ica.yaeger.exceptions;

import nl.han.ica.yaeger.scene.SceneType;
import nl.han.ica.yaeger.scene.StaticScene;

/**
 * Een {@code YaegerSceneNotAvailableException} wordt gegooit waneer een {@link StaticScene} wordt opgevraagd die niet beschikbaar
 * is.
 */
public class YaegerSceneNotAvailableException extends RuntimeException {

    /**
     * Maak een nieuwe {@code YaegerSceneNotAvailableException} met de gegeven {@code type}.
     *
     * @param type De type van de {@link StaticScene}
     */
    public YaegerSceneNotAvailableException(SceneType type) {

        super("StaticScene " + type + " kan niet worden gevonden. Zorg ervoor dat deze Scene is teogevoegd aan de Engine");
    }
}
