package com.github.hanyaeger.api.scenes;

import com.github.hanyaeger.core.YaegerConfig;
import com.github.hanyaeger.core.*;
import com.github.hanyaeger.core.entities.DragRepositoryAccessor;
import com.github.hanyaeger.core.entities.GameNode;
import com.github.hanyaeger.core.scenes.DimensionsProvider;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * A {@code YaegerScene} encapsulates a scene or level from a {@code Yaeger Game}.
 */
public interface YaegerScene extends GameNode, DimensionsProvider, Destroyable, Initializable, Activatable, Clearable, Effectable, DragRepositoryAccessor {

    /**
     * Setup the {@code YaegerScene}. Use this method to setup all properties of the {@code YaegerScene}. The
     * following methods are available:
     *
     * <ul>
     *     <li>{@link #setBackgroundImage(String)}</li>
     *     <li>{@link #setBackgroundImage(String)}</li>
     *     <li>{@link #setBackgroundAudio(String)}</li>
     * </ul>
     */
    void setupScene();

    /**
     * Use this method to setup all instances of {@code YaegerEntity} that should
     * be added to the {@code YaegerScene} before activation.
     */
    void setupEntities();

    /**
     * Use this method to trigger behaviour that should be set after the {@code YaegerScene} has been completely
     * set up.
     */
    void postActivate();

    /**
     * Set the background color of the {@code YaegerScene}. When both the background color and a background image
     * are set, the color is not visible.
     *
     * @param color the {@link Color} of the background
     */
    void setBackgroundColor(final Color color);

    /**
     * Set the background image file.
     * <p>
     * The {@code url} of the image is relative to the {@code resources/} folder, where all
     * resources should be placed. If the resource is placed in a subfolder of {@code resources/}, this folder should
     * be opened explicitly from the module descriptor.
     *
     * @param url the name of the image file, including extension. Although many different file types are supported,
     *            the following types are preferred:
     *            <ul>
     *            <li>jpg, jpeg</li>
     *            <li>png</li>
     *            </ul>
     */
    void setBackgroundImage(final String url);

    /**
     * Set the background audio file. Currently only {@code *.mp3} files are supported. The audio file
     * will be looped indefinitely, until the {@code YaegerScene} is destroyed.
     * <p>
     * Set the background image file. The {@code url} of the image is relative to the {@code resources/} folder, where all
     * resources should be placed. If the resource is placed in a subfolder of {@code resources/}, this folder should
     * be opened explicitly from the module descriptor.
     *
     * @param url the name of the audio file, including extension
     */
    void setBackgroundAudio(final String url);

    /**
     * Return the {@link Scene} that is encapsulated by this {@code YaegerScene}
     *
     * @return the {@link Scene} that is encapsulated bu this {@code YaegerScene}
     */
    Scene getScene();

    /**
     * Return the {@link Stage} to which this {@code YaegerScene} will be added.
     *
     * @return the {@link Stage} to which this {@code YaegerScene} will be added
     */
    Stage getStage();

    /**
     * Set the {@link Stage} on this {@code YaegerScene}. A {@code YaegerScene} will only be added to
     * the {@link Stage} when the {@code YaegerScene} is the one that is being shown. Only when a {@code YaegerScene}
     * is added, its {@code width} and {@code height} are available. Thus, to make it possible to request the
     * {@code width} and {@code height} during the {@code YaegerScene#setupEntities()} methods, we need the {@link Stage}
     * at an earlier stage. That is why we actively set the {@link Stage} on each {@code YaegerScene} that is created.
     *
     * @param stage the current {@link Stage}
     */
    void setStage(final Stage stage);

    /**
     * Set the Cursor.
     *
     * @param cursor the {@link Cursor} that should be used
     */
    default void setCursor(final Cursor cursor) {
        getScene().setCursor(cursor);
    }

    /**
     * Return the width of this {@code YaegerScene}.
     *
     * @return the width of this {@code YaegerScene}
     */
    default double getWidth() {
        return getStage().getScene().getWidth();
    }

    /**
     * Return the height of this {@code YaegerScene}.
     *
     * @return the height of this {@code YaegerScene}
     */
    default double getHeight() {
        return getStage().getScene().getHeight();
    }

    /**
     * Set the {@code YaegerConfig} to be used by this {@code YaegerScene}.
     *
     * @param yaegerConfig the {@link YaegerConfig} to be used by this {@code YaegerScene}
     */
    void setConfig(final YaegerConfig yaegerConfig);
}
