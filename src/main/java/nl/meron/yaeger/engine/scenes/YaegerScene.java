package nl.meron.yaeger.engine.scenes;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nl.meron.yaeger.engine.Clearable;
import nl.meron.yaeger.engine.Activatable;
import nl.meron.yaeger.engine.Destroyable;
import nl.meron.yaeger.engine.Initializable;
import nl.meron.yaeger.engine.entities.entity.Entity;

/**
 * A {@code YaegerScene} encapsulates a scene or level from a {@code Yaeger Game}.
 */
public interface YaegerScene extends DimensionsProvider, Destroyable, Initializable, Activatable, Clearable {

    /**
     * Setup the {@link YaegerScene}. Use this method to add background images and/or background audio.
     */
    void setupScene();

    /**
     * Use this method to setup all instances of {@link Entity} that should
     * be added to the {@link YaegerScene} before activation.
     */
    void setupEntities();

    /**
     * Use this method to trigger behaviour that should be set after the {@link YaegerScene} has been completely
     * set up.
     */
    void postActivate();

    /**
     * Set the background color of the {@link YaegerScene}.
     *
     * @param color The {@link Color} of the background.
     */
    void setBackgroundColor(Color color);

    /**
     * Set the name of the background image file.
     *
     * @param url The name of the image file, including extention. Although many different file types are supported,
     *            the following types are preferred:
     *            <ul>
     *            <li>jpg, jpeg</li>
     *            <li>png</li>
     *            </ul>
     */
    void setBackgroundImage(String url);

    /**
     * Set the name of the background audio file. Currently only {@code *.mp3} files are supported.
     *
     * @param url The name of the audio file, including extention.
     */
    void setBackgroundAudio(final String url);

    /**
     * Return the {@link Scene} that is encapsulated by this {@link YaegerScene}
     *
     * @return The {@link Scene} that is encapsulated bu this {@link YaegerScene}
     */
    Scene getScene();

    /**
     * Return the {@link Stage} to which this {@link YaegerScene} will be added.
     *
     * @return The {@link Stage} to which this {@link YaegerScene} will be added.
     */
    Stage getStage();

    /**
     * Set the {@link Stage} on this {@link YaegerScene}. A {@link YaegerScene} will only be added to
     * the {@link Stage} when the {@link YaegerScene} is the one that is being shown. Only when a {@link YaegerScene}
     * is added, its {@code width} and {@code height} are available. Thus, to make it possible to request the
     * {@code width} and {@code height} during the {@link YaegerScene#setupEntities()} methods, we need the {@link Stage}
     * at an earlier stage. That is why we actively set the {@link Stage} on each {@link YaegerScene} that is created.
     *
     * @param stage The current {@link Stage}.
     */
    void setStage(Stage stage);

    /**
     * Set the Cursor.
     *
     * @param cursor the {@link Cursor} that should be used
     */
    default void setCursor(final Cursor cursor) {
        getScene().setCursor(cursor);
    }

    /**
     * Return the width of this {@link YaegerScene}.
     *
     * @return The width of this {@link YaegerScene}.
     */
    default double getWidth() {
        return getStage().getWidth();
    }

    /**
     * Return the height of this {@link YaegerScene}.
     *
     * @return The height of this {@link YaegerScene}.
     */
    default double getHeight() {
        return getStage().getHeight();
    }
}
