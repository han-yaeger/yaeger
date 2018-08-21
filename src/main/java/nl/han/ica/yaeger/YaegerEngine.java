package nl.han.ica.yaeger;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.han.ica.yaeger.exceptions.YaegerSceneNotAvailableException;
import nl.han.ica.yaeger.metrics.GameDimensions;
import nl.han.ica.yaeger.scene.SceneType;
import nl.han.ica.yaeger.scene.StaticScene;
import nl.han.ica.yaeger.scene.YaegerScene;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code YaegerEngine} is de basis-superklasse die ge-extend moet worden. Na het extenden zal een aantal methodes
 * worden aangeboden die het mogelijk maken de inhoud van het spel te initialiseren.
 */
public abstract class YaegerEngine extends Application {

    private static final GameDimensions DEFAULT_GAME_DIMENSIONS = new GameDimensions(640, 480);

    private GameDimensions gameDimensions = DEFAULT_GAME_DIMENSIONS;

    private Stage yaegerStage;
    private Map<SceneType, StaticScene> scenes = new HashMap<>();
    private YaegerScene activeScene;

    /**
     * Zet de breedte en hoogte van het spel.
     *
     * @param dimensions Een {@link GameDimensions} object encapsuleert de breedte en hoogte van een spel.
     */
    protected void setGameDimensions(GameDimensions dimensions) {
        this.gameDimensions = dimensions;
    }

    /**
     * Zet de titel van het spel.
     *
     * @param title De titel van het spel.
     */
    protected void setGameTitle(String title) {
        yaegerStage.setTitle(title);
    }

    /**
     * Zet de huidige actieve {@link YaegerScene}. Dit is de {@code Scene} die getoond wordt op het scherm en waarvan,
     * indien beschikbaar, de {@code Gameloop} en {@code Eventlisteners} hun werk doen.
     *
     * @param type De enumeratie die de type van de {@code Scene} bevat.
     * @return De {@link YaegerScene}.
     */
    protected YaegerScene setActiveScene(SceneType type) {
        var requestedScene = scenes.get(type);

        if (requestedScene == null) {
            throw new YaegerSceneNotAvailableException(type);
        }

        requestedScene.setupScene();

        if (activeScene != null) {
            activeScene.tearDownScene();
        }

        activeScene = requestedScene;

        yaegerStage.setScene(activeScene.getScene());
        return activeScene;
    }

    /**
     * Voeg een {@link StaticScene} toe aan dit spel.
     *
     * @param type  De enumeratie die de type van de {@code Scene} bevat.
     * @param scene De {@link StaticScene} die moet worden toegevoegd.
     */
    public void addScene(SceneType type, StaticScene scene) {
        scenes.put(type, scene);
    }

    /**
     * Gebruik deze methode voor het initialiseren van het spel.
     *
     * <p>
     * Op dit moment is het mogelijk om de afmetingen van het scherm te zetten of een titel van het spel te zetten.
     * </p>
     */
    protected abstract void initializeGame();

    /**
     * Gebruik deze methode voor het aanmaken en toevoegen van alle {@link StaticScene}s. Gebruik dit moment ook
     * om de actieve {@link StaticScene} te zetten. Indien deze niet gezet is, zal een willekeurige als actieve
     * worden gebruikt.
     */
    protected abstract void setupScenes();

    @Override
    public void start(Stage primaryStage) {
        yaegerStage = primaryStage;

        initializeGame();

        yaegerStage.setWidth(gameDimensions.getWidth());
        yaegerStage.setHeight(gameDimensions.getHeight());

        setupScenes();

        showGame();
    }

    /**
     * Retourneer de hoogte van het spel.
     *
     * @return De hoogte van het spel.
     */
    public int getGameHeight() {
        return this.gameDimensions.getHeight();
    }

    /**
     * Retourneer de breedte van het spel.
     *
     * @return De breedte van het spel.
     */
    public int getGameWidth() {
        return this.gameDimensions.getWidth();
    }

    /**
     * Stop het spel en sluit het scherm af.
     */
    public void quitGame() {
        yaegerStage.close();
    }

    private void showGame() {
        yaegerStage.show();
    }
}
