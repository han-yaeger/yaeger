package nl.meron.yaeger.engine.scenes;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import nl.meron.yaeger.engine.Clearable;
import nl.meron.yaeger.engine.Configurable;
import nl.meron.yaeger.engine.Destroyable;
import nl.meron.yaeger.engine.Initializable;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.entities.entity.UpdateDelegator;

/**
 * A {@code YaegerScene} encapsulates a scene or level from a {@code Yaeger Game}.
 */
public interface YaegerScene extends Destroyable, Initializable, Configurable, Clearable {

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
    void postActivation();

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
     * Set the Cursor.
     *
     * @param cursor the {@link Cursor} that should be used
     */
    default void setCursor(final Cursor cursor) {
        getScene().setCursor(cursor);
    }
}
