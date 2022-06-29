package com.github.hanyaeger.api;

import com.github.hanyaeger.api.scenes.DynamicScene;
import com.github.hanyaeger.api.scenes.StaticScene;
import com.github.hanyaeger.core.YaegerCommandLineParser;
import com.github.hanyaeger.core.YaegerStage;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.github.hanyaeger.core.guice.YaegerModule;
import com.github.hanyaeger.core.media.BackgroundAudioMediaPlayer;
import com.google.inject.Guice;
import javafx.application.Application;
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
    /**
     * As an ode to the old days, the default dimensions of a Yaeger Game is 640x480px.
     */
    public static final Size DEFAULT_GAME_DIMENSIONS = new Size(640, 480);

    private YaegerStage yaegerStage;
    private BackgroundAudioMediaPlayer backgroundAudioMediaPlayer;

    /**
     * Set the current active {@link YaegerScene}.
     *
     * @param id the {@link Integer} identifying the {@link YaegerScene}
     */
    public void setActiveScene(final int id) {
        yaegerStage.setActiveScene(id);
    }

    /**
     * Stop and close the Game.
     */
    public void quit() {
        backgroundAudioMediaPlayer.destroy();
        yaegerStage.quit();
    }

    /**
     * Set the {@link Size}, being the {@code width} and {@code height} of the game.
     *
     * @param size a {@link Size} object that encapsulates the {@code width} and {@code height} of the game
     */
    protected void setSize(final Size size) {
        yaegerStage.setSize(size);
    }

    /**
     * Set the title of the Game.
     *
     * @param title a {@link String} containing the title of the Game
     */
    protected void setGameTitle(final String title) {
        yaegerStage.setTitle(title);
    }


    /**
     * Add a {@link YaegerScene} to the Game.
     *
     * @param id    the {@link Integer} identifying the {@link YaegerScene}
     * @param scene the {@link YaegerScene} that should be added
     */
    public void addScene(final int id, final YaegerScene scene) {
        yaegerStage.addScene(id, scene);
    }

    /**
     * Use this method to call the methods responsible for setting up the Yaeger Game. The following methods are
     * available:
     *
     * <ul>
     *     <li>{@link #setSize(Size)}</li>
     *     <li>{@link #setGameTitle(String)}</li>
     *     <li>{@link #setBackgroundAudio(String)}</li>
     *     <li>{@link #getBackgroundAudioVolume()}</li>
     * </ul>
     */
    public abstract void setupGame();

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
    public abstract void setupScenes();

    /**
     * Set the background audio file. Currently, only {@code *.mp3} files are supported. The audio file
     * will be looped indefinitely. Since it is part of the {@code YaegerGame}, it will continue
     * playing through each {@link YaegerScene}.
     * <p>
     * If the audio file is specific to each {@link YaegerScene}, the method {@link YaegerScene#setBackgroundAudio(String)}
     * should be used.
     * <p>
     * Set the background image file. The {@code url} of the image is relative to the {@code resources/} folder, where all
     * resources should be placed. If the resource is placed in a subfolder of {@code resources/}, this folder should
     * be opened explicitly from the module descriptor.
     *
     * @param url the name of the audio file, including extension
     */
    protected void setBackgroundAudio(final String url) {
        backgroundAudioMediaPlayer.playBackgroundAudio(url);
    }

    /**
     * Set the playback volume of the background audio.
     *
     * @param volume the volume
     */
    protected void setBackgroundAudioVolume(double volume) {
        backgroundAudioMediaPlayer.setVolume(volume);
    }

    /**
     * Return the playback volume of the background audio. This is a value between [0, 1.0],
     * where 0 means it is not audible and 1.0 it is played at its original volume level.
     *
     * @return the volume as a {@code double}
     */
    protected double getBackgroundAudioVolume() {
        return backgroundAudioMediaPlayer.getVolume();
    }

    /**
     * Stop playing the background audio.
     */
    protected void stopBackgroundAudio() {
        backgroundAudioMediaPlayer.stopBackgroundAudio();
    }

    @Override
    public void start(final Stage primaryStage) {
        final var yaegerConfig = new YaegerCommandLineParser().parseToConfig(getParameters().getRaw());

        final var injector = Guice.createInjector(new YaegerModule());
        backgroundAudioMediaPlayer = new BackgroundAudioMediaPlayer();
        yaegerStage = new YaegerStage(this, primaryStage, yaegerConfig);
        injector.injectMembers(yaegerStage);
        yaegerStage.init(injector);
    }
}
