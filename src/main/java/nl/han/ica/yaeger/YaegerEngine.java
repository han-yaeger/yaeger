package nl.han.ica.yaeger;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import nl.han.ica.yaeger.exceptions.YaegerLifecycleException;
import nl.han.ica.yaeger.gameobjects.GameObject;
import nl.han.ica.yaeger.gameobjects.GameObjects;
import nl.han.ica.yaeger.gameobjects.spawners.ObjectSpawner;
import nl.han.ica.yaeger.metrics.GameDimensions;
import nl.han.ica.yaeger.resourceconsumer.ResourceConsumer;

import java.util.HashSet;
import java.util.Set;

/**
 * {@code YaegerEngine} is de basis-superklasse die ge-extend moet worden. Na het extenden zal een aantal methodes
 * worden aangeboden die het mogelijk maken de inhoud van het spel te initialiseren.
 */
public abstract class YaegerEngine extends Application implements ResourceConsumer {

    private static final GameDimensions DEFAULT_GAME_DIMENSIONS = new GameDimensions(640, 480);

    private GameDimensions gameDimensions = DEFAULT_GAME_DIMENSIONS;

    private Set<KeyCode> input = new HashSet<>();

    private Set<GameObject> inititialGameObjects = new HashSet<>();
    private GameObjects gameObjects;
    private Stage primaryStage;
    private Scene scene;

    private boolean sceneIsCreated = false;
    private boolean stageIsShown = false;


    /**
     * Zet de breedte en hoogte van het spel.
     *
     * @param dimensions Een {@link GameDimensions} object encapsuleert de breedte en hoogte van een spel.
     */
    protected void setGameDimensions(GameDimensions dimensions) {
        if (sceneIsCreated) {
            throw new YaegerLifecycleException("Het zetten van de spel dimensies is niet toegestaan. Het algemene scherm (stage) is al aangemaakt.");
        }
        this.gameDimensions = dimensions;
    }

    /**
     * Zet de titel van het spel.
     *
     * @param title De titel van het spel.
     */
    public void setGameTitle(String title) {
        if (sceneIsCreated) {
            throw new YaegerLifecycleException("Het zetten van de titel is nu niet toegestaan. Het algemene scherm (stage) is al aangemaakt.");
        }

        primaryStage.setTitle(title);
    }

    /**
     * This method is called before the scene is created.
     * <p>
     * At this stage it is possible to set the dimensions of the scene, to init a title to the application or
     * perform other initializations.
     * </p>
     */
    protected void beforeStageIsCreated() {
    }

    /**
     * Override this method to setup the stage before it is shown.
     */
    protected void beforeGameLoopIsCreated() {
    }

    /**
     * This method is called directly after the stage is shown.
     * <p>
     * Override this method to init behaviour that should be added after the stage is shown.
     * </p>
     */
    protected void afterStageIsShown() {
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
        if (!sceneIsCreated) {
            throw new YaegerLifecycleException("It is not yet allowed to set the Background of the Scene. This is only allowed after the Scene is created.");
        }

        var stringUrl = createPathForResource(image);
        var pattern = new ImagePattern(new Image(stringUrl));
        scene.setFill(pattern);
    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        beforeStageIsCreated();

        createStage();

        beforeGameLoopIsCreated();
        createGameLoop();

        showStage();
        afterStageIsShown();
    }

    /**
     * Add a new GameObject to the Scene. GameObjects can only be added once. If a GameObject is
     * added multiple times, only one instance will be added.
     *
     * @param gameObject A GameObject;
     */
    protected void addGameObject(GameObject gameObject) {
        if (stageIsShown) {
            throw new YaegerLifecycleException("It is no longer allowed to init more GameObjects. Please use a " +
                    "GameObjectSpawner to create new GameObjects after the GameLoop has been created.");
        }

        inititialGameObjects.add(gameObject);
    }

    /**
     * Registreer een {@link ObjectSpawner}. Na registratie zal de {@link ObjectSpawner} verantwoordelijk worden voor
     * het aanmaken (spawnen) van nieuwe {@link GameObject}s.
     *
     * @param spawner De {@link ObjectSpawner} die geregistreerd moet worden.
     */
    protected void registerSpawner(ObjectSpawner spawner) {
        gameObjects.registerSpawner(spawner);
    }

    private void createGameLoop() {
        gameObjects.init(inititialGameObjects);

        AnimationTimer animator = new AnimationTimer() {
            @Override
            public void handle(long arg0) {

                gameObjects.update();
            }
        };

        animator.start();
    }

    private void addKeyListeners(Scene scene) {
        scene.setOnKeyPressed(
                e -> {
                    var code = e.getCode();
                    input.add(code);
                    gameObjects.notifyGameObjectsOfPressedKeys(input);
                });

        scene.setOnKeyReleased(
                e -> {
                    var code = e.getCode();
                    input.remove(code);
                    gameObjects.notifyGameObjectsOfPressedKeys(input);
                });
    }

    private void createStage() {
        var root = new Group();
        gameObjects = new GameObjects(root);

        scene = new Scene(root, gameDimensions.getWidth(), gameDimensions.getHeight());
        primaryStage.setScene(scene);

        addKeyListeners(scene);
        sceneIsCreated = true;
    }

    private void showStage() {
        primaryStage.show();
    }
}
