package nl.meron.yaeger.engine.scenes;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.stage.Stage;
import nl.meron.yaeger.engine.annotations.AnnotationProcessor;
import nl.meron.yaeger.engine.exceptions.YaegerSceneNotAvailableException;

import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * A {@link SceneCollection} contains all instances of {@link YaegerScene} that are part of a Game. It is
 * responsible for initializing all the scenes and setting the current {@link YaegerScene}.
 */
public class SceneCollection extends LinkedHashMap<Integer, YaegerScene> {

    private final transient Stage stage;
    private final transient Injector injector;
    private transient AnnotationProcessor annotationProcessor;
    private transient YaegerScene activeScene;

    public SceneCollection(final Stage stage, final Injector injector) {
        this.stage = stage;
        this.injector = injector;
    }

    /**
     * Add a {@link YaegerScene} to the collection of {@link SceneCollection}. A {@link YaegerScene} uses a number ({@link Integer})
     * to be identified. Each number can be only used ones..
     *
     * @param number The {@link Integer} identifying the {@link YaegerScene}
     * @param scene  The {@link YaegerScene} to be added
     */
    public void addScene(final int number, final YaegerScene scene) {
        put(number, scene);
        scene.init(injector);
        scene.setStage(stage);

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
        annotationProcessor.configureUpdateDelegators(scene);
        annotationProcessor.invokeInitializers(scene);
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
        SceneCollection sceneCollection = (SceneCollection) o;
        return Objects.equals(stage, sceneCollection.stage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stage);
    }

    @Inject
    public void setAnnotationProcessor(AnnotationProcessor annotationProcessor) {
        this.annotationProcessor = annotationProcessor;
    }
}
