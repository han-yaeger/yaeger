package nl.han.ica.yaeger.scene.impl;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import nl.han.ica.yaeger.KeyListener;
import nl.han.ica.yaeger.debug.Debugger;
import nl.han.ica.yaeger.entities.Entity;
import nl.han.ica.yaeger.entities.spawners.EntitySpawner;
import nl.han.ica.yaeger.scene.YaegerScene;
import nl.han.ica.yaeger.scene.delegates.BackgroundDelegate;
import nl.han.ica.yaeger.scene.delegates.KeyListenerDelegate;
import nl.han.ica.yaeger.scene.factory.GroupFactory;
import nl.han.ica.yaeger.scene.factory.SceneFactory;

import java.util.Set;

public abstract class StaticScene implements YaegerScene, KeyListener {

    private Scene scene;
    private Group root;
    Debugger debugger;
    private KeyListenerDelegate keyListenerDelegate = new KeyListenerDelegate();
    private BackgroundDelegate backgroundDelegate = new BackgroundDelegate();

    /**
     * Maak een nieuwe {@code StaticScene}. Tijdens constructie wordt als eerste de methode {@code initializeScene}
     * aangeroepen.
     */
    protected StaticScene() {
        initializeScene();
    }

    /**
     * Voeg een {@link Entity} toe aan de {@code scene}. {@link Entity}s kunnen maar één keer worden toegevoegd.
     * Deze methode kan enkel gebruikt worden voor {@link Entity}en die bij initialisatie aan het game moeten worden
     * toegevoegd. Indien er tijdens het game extra {@link Entity}en moeten worden toegevoegd, gebruik dan een
     * {@link EntitySpawner}.
     *
     * @param entity Het {@link Entity} dat moet worden toegevoegd.
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
     * Set the name of the background audio file. Currently only *.mp3 files are supported.
     *
     * @param file The name of the audio file, including extention.
     */
    protected void setBackgroundAudio(String file) {
        backgroundDelegate.setBackgroundAudio(file);
    }

    /**
     * Implement this method to be informed when a key has been pressed or released..
     *
     * @param input A {@link Set} containg all keys currently pressed.
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

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public void setupScene() {
        root = new GroupFactory().getInstance();
        scene = new SceneFactory().getInstance(root);
        debugger = new Debugger(root);
        keyListenerDelegate.setup(scene, this);
        backgroundDelegate.setup(scene);
    }

    @Override
    public void tearDownScene() {
        keyListenerDelegate.tearDown(scene);
        backgroundDelegate.tearDown(scene);
        clearView();
    }

    private void clearView() {
        root.getChildren().clear();
        root = null;
        scene = null;
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> input) {
        if (input.contains(KeyCode.F1)) {
            debugger.toggle();
        }

        onInputChanged(input);
    }
}
