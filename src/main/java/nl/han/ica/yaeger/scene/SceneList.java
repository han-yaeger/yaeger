package nl.han.ica.yaeger.scene;

import java.util.HashMap;

/**
 * Een {@code SceneList} encapsuleert al het gedrag van de geimplementeerde {@link YaegerScene}s.
 */
public class SceneList extends HashMap<SceneType, YaegerScene> {

    private YaegerScene activeScene;

    /**
     * Laad de {@link YaegerScene} van het gegeven type.
     *
     * @param type Het {@link SceneType} dat geladen moet worden.
     */
    public void loadScene(SceneType type) {

    }
}
