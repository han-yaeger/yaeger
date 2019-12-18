package nl.meron.yaeger.engine;

import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import nl.meron.yaeger.engine.scenes.Scenes;
import nl.meron.yaeger.engine.scenes.SceneType;
import nl.meron.yaeger.engine.scenes.YaegerScene;

/**
 * {@code YaegerEngine} is the base class that must be extended to create a YAEGER game.
 */
public abstract class YaegerEngine extends Application {
    public static final KeyCode TOGGLE_DEBUGGER_KEY = KeyCode.F1;

    private static final Size DEFAULT_GAME_DIMENSIONS = new Size(640, 480);

    private Size size = DEFAULT_GAME_DIMENSIONS;

    private Stage yaegerStage;
    private Scenes scenes;

    /**
     * Set the {@link Size}, being the {@code width} and {@code height} of the game.
     *
     * @param size A {@link Size} object that encapsulates the {@code width} and {@code height} of the game
     */
    protected void setSize(Size size) {
        this.size = size;
    }

    /**
     * Set the title of the Game.
     *
     * @param title A {@link String} containing the title of the Game
     */
    protected void setGameTitle(String title) {
        yaegerStage.setTitle(title);
    }

    /**
     * Set the current active {@link YaegerScene}.
     *
     * @param type The {@link SceneType} that corresponds to the {@link YaegerScene}.
     */
    protected void setActiveScene(SceneType type) {
        scenes.setActive(type);
    }

    /**
     * Add a {@link YaegerScene} to the Game.
     *
     * @param type  A {@link SceneType} enumeration that represents the type of the {@link YaegerScene}
     * @param scene The {@link YaegerScene} that should be added
     */
    protected void addScene(SceneType type, YaegerScene scene) {

        scenes.addScene(type, scene);
    }

    /**
     * Use this method for game initialization code.
     *
     * <p>
     * Initialization options are:
     *     <ul>
     *         <li>setSize()</li>
     *         <li>setGameTitle()</li>
     *     </ul>
     * </p>
     */
    protected abstract void initializeGame();

    /**
     * Use this method to create and add the different instances of {@link YaegerScene} that comprise the Game.
     */
    protected abstract void setupScenes();

    @Override
    public void start(Stage primaryStage) {
        yaegerStage = primaryStage;

        scenes = new Scenes(primaryStage);

        initializeGame();

        yaegerStage.setWidth(size.getWidth());
        yaegerStage.setHeight(size.getHeight());

        setupScenes();

        showGame();
    }

    /**
     * Returns the height of the Game.
     *
     * @return The height of the Game.
     */
    public double getGameHeight() {
        return this.size.getHeight();
    }

    /**
     * Returns the width of the Game.
     *
     * @return The width of the Game.
     */
    public double getGameWidth() {
        return this.size.getWidth();
    }

    /**
     * Stop the Game and close the Game.
     */
    public void quitGame() {
        yaegerStage.close();
    }

    private void showGame() {
        yaegerStage.show();
    }
}
