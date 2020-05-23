package com.github.hanyaeger.api.engine;

import com.github.hanyaeger.api.guice.factories.SceneCollectionFactory;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.stage.Stage;
import com.github.hanyaeger.api.engine.scenes.SceneCollection;
import com.github.hanyaeger.api.engine.scenes.YaegerScene;

/**
 * A {@link YaegerStage} encapsulates an JavaFX {@link Stage}. It defines the window
 * that is being used and contains the {@link SceneCollection} that contains all instances
 * of {@link YaegerScene} that are part of the {@link YaegerApplication}.
 */
public class YaegerStage implements Initializable {

    private Size size = YaegerApplication.DEFAULT_GAME_DIMENSIONS;

    private YaegerApplication yaegerApplication;
    private Stage stage;
    private SceneCollectionFactory sceneCollectionFactory;
    private SceneCollection sceneCollection;

    YaegerStage(final YaegerApplication yaegerApplication, final Stage stage) {
        this.yaegerApplication = yaegerApplication;
        this.stage = stage;
    }

    /**
     * Set the {@link Size}, being the {@code width} and {@code height} of the game.
     *
     * @param size A {@link Size} object that encapsulates the {@code width} and {@code height} of the game
     */
    protected void setSize(final Size size) {
        this.size = size;
    }

    @Override
    public void init(final Injector injector) {
        stage.setResizable(false);
        sceneCollection = sceneCollectionFactory.create(stage);
        injector.injectMembers(sceneCollection);
        sceneCollection.init(injector);

        yaegerApplication.initializeGame();
        stage.setWidth(size.getWidth());
        stage.setHeight(size.getHeight());

        yaegerApplication.setupScenes();
        sceneCollection.postSetupScenes();

        stage.show();
    }

    /**
     * Stop and close the Game.
     */
    protected void quit() {
        stage.close();
    }

    /**
     * Set the title of the Game.
     *
     * @param title A {@link String} containing the title of the Game
     */
    protected void setTitle(final String title) {
        stage.setTitle(title);
    }

    /**
     * Add a {@link YaegerScene} to the {@link YaegerStage}.
     *
     * @param id The number as an {@code int} that identifies the {@link YaegerScene}. By calling the method
     *               {@link #setActiveScene(int)} the added {@link YaegerScene} can be set as the active one.
     * @param scene  The {@link YaegerScene} that should be added.
     */
    protected void addScene(final int id, final YaegerScene scene) {
        sceneCollection.addScene(id, scene);
    }

    /**
     * Set the current active {@link YaegerScene}.
     *
     * @param id The {@link Integer} identifying the {@link YaegerScene}
     */
    protected void setActiveScene(final int id) {
        sceneCollection.setActive(id);
    }

    @Inject
    public void setSceneCollectionFactory(final SceneCollectionFactory sceneCollectionFactory) {
        this.sceneCollectionFactory = sceneCollectionFactory;
    }
}
