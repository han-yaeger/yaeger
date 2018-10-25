package nl.han.ica.yaeger.engine.scenes.impl;

import com.google.inject.Inject;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import nl.han.ica.yaeger.engine.userinput.KeyListener;
import nl.han.ica.yaeger.engine.debug.Debugger;
import nl.han.ica.yaeger.engine.entities.entity.Entity;
import nl.han.ica.yaeger.engine.entities.spawners.EntitySpawner;
import nl.han.ica.yaeger.engine.scenes.YaegerScene;
import nl.han.ica.yaeger.engine.scenes.delegates.BackgroundDelegate;
import nl.han.ica.yaeger.engine.scenes.delegates.KeyListenerDelegate;
import nl.han.ica.yaeger.module.factories.DebuggerFactory;
import nl.han.ica.yaeger.module.factories.SceneFactory;

import java.util.Set;

public abstract class StaticScene implements YaegerScene, KeyListener {

    private SceneFactory sceneFactory;
    private DebuggerFactory debuggerFactory;

    private Scene scene;
    private Group root;
    Debugger debugger;
    private KeyListenerDelegate keyListenerDelegate;
    private BackgroundDelegate backgroundDelegate;

    /**
     * Instantiate a new {@code StaticScene}. During construction, the lifecycle method {@code initializeScene}
     * will be called.
     */
    protected StaticScene() {
        backgroundDelegate = new BackgroundDelegate();

        initializeScene();
    }

    /**
     * Add an {@link Entity} to this {@link YaegerScene}. An {@link Entity} can only be added once.
     * <p>
     * This method can only be used to add an instance of {@link Entity} during initialisation.If
     * one should be added during the game, a {@link EntitySpawner} should be used.
     * </p>
     *
     * @param entity the {@link Entity} to be added
     */
    protected void addEntity(Entity entity) {
        root.getChildren().add(entity.getGameNode());
    }

    /**
     * Set the name of the background image file.
     *
     * @param image The name of the image file, including extention. Although many different file types are supported,
     *              the following types are preferred:
     *              <ul>
     *              <li>jpg, jpeg</li>
     *              <li>png</li>
     *              </ul>
     */
    protected void setBackgroundImage(String image) {
        backgroundDelegate.setBackgroundImageUrl(image);
    }

    /**
     * Set the name of the background audio file. Currently only {@code *.mp3} files are supported.
     *
     * @param file The name of the audio file, including extention.
     */
    protected void setBackgroundAudio(String file) {
        backgroundDelegate.setBackgroundAudio(file);
    }

    /**
     * Implement this method to be informed when a key has been pressed or released.
     *
     * @param input A {@link Set} containing all keys currently pressed.
     */
    public abstract void onInputChanged(Set<KeyCode> input);

    /**
     * Return the {@link Group} to which all instances of {@link Entity} are added.
     *
     * @return The {@link Group} to which all instances of {@link Entity} are added.
     */
    protected Group getRoot() {
        return this.root;
    }


    /**
     * Set the {@link Group} to be used. The {@link Group} will be the root node of the graph that
     * will be constructed for this {@link Scene}.
     *
     * @param root the {@link Group} to be used
     */
    @Inject
    public void setRoot(Group root) {
        this.root = root;
    }

    /**
     * Set the {@link KeyListener} that should be used. In general, this will be the {@link YaegerScene}
     * itself.
     *
     * @param keyListenerDelegate the {@link KeyListener} to be used
     */
    @Inject
    public void setKeyListenerDelegate(KeyListenerDelegate keyListenerDelegate) {
        this.keyListenerDelegate = keyListenerDelegate;
    }

    /**
     * Set the {@link SceneFactory} that should be used to create a {@link Scene}.
     *
     * @param sceneFactory the {@link SceneFactory} to be used
     */
    @Inject
    public void setSceneFactory(SceneFactory sceneFactory) {
        this.sceneFactory = sceneFactory;
    }

    /**
     * Set the {@link DebuggerFactory} that should be used to create a {@link Debugger}.
     *
     * @param debuggerFactory the {@link DebuggerFactory} to be used
     */
    @Inject
    public void setDebuggerFactory(DebuggerFactory debuggerFactory) {
        this.debuggerFactory = debuggerFactory;
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public void setupScene() {
        scene = sceneFactory.create(root);
        debugger = debuggerFactory.create(root);

        keyListenerDelegate.setup(scene, this);
        backgroundDelegate.setup(scene);
    }

    @Override
    public void destroy() {
        keyListenerDelegate.tearDown(scene);
        backgroundDelegate.tearDown(scene);
        clearView();
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> input) {
        if (input.contains(KeyCode.F1)) {
            debugger.toggle();
        }

        onInputChanged(input);
    }

    private void clearView() {
        root.getChildren().clear();
        root = null;
        scene = null;
    }


}
