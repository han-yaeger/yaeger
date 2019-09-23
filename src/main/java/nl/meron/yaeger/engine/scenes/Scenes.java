package nl.meron.yaeger.engine.scenes;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.stage.Stage;
import nl.meron.yaeger.engine.exceptions.YaegerSceneNotAvailableException;
import nl.meron.yaeger.module.YaegerModule;
import nl.meron.yaeger.engine.exceptions.YaegerSceneNotAvailableException;
import nl.meron.yaeger.module.YaegerModule;

import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * A {@link Scenes} contains all instances of {@link YaegerScene} that are part of the Game.
 */
public class Scenes extends LinkedHashMap<SceneType, YaegerScene> {

    private final transient Stage stage;
    private transient YaegerScene activeScene;
    private transient Injector injector;

    public Scenes(Stage stage) {
        this.injector = Guice.createInjector(new YaegerModule());
        this.stage = stage;
    }

    /**
     * Voeg een {@link YaegerScene} toe aan de lijst van {@link Scenes}. Een {@link YaegerScene} is altijd gekoppeld
     * aan een {@link SceneType}. Van ieder {@link SceneType} kan er maar één worden toegevoegd aan de lijst van
     * {@code scenes}.
     *
     * @param type  De {@link SceneType} van de {@link YaegerScene} die moet worden toegevoegd.
     * @param scene De {@link YaegerScene} die moet worden toegevoegd.
     */
    public void addScene(SceneType type, YaegerScene scene) {
        put(type, scene);

        scene.init(injector);

        if (size() == 1) {
            activate(scene);
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
     * Set the {@link YaegerScene} of the given {@link SceneType}. Dit is de {@code scenes} die getoond wordt op het scherm en waarvan,
     * indien beschikbaar, de {@code Gameloop} en {@code Eventlisteners} hun werk doen.
     *
     * @param type the {@link SceneType} of the {@link YaegerScene} to set as the active scene
     */
    public void setActive(SceneType type) {

        var requestedScene = get(type);

        if (requestedScene == null) {
            throw new YaegerSceneNotAvailableException(type);
        }

        if (activeScene != null) {
            activeScene.destroy();
        }

        activate(requestedScene);
    }

    private void activate(YaegerScene scene) {
        injector.injectMembers(scene);
        scene.configure();
        scene.setupScene();
        scene.setupEntities();
        activeScene = scene;
        setActiveSceneOnStage();
        scene.postActivation();
    }

    private void setActiveSceneOnStage() {
        stage.setScene(activeScene.getScene());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Scenes scenes = (Scenes) o;
        return Objects.equals(stage, scenes.stage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stage);
    }
}
