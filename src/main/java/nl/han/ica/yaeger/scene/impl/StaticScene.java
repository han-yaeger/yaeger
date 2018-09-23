package nl.han.ica.yaeger.scene.impl;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.ImagePattern;
import nl.han.ica.yaeger.debug.Debugger;
import nl.han.ica.yaeger.entities.Entity;
import nl.han.ica.yaeger.entities.spawners.EntitySpawner;
import nl.han.ica.yaeger.resourceconsumer.ResourceConsumer;
import nl.han.ica.yaeger.resourceconsumer.audio.Sound;
import nl.han.ica.yaeger.scene.YaegerScene;
import nl.han.ica.yaeger.scene.factory.GroupFactory;
import nl.han.ica.yaeger.scene.factory.SceneFactory;

import java.util.HashSet;
import java.util.Set;

public abstract class StaticScene implements YaegerScene, ResourceConsumer {

    private Scene scene;
    private Group root;
    Debugger debugger;
    private Sound backgroundAudio;
    private String backgroundAudioUrl;
    private String backgroundImage;
    protected Set<KeyCode> input = new HashSet<>();

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
     * Zet het achtergrondplaatje van de scene.
     *
     * @param image De naam van het bestand, inclusief extentie. Er worden zeer veel bestandsformaten ondersteund, maar
     *              kies bij voorkeur voor een van de volgende:
     *              <ul>
     *              <li>jpg, jpeg</li>
     *              <li>png</li>
     *              </ul>
     */
    protected void setBackgroundImage(String image) {
        backgroundImage = image;
    }

    /**
     * Zet de achtergrond-audio van de scene.
     *
     * @param file De naam van het bestand, inclusief extentie. Er worden zeer veel bestandsformaten ondersteund, maar
     *             kies bij voorkeur voor een van de volgende:
     *             <ul>
     *             <li>jpg, jpeg</li>
     *             <li>png</li>
     *             </ul>
     */
    protected void setBackgroundAudio(String file) {
        backgroundAudioUrl = file;
    }

    /**
     * Implementeer deze methode om bericht te krijgen wanneer set van ingedrukte toetsen wijzigt.
     *
     * @param input Een {@link Set} die alle toetsen bevat die momenteel ingedrukt zijn.
     */
    public abstract void onInputChanged(Set<KeyCode> input);


    @Override
    public void setupScene() {
        root = new GroupFactory().getInstance();
        scene = new SceneFactory().getInstance(root);
        debugger = new Debugger(root);
        addKeyListeners();

        setupBackgroundAudio();
        setupBackgroundImage();
    }

    @Override
    public void tearDownScene() {
        removeKeyListeners();
        stopBackgroundAudio();
        clearView();
    }

    private void setupBackgroundAudio() {
        if (backgroundAudioUrl != null) {
            backgroundAudio = new Sound(backgroundAudioUrl, Sound.INDEFINITE);
            backgroundAudio.play();
        }
    }

    private void setupBackgroundImage() {
        if (backgroundImage != null && scene != null) {
            var stringUrl = createPathForResource(backgroundImage);
            var pattern = new ImagePattern(new Image(stringUrl));
            scene.setFill(pattern);
        }
    }

    private void clearView() {
        root.getChildren().clear();
        root = null;
        scene = null;
    }

    private void stopBackgroundAudio() {
        if (backgroundAudio != null) {
            backgroundAudio.stop();
            backgroundAudio = null;
        }
    }

    private void removeKeyListeners() {
        scene.setOnKeyPressed(null);
        scene.setOnKeyReleased(null);
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Retourneer de {@link Group} waar alle {@link Entity} aan moeten worden toegevoegd.
     *
     * @return De {@link Group} waar alle {@link Entity} aan moeten worden toegevoegd.
     */
    protected Group getRoot() {
        return this.root;
    }

    private void addKeyListeners() {
        scene.setOnKeyPressed(
                e -> {
                    var code = e.getCode();
                    input.add(code);
                    inputChanged(input);
                });

        scene.setOnKeyReleased(
                e -> {
                    var code = e.getCode();
                    input.remove(code);
                    inputChanged(input);
                });
    }

    private void inputChanged(Set<KeyCode> input) {

        if (input.contains(KeyCode.F1)) {
            debugger.toggle();
        }

        onInputChanged(input);
    }
}
