package nl.meron.yaeger.engine.entities.entity;

/**
 * A {@link SceneChild} is part of a {@link nl.meron.yaeger.engine.scenes.YaegerScene}
 * and has thus access to width and height of the {@link nl.meron.yaeger.engine.scenes.YaegerScene} it
 * is part of.
 */
public interface SceneChild extends NodeProvider {

    /**
     * Return the width of the {@link javafx.scene.Scene} that this {@code Entity}
     * is part of.
     *
     * @return the width of this {@code Scene} as a {@code double}
     */
    default double getSceneWidth() {
        return getGameNode().getScene().getWidth();
    }

    /**
     * Return the height of the {@link javafx.scene.Scene} that this {@code Entity}
     * is part of.
     *
     * @return the height of this {@code Scene} as a {@code double}
     */
    default double getSceneHeight() {
        return getGameNode().getScene().getHeight();
    }
}
