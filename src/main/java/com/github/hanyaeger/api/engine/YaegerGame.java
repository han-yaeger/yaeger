package com.github.hanyaeger.api.engine;

import com.github.hanyaeger.api.engine.scenes.DynamicScene;
import com.github.hanyaeger.api.engine.scenes.StaticScene;
import com.github.hanyaeger.api.engine.scenes.YaegerScene;
import com.github.hanyaeger.api.guice.YaegerModule;
import com.google.inject.Guice;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * {@link YaegerGame} is the base class that must be extended to create a Yaeger game. After extending this class,
 * two lifecycle methods will become available: {@link #setupGame()} and {@link #setupScenes()}. The methods should be used
 * for any further configuration.
 * <p>
 * The constructor of the extending class should be the default empty constructor. Furthermore, the extending class
 * should have a main-method that should look as follows:
 * <pre>{@code
 *      public static void main(String[] args) {
 *          launch(args);
 *      }
 * }
 * </pre>
 */
public abstract class YaegerGame extends Application {
    public static final KeyCode TOGGLE_DEBUGGER_KEY = KeyCode.F1;
    public static final Size DEFAULT_GAME_DIMENSIONS = new Size(640, 480);

    private YaegerStage yaegerStage;

    /**
     * Set the current active {@link YaegerScene}.
     *
     * @param id The {@link Integer} identifying the {@link YaegerScene}
     */
    public void setActiveScene(final int id) {
        yaegerStage.setActiveScene(id);
    }

    /**
     * Stop and close the Game.
     */
    public void quit() {
        yaegerStage.quit();
    }

    /**
     * Set the {@link Size}, being the {@code width} and {@code height} of the game.
     *
     * @param size A {@link Size} object that encapsulates the {@code width} and {@code height} of the game.
     */
    protected void setSize(final Size size) {
        yaegerStage.setSize(size);
    }

    /**
     * Set the title of the Game.
     *
     * @param title A {@link String} containing the title of the Game.
     */
    protected void setGameTitle(final String title) {
        yaegerStage.setTitle(title);
    }


    /**
     * Add a {@link YaegerScene} to the Game.
     *
     * @param id    The {@link Integer} identifying the {@link YaegerScene}
     * @param scene The {@link YaegerScene} that should be added
     */
    protected void addScene(final int id, final YaegerScene scene) {
        yaegerStage.addScene(id, scene);
    }

    /**
     * Use this method to setup the Game. The following methods are available:
     *
     * <ul>
     *     <li>{@link #setSize(Size)}</li>
     *     <li>{@link #setGameTitle(String)}</li>
     * </ul>
     */
    protected abstract void setupGame();

    /**
     * Use this method to add the instances of {@link YaegerScene} that make up the Game. A {@link YaegerScene} will
     * usually either be a level, a welcome-screen, an intro-screen or a game-over screen, for instance.
     *
     * <p>To create a {@link YaegerScene} extend either a {@link StaticScene} or a
     * {@link DynamicScene}. A {@link StaticScene} should
     * be used for simple screens that do not need the Game world Update cycle. If such an cycle is required, extend
     * a {@link DynamicScene}.
     * </p>
     */
    protected abstract void setupScenes();

    @Override
    public void start(final Stage primaryStage) {
        var yaegerConfig = new YaegerCommandLineParser().parseToConfig(getParameters().getRaw());

        var injector = Guice.createInjector(new YaegerModule());
        yaegerStage = new YaegerStage(this, primaryStage, yaegerConfig);
        injector.injectMembers(yaegerStage);
        yaegerStage.init(injector);
    }
}
