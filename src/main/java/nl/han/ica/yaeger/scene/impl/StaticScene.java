package nl.han.ica.yaeger.scene.impl;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.ImagePattern;
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
    private Sound backgroundAudio;
    protected Set<KeyCode> input = new HashSet<>();

    /**
     * Voeg een {@link Entity} toe aan de {@code Scene}. {@link Entity}s kunnen maar één keer worden toegevoegd.
     * Deze methode kan enkel gebruikt worden voor {@link Entity}en die bij initialisatie aan het spel moeten worden
     * toegevoegd. Indien er tijdens het spel extra {@link Entity}en moeten worden toegevoegd, gebruik dan een
     * {@link EntitySpawner}.
     *
     * @param entity Het {@link Entity} dat moet worden toegevoegd.
     */
    protected void addEntity(Entity entity) {
        root.getChildren().add(entity.getGameNode());
    }

    /**
     * Zet het achtergrondplaatje van de Scene.
     *
     * @param image De naam van het bestand, inclusief extentie. Er worden zeer veel bestandsformaten ondersteund, maar
     *              kies bij voorkeur voor een van de volgende:
     *              <ul>
     *              <li>jpg, jpeg</li>
     *              <li>png</li>
     *              </ul>
     */
    protected void setBackgroundImage(String image) {

        var stringUrl = createPathForResource(image);
        var pattern = new ImagePattern(new Image(stringUrl));
        scene.setFill(pattern);
    }

    /**
     * Zet de achtergrondaudio van de Scene.
     *
     * @param file De naam van het bestand, inclusief extentie. Er worden zeer veel bestandsformaten ondersteund, maar
     *             kies bij voorkeur voor een van de volgende:
     *             <ul>
     *             <li>jpg, jpeg</li>
     *             <li>png</li>
     *             </ul>
     */
    protected void setBackgroundAudio(String file) {

        backgroundAudio = new Sound(file, Sound.INDEFINITE);
        backgroundAudio.play();
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
        addKeyListeners();

        if (backgroundAudio != null) {
            backgroundAudio.play();
        }
    }

    @Override
    public void tearDownScene() {
        removeKeyListeners();
        stopBackgroundAudio();
        removeElementsFromView();
    }

    private void removeElementsFromView() {
        root.getChildren().clear();
    }

    private void stopBackgroundAudio() {
        if (backgroundAudio != null) {
            backgroundAudio.stop();
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
                    onInputChanged(input);
                });

        scene.setOnKeyReleased(
                e -> {
                    var code = e.getCode();
                    input.remove(code);
                    onInputChanged(input);
                });
    }
}
