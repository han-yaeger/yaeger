package nl.meron.yaeger.engine;

import com.google.inject.Guice;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import nl.meron.yaeger.engine.scenes.SceneCollection;
import nl.meron.yaeger.engine.scenes.YaegerScene;
import nl.meron.yaeger.guice.YaegerModule;

/**
 * {@link YaegerEngine} is the base class that must be extended to create a Yaeger game.
 */
public abstract class YaegerEngine extends Application {
    public static final KeyCode TOGGLE_DEBUGGER_KEY = KeyCode.F1;

    private static final Size DEFAULT_GAME_DIMENSIONS = new Size(640, 480);

    private Size size = DEFAULT_GAME_DIMENSIONS;

    private Stage yaegerStage;
    private SceneCollection sceneCollection;

    /**
     * Set the {@link Size}, being the {@code width} and {@code height} of the game.
     *
     * @param size A {@link Size} object that encapsulates the {@code width} and {@code height} of the game
     */
    protected void setSize(final Size size) {
        this.size = size;
    }

    /**
     * Set the title of the Game.
     *
     * @param title A {@link String} containing the title of the Game
     */
    protected void setGameTitle(final String title) {
        yaegerStage.setTitle(title);
    }

    /**
     * Set the current active {@link YaegerScene}.
     *
     * @param number The {@link Integer} identifying the {@link YaegerScene}
     */
    protected void setActiveScene(final int number) {
        sceneCollection.setActive(number);
    }

    /**
     * Add a {@link YaegerScene} to the Game.
     *
     * @param number The {@link Integer} identifying the {@link YaegerScene}
     * @param scene  The {@link YaegerScene} that should be added
     */
    protected void addScene(final int number, final YaegerScene scene) {

        sceneCollection.addScene(number, scene);
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
     * Use this method to add the instances of {@link YaegerScene} that make up the Game. A {@link YaegerScene} will
     * usually either be a level, a welcome-screen, an intro-screen or a game-over screen, for instance.
     *
     * <p>To create a {@link YaegerScene} extend either a {@link nl.meron.yaeger.engine.scenes.StaticScene} or a
     * {@link nl.meron.yaeger.engine.scenes.DynamicScene}. A {@link nl.meron.yaeger.engine.scenes.StaticScene} should
     * be used for simple screens that do not need the Game world Update cycle. If such an cycle is required, extend
     * a {@link nl.meron.yaeger.engine.scenes.DynamicScene}.
     * </p>
     */
    protected abstract void setupScenes();

    @Override
    public void start(final Stage primaryStage) {
        var injector = Guice.createInjector(new YaegerModule());
        yaegerStage = primaryStage;
        yaegerStage.setResizable(false);

        sceneCollection = new SceneCollection(primaryStage, injector);
        injector.injectMembers(sceneCollection);

        initializeGame();

        yaegerStage.setWidth(size.getWidth());
        yaegerStage.setHeight(size.getHeight());

        setupScenes();

        showGame();
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
