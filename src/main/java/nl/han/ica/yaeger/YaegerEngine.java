package nl.han.ica.yaeger;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import nl.han.ica.yaeger.exceptions.YaegerLifecycleException;
import nl.han.ica.yaeger.gameobjects.GameObject;
import nl.han.ica.yaeger.gameobjects.KeyListener;
import nl.han.ica.yaeger.metrics.GameDimensions;

import java.util.HashSet;
import java.util.Set;

public abstract class YaegerEngine extends Application {

    private static final GameDimensions DEFAULT_GAME_DIMENSIONS = new GameDimensions(640, 480);

    private GameDimensions gameDimensions = DEFAULT_GAME_DIMENSIONS;

    private Set<String> input = new HashSet<>();

    private Set<GameObject> gameObjects;
    private Stage primaryStage;
    private Scene primaryScene;

    private boolean sceneIsCreated = false;

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
     * At this stage it is possible to set the dimensions of the scene, to add a title to the application or
     * perform other initializations.
     * </p>
     */
    protected void beforeStageIsCreated() {
    }

    /**
     * Override this method to setup the stage before it is shown.
     */
    protected void beforeStageIsShown() {
    }

    /**
     * This method is called directly after the stage is shown.
     * <p>
     * Override this method to add behaviour that should be added after the stage is shown.
     * </p>
     */
    private void afterStageIsShown() {
    }

    /**
     * Return all GameObjects currently registered on this engine.
     *
     * @return A Set of GameObjects
     */
    public Set<GameObject> getGameObjects() {
        return gameObjects;
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

        primaryScene = createPrimaryScene(primaryStage);

        beforeStageIsShown();
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
        if (gameObjects == null) {
            gameObjects = new HashSet<>();
        }

        gameObjects.add(gameObject);
    }

    private void createGameLoop() {
        AnimationTimer animator = new AnimationTimer() {
            @Override
            public void handle(long arg0) {
                updateGameObjects();
            }
        };

        animator.start();

    }

    private void updateGameObjects() {
        gameObjects.stream().forEach(gameObject -> gameObject.update());
    }


    private void addKeyListeners(Scene scene) {
        scene.setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();
                    input.add(code);
                    notifyGameObjectsOfPressedKeys();
                });

        scene.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    input.remove(code);
                    notifyGameObjectsOfPressedKeys();
                });
    }


    private Scene createPrimaryScene(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, gameDimensions.getWidth(), gameDimensions.getHeight());
        primaryStage.setScene(scene);

        addKeyListeners(scene);
        setBackgroundImage(scene);
        sceneIsCreated = true;

        return scene;
    }

    private void showStage() {
        primaryStage.show();
    }

    private void notifyGameObjectsOfPressedKeys() {
        gameObjects.stream().filter(gameObject -> gameObject instanceof KeyListener)
                .forEach(gameObject -> ((KeyListener) gameObject).onPressedKeysChange(input));
    }
}
