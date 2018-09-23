package nl.han.ica.yaeger.scene;

import javafx.stage.Stage;
import nl.han.ica.yaeger.exceptions.YaegerSceneNotAvailableException;

import java.util.LinkedHashMap;

/**
 * Een {@code Scenes} encapsuleert al het gedrag van de geimplementeerde {@link YaegerScene}.
 */
public class Scenes extends LinkedHashMap<SceneType, YaegerScene> {

    private final Stage stage;
    private transient YaegerScene activeScene;

    public Scenes(Stage stage) {
        this.stage = stage;
    }

    /**
     * Laad de {@link YaegerScene} van het gegeven type.
     *
     * @param type Het {@link SceneType} dat geladen moet worden.
     */
    public void loadScene(SceneType type) {

    }

    /**
     * Voeg een {@link YaegerScene} toe aan de lijst van {@code scenes}. Een {@link YaegerScene} is altijd gekoppeld
     * aan een {@link SceneType}. Van ieder {@link SceneType} kan er maar één worden toegevoegd aan de lijst van
     * {@code scenes}.
     *
     * @param type  De {@link SceneType} van de {@link YaegerScene} die moet worden toegevoegd.
     * @param scene De {@link YaegerScene} die moet worden toegevoegd.
     */
    public void addScene(SceneType type, YaegerScene scene) {
        put(type, scene);

        if (size() == 1) {
            activeScene = scene;

            scene.setupScene();
            setActiveSceneOnStage();
        }
    }

    /**
     * Return the {@link YaegerScene} currently active.
     *
     * @return The {@link YaegerScene} that is currently active.
     */
    public YaegerScene getActiveScene() {
        return activeScene;
    }

    /**
     * Zet de huidige actieve {@link YaegerScene}. Dit is de {@code scene} die getoond wordt op het scherm en waarvan,
     * indien beschikbaar, de {@code Gameloop} en {@code Eventlisteners} hun werk doen.
     *
     * @param type De enumeratie die de type van de {@link YaegerScene} bevat.
     */
    public void setActive(SceneType type) {

        var requestedScene = get(type);

        if (requestedScene == null) {
            throw new YaegerSceneNotAvailableException(type);
        }

        requestedScene.setupScene();

        if (activeScene != null) {
            activeScene.tearDownScene();
        }

        activeScene = requestedScene;

        setActiveSceneOnStage();
    }

    private void setActiveSceneOnStage() {
        stage.setScene(activeScene.getScene());
    }
}
