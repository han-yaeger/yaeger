package nl.han.ica.yaeger.engine.scenes;

import javafx.scene.Scene;
import nl.han.ica.yaeger.engine.Clearable;
import nl.han.ica.yaeger.engine.Configurable;
import nl.han.ica.yaeger.engine.Destroyable;
import nl.han.ica.yaeger.engine.Initializable;

/**
 * A {@code YaegerScene} encapsulates a scene or level from a {@code Yaeger Game}.
 */
public interface YaegerScene extends Destroyable, Initializable, Configurable, Clearable {

    /**
     * Setup the {@link YaegerScene}. Use this method to add background images and/or background audio.
     */
    void setupScene();

    /**
     * Use this method to setup all instances of {@link nl.han.ica.yaeger.engine.entities.entity.Entity} that should
     * be added to the {@link YaegerScene} before activation.
     */
    void setupEntities();

    /**
     * Use this method to trigger behaviour that should be set after the {@link YaegerScene} has been completely
     * set up.
     */
    void postActivation();

    /**
     * Return the {@link Scene} that is encapsulated by this {@link YaegerScene}
     *
     * @return The {@link Scene} that is encapsulated bu this {@link YaegerScene}
     */
    Scene getScene();
}
