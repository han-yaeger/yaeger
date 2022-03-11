package com.github.hanyaeger.core;

import com.github.hanyaeger.core.annotations.AnnotationProcessor;
import com.github.hanyaeger.core.exceptions.YaegerSceneNotAvailableException;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.stage.Stage;
import com.github.hanyaeger.core.scenes.splash.SplashScreenFactory;

import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * A {@link SceneCollection} contains all instances of {@link YaegerScene} that are part of a Game. It is
 * responsible for initializing all the scenes and setting the current {@link YaegerScene}.
 */
public class SceneCollection extends LinkedHashMap<Integer, YaegerScene> implements Initializable {

    private final transient Stage stage;
    private final transient YaegerConfig yaegerConfig;
    private transient Injector injector;
    private transient AnnotationProcessor annotationProcessor;
    private transient YaegerScene activeScene;
    private transient int firstScene;
    private transient SplashScreenFactory splashScreenFactory;
    private transient boolean finishedSplashScreen;

    /**
     * Create a new {@link SceneCollection} for the given {@link Stage} and {@link YaegerConfig}.
     *
     * @param stage        the {@link Stage} that should be used
     * @param yaegerConfig the {@link YaegerConfig} that should be used
     */
    public SceneCollection(final Stage stage, final YaegerConfig yaegerConfig) {
        this.stage = stage;
        this.yaegerConfig = yaegerConfig;
        this.finishedSplashScreen = false;
    }

    /**
     * Add a {@link YaegerScene} to the collection of {@link SceneCollection}. A {@link YaegerScene} uses a number ({@link Integer})
     * to be identified. Each number can be only used one time.
     *
     * @param id    the {@code int} identifying the {@link YaegerScene}
     * @param scene the {@link YaegerScene} to be added
     */
    public void addScene(final int id, final YaegerScene scene) {
        put(id, scene);
        scene.init(injector);
        scene.setStage(stage);
        scene.setConfig(yaegerConfig);

        if (size() == 1) {
            firstScene = id;
        }
    }

    /**
     * Return the {@link YaegerScene} currently active.
     *
     * @return the {@link YaegerScene} that is currently active
     */
    public YaegerScene getActiveScene() {
        return activeScene;
    }

    /**
     * Set the {@link YaegerScene} of the given {@code int}. The set {@link YaegerScene} will be shown and if available
     * its {@code GameLoop} and {@code EventListeners} will be active.
     *
     * @param id the {@code int} identifying the {@link YaegerScene}
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
        scene.activate();
        annotationProcessor.invokeActivators(scene);

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

    void postSetupScenes() {
        if (yaegerConfig.showSplash()) {
            addSplashScreen();
        } else {
            activateFirstScene();
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        var sceneCollection = (SceneCollection) o;
        return Objects.equals(stage, sceneCollection.stage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stage);
    }

    private void addSplashScreen() {
        var splash = splashScreenFactory.create(() -> {
            this.finishedSplashScreen = true;
            activateFirstScene();
        });
        splash.setConfig(yaegerConfig);
        splash.init(injector);
        splash.setStage(stage);
        activate(splash);
    }

    private void activateFirstScene() {
        if (get(firstScene) == null) {
            stage.close();
        } else {
            setActive(firstScene);
        }
    }

    /**
     * Set the {@link AnnotationProcessor} to be used.
     *
     * @param annotationProcessor the {@link AnnotationProcessor}
     */
    @Inject
    public void setAnnotationProcessor(final AnnotationProcessor annotationProcessor) {
        this.annotationProcessor = annotationProcessor;
    }

    /**
     * Set the {@link SplashScreenFactory} to be used.
     *
     * @param splashScreenFactory the {@link SplashScreenFactory}
     */
    @Inject
    public void setSplashScreenFactory(final SplashScreenFactory splashScreenFactory) {
        this.splashScreenFactory = splashScreenFactory;
    }
}
