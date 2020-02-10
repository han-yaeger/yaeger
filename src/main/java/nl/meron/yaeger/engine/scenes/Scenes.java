package nl.meron.yaeger.engine.scenes;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.stage.Stage;
import nl.meron.yaeger.engine.exceptions.YaegerSceneNotAvailableException;
import nl.meron.yaeger.guice.YaegerModule;

import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * A {@link Scenes} contains all instances of {@link YaegerScene} that are part of the Game.
 */
public class Scenes extends LinkedHashMap<Integer, YaegerScene> {

    private final transient Stage stage;
    private transient YaegerScene activeScene;
    private transient Injector injector;

    public Scenes(final Stage stage) {
        this.injector = Guice.createInjector(new YaegerModule());
        this.stage = stage;
    }

    /**
     * Add a {@link YaegerScene} to the collection of {@link Scenes}. A {@link YaegerScene} uses a number ({@link Integer})
     * to be identified. Each number can be only used ones..
     *
     * @param number The {@link Integer} identifying the {@link YaegerScene}
     * @param scene  The {@link YaegerScene} to be added
     */
    public void addScene(final int number, final YaegerScene scene) {
        put(number, scene);

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
     * Set the {@link YaegerScene} of the given {@code int}. The set {@link YaegerScene} will be shown and if available
     * its {@code GameLoop} and {@code EventListeners} will be active.
     *
     * @param number The {@link Integer} identifying the {@link YaegerScene}
     */
    public void setActive(final int number) {

        var requestedScene = get(number);

        if (requestedScene == null) {
            throw new YaegerSceneNotAvailableException(number);
        }

        if (activeScene != null) {
            activeScene.destroy();
        }

        activate(requestedScene);
    }

    private void activate(final YaegerScene scene) {
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
    public boolean equals(final Object o) {
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
