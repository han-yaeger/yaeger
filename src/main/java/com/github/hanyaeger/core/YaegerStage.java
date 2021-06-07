package com.github.hanyaeger.core;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.YaegerGame;
import com.github.hanyaeger.core.factories.SceneCollectionFactory;
import com.github.hanyaeger.core.factories.SceneFactory;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.github.hanyaeger.api.scenes.YaegerScene;

/**
 * A {@link YaegerStage} encapsulates an JavaFX {@link Stage}. It defines the window
 * that is being used and contains the {@link SceneCollection} that contains all instances
 * of {@link YaegerScene} that are part of the {@link YaegerGame}.
 */
public class YaegerStage implements Initializable {

    private Size size = YaegerGame.DEFAULT_GAME_DIMENSIONS;

    private final YaegerGame yaegerGame;
    private final Stage stage;
    private final YaegerConfig yaegerConfig;
    private SceneFactory sceneFactory;
    private SceneCollectionFactory sceneCollectionFactory;
    private SceneCollection sceneCollection;

    /**
     * Create a new {@code YaegerStage} with the given parameters. A {@code YaegerStage} encapsulates a {@link Stage},
     * which is the representation of the window. The {@link Scene}, which is encapsulated by the {@link YaegerScene}
     * will then represent the content of the window.
     *
     * @param yaegerGame   the {@link YaegerGame} for which this {@code YaegerStage is created}
     * @param stage        the {@link Stage} that should be encapsulated. This {@link Stage} is created by JavaFX whenever a
     *                     JavaFX is created
     * @param yaegerConfig the {@link YaegerConfig} that contains runtime configurations for this {@link YaegerGame}
     */
    public YaegerStage(final YaegerGame yaegerGame, final Stage stage, final YaegerConfig yaegerConfig) {
        this.yaegerGame = yaegerGame;
        this.stage = stage;
        this.yaegerConfig = yaegerConfig;
    }

    /**
     * Set the {@link Size}, being the {@code width} and {@code height} of the game.
     *
     * @param size a {@link Size} object that encapsulates the {@code width} and {@code height} of the game
     */
    public void setSize(final Size size) {
        this.size = size;
    }

    @Override
    public void init(final Injector injector) {
        stage.setResizable(false);
        sceneCollection = sceneCollectionFactory.create(stage, yaegerConfig);
        injector.injectMembers(sceneCollection);
        sceneCollection.init(injector);

        yaegerGame.setupGame();

        stage.setScene(sceneFactory.createEmptyForSize(size));
        stage.show();

        stage.setWidth(stage.getWidth());
        stage.setHeight(stage.getHeight());

        yaegerGame.setupScenes();
        sceneCollection.postSetupScenes();

        stage.show();
    }

    /**
     * Stop and close the Game.
     */
    public void quit() {
        stage.close();
    }

    /**
     * Set the title of the Game.
     *
     * @param title a {@link String} containing the title of the Game
     */
    public void setTitle(final String title) {
        stage.setTitle(title);
    }

    /**
     * Add a {@link YaegerScene} to the {@link YaegerStage}.
     *
     * @param id    the number as an {@code int} that identifies the {@link YaegerScene}. By calling the method
     *              {@link #setActiveScene(int)} the added {@link YaegerScene} can be set as the active one
     * @param scene the {@link YaegerScene} that should be added
     */
    public void addScene(final int id, final YaegerScene scene) {
        sceneCollection.addScene(id, scene);
    }

    /**
     * Set the current active {@link YaegerScene}.
     *
     * @param id the {@link Integer} identifying the {@link YaegerScene}
     */
    public void setActiveScene(final int id) {
        sceneCollection.setActive(id);
    }

    /**
     * Set the {@link SceneCollectionFactory} to be used whenever a {@link SceneCollection} has to be created.
     *
     * @param sceneCollectionFactory the {@link SceneCollectionFactory}
     */
    @Inject
    public void setSceneCollectionFactory(final SceneCollectionFactory sceneCollectionFactory) {
        this.sceneCollectionFactory = sceneCollectionFactory;
    }

    /**
     * Set the {@link SceneFactory} to be used whenever a {@link Scene} has to be created.
     *
     * @param sceneFactory the {@link SceneFactory}
     */
    @Inject
    public void setSceneFactory(final SceneFactory sceneFactory) {
        this.sceneFactory = sceneFactory;
    }
}
