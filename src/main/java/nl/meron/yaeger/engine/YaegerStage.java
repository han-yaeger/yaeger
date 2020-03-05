package nl.meron.yaeger.engine;

import com.google.inject.Injector;
import javafx.stage.Stage;
import nl.meron.yaeger.engine.scenes.SceneCollection;
import nl.meron.yaeger.engine.scenes.YaegerScene;

public class YaegerStage implements Initializable {

    private Size size = YaegerEngine.DEFAULT_GAME_DIMENSIONS;

    private YaegerEngine yaegerEngine;
    private Stage stage;
    private SceneCollection sceneCollection;

    YaegerStage(final YaegerEngine yaegerEngine, final Stage stage) {
        this.yaegerEngine = yaegerEngine;
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
    public void init(Injector injector) {
        stage.setResizable(false);

        sceneCollection = new SceneCollection(stage, injector);
        injector.injectMembers(sceneCollection);

        yaegerEngine.initializeGame();

        stage.setWidth(size.getWidth());
        stage.setHeight(size.getHeight());

        yaegerEngine.setupScenes();

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
     * @param number The number as an {@code int} that identifies the {@link YaegerScene}. By calling the method
     *               {@link #setActiveScene(int)} the added {@link YaegerScene} can be set as the active one.
     * @param scene  The {@link YaegerScene} that should be added.
     */
    protected void addScene(final int number, final YaegerScene scene) {
        sceneCollection.addScene(number, scene);
    }

    /**
     * Set the current active {@link YaegerScene}.
     *
     * @param number The {@link Integer} identifying the {@link YaegerScene}
     */
    protected void setActiveScene(final int number) {
        sceneCollection.setActive(number);
    }
}
