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
import nl.han.ica.yaeger.gameobjects.spawners.ObjectSpawer;
import nl.han.ica.yaeger.metrics.GameDimensions;

import java.util.HashSet;
import java.util.Set;

public abstract class YaegerEngine extends Application {

    private static final GameDimensions DEFAULT_GAME_DIMENSIONS = new GameDimensions(640, 480);

    private GameDimensions gameDimensions = DEFAULT_GAME_DIMENSIONS;

    private Set<KeyCode> input = new HashSet<>();

    private Set<GameObject> inititialGameObjects = new HashSet<>();
    private GameObjects gameObjects;
    private Stage primaryStage;

    private boolean sceneIsCreated = false;
    private boolean stageIsShown = false;


    /**
     * Call this method the set the width and height of the game.
     *
     * @param dimensions A GameDimensions that contains the width and height of the game
     */
    public void setGameDimensions(GameDimensions dimensions) {
        if (sceneIsCreated) {
            throw new YaegerLifecycleException("Setting game dimensions is not allowed. The stage is already created.");
        }
        this.gameDimensions = dimensions;
    }

    /**
     * Set the title of the game.
     *
     * @param title The title of the game
     */
    public void setGameTitle(String title) {
        if (sceneIsCreated) {
            throw new YaegerLifecycleException("Setting game title is not allowed. The scene is already created.");
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
     * Set the background image of the Scene.
     *
     * @param scene
     */
    @Deprecated
    public void setBackgroundImage(Scene scene) {
        var url = getClass().getClassLoader().getResource("background.jpg");
        var stringUrl = url.toString();

        var image = new Image(stringUrl);
        var pattern = new ImagePattern(image);
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
     * Register an ObjectSpawner.
     *
     * @param spawner The ObjectSpawner to be registered.
     */
    public void registerSpawner(ObjectSpawer spawner) {
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

    private Scene createStage() {
        var root = new Group();
        gameObjects = new GameObjects(root);

        var scene = new Scene(root, gameDimensions.getWidth(), gameDimensions.getHeight());
        primaryStage.setScene(scene);

        addKeyListeners(scene);
        sceneIsCreated = true;

        return scene;
    }

    private void showStage() {
        primaryStage.show();
    }
}
