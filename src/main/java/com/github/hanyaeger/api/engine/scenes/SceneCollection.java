package com.github.hanyaeger.api.engine.scenes;

import com.github.hanyaeger.api.engine.Initializable;
import com.github.hanyaeger.api.engine.annotations.AnnotationProcessor;
import com.github.hanyaeger.api.engine.exceptions.YaegerSceneNotAvailableException;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.stage.Stage;
import com.github.hanyaeger.api.engine.scenes.splash.SplashScreenFactory;

import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * A {@link SceneCollection} contains all instances of {@link YaegerScene} that are part of a Game. It is
 * responsible for initializing all the scenes and setting the current {@link YaegerScene}.
 */
public class SceneCollection extends LinkedHashMap<Integer, YaegerScene> implements Initializable {

    private final transient Stage stage;
    private transient Injector injector;
    private transient AnnotationProcessor annotationProcessor;
    private transient YaegerScene activeScene;
    private int firstScene;
    private SplashScreenFactory splashScreenFactory;
    private boolean finishedSplashScreen = false;

    public SceneCollection(final Stage stage) {
        this.stage = stage;
    }

    /**
     * Add a {@link YaegerScene} to the collection of {@link SceneCollection}. A {@link YaegerScene} uses a number ({@link Integer})
     * to be identified. Each number can be only used one time.
     *
     * @param id    The {@link Integer} identifying the {@link YaegerScene}
     * @param scene The {@link YaegerScene} to be added
     */
    public void addScene(final int id, final YaegerScene scene) {
        put(id, scene);
        scene.init(injector);
        scene.setStage(stage);

        if (size() == 1) {
            firstScene = id;
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
     * @param id The {@link Integer} identifying the {@link YaegerScene}
     */
    public void setActive(final int id) {
        if (!finishedSplashScreen) {
            firstScene = id;
        }

        var requestedScene = get(id);

        if (requestedScene == null) {
            throw new YaegerSceneNotAvailableException(id);
        }

        if (activeScene != null) {
            activeScene.destroy();
        }

        activate(requestedScene);
    }

    private void activate(final YaegerScene scene) {
        injector.injectMembers(scene);
        annotationProcessor.configureUpdateDelegators(scene);
        annotationProcessor.invokeActivators(scene);
        scene.activate();

        activeScene = scene;
        setActiveSceneOnStage();

        annotationProcessor.invokePostActivators(scene);
        scene.postActivate();
    }

    private void setActiveSceneOnStage() {
        stage.setScene(activeScene.getScene());
    }

    @Override
    public void init(final Injector injector) {
        this.injector = injector;
    }

    public void postSetupScenes() {
        addSplashScreen();
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

    private void addSplashScreen() {
        var splash = splashScreenFactory.create(() -> {
            this.finishedSplashScreen = true;
            setActive(firstScene);
        });
        splash.init(injector);
        splash.setStage(stage);
        activate(splash);
    }

    @Inject
    public void setAnnotationProcessor(final AnnotationProcessor annotationProcessor) {
        this.annotationProcessor = annotationProcessor;
    }

    @Inject
    public void setSplashScreenFactory(final SplashScreenFactory splashScreenFactory) {
        this.splashScreenFactory = splashScreenFactory;
    }
}
