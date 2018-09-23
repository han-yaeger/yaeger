package nl.han.ica.yaeger;

import javafx.application.Application;
import javafx.stage.Stage;
import nl.han.ica.yaeger.metrics.GameDimensions;
import nl.han.ica.yaeger.scene.Scenes;
import nl.han.ica.yaeger.scene.SceneType;
import nl.han.ica.yaeger.scene.YaegerScene;

/**
 * {@code YaegerEngine} is de basis-superklasse die ge-extend moet worden. Na het extenden zal een aantal methodes
 * worden aangeboden die het mogelijk maken de inhoud van het game te initialiseren.
 */
public abstract class YaegerEngine extends Application {

    private static final GameDimensions DEFAULT_GAME_DIMENSIONS = new GameDimensions(640, 480);

    private GameDimensions gameDimensions = DEFAULT_GAME_DIMENSIONS;

    private Stage yaegerStage;
    private Scenes scenes;

    /**
     * Zet de breedte en hoogte van het game.
     *
     * @param dimensions Een {@link GameDimensions} object encapsuleert de breedte en hoogte van een game.
     */
    protected void setGameDimensions(GameDimensions dimensions) {
        this.gameDimensions = dimensions;
    }

    /**
     * Zet de titel van het game.
     *
     * @param title De titel van het game.
     */
    protected void setGameTitle(String title) {
        yaegerStage.setTitle(title);
    }

    /**
     * Zet de huidige actieve {@link YaegerScene}. Dit is de {@code scene} die getoond wordt op het scherm en waarvan,
     * indien beschikbaar, de {@code Gameloop} en {@code Eventlisteners} hun werk doen.
     *
     * @param type De enumeratie die de type van de {@link YaegerScene} bevat.
     */
    protected void setActiveScene(SceneType type) {
        scenes.setActive(type);
    }

    /**
     * Voeg een {@link YaegerScene} toe aan dit game.
     *
     * @param type  De enumeratie die de type van de {@code scene} bevat.
     * @param scene De {@link YaegerScene} die moet worden toegevoegd.
     */
    protected void addScene(SceneType type, YaegerScene scene) {
        scenes.put(type, scene);
    }

    /**
     * Gebruik deze methode voor het initialiseren van het game.
     *
     * <p>
     * Op dit moment is het mogelijk om de afmetingen van het scherm te zetten of een titel van het game te zetten.
     * </p>
     */
    protected abstract void initializeGame();

    /**
     * Gebruik deze methode voor het aanmaken en toevoegen van alle {@link YaegerScene}s. Gebruik dit moment ook
     * om de actieve {@link YaegerScene} te zetten. Indien deze niet gezet is, zal een willekeurige als actieve
     * worden gebruikt.
     */
    protected abstract void setupScenes();

    @Override
    public void start(Stage primaryStage) {
        yaegerStage = primaryStage;

        scenes = new Scenes(primaryStage);

        initializeGame();

        yaegerStage.setWidth(gameDimensions.getWidth());
        yaegerStage.setHeight(gameDimensions.getHeight());

        setupScenes();

        showGame();
    }

    /**
     * Retourneer de hoogte van het game.
     *
     * @return De hoogte van het game.
     */
    public int getGameHeight() {
        return this.gameDimensions.getHeight();
    }

    /**
     * Retourneer de breedte van het game.
     *
     * @return De breedte van het game.
     */
    public int getGameWidth() {
        return this.gameDimensions.getWidth();
    }

    /**
     * Stop het game en sluit het scherm af.
     */
    public void quitGame() {
        yaegerStage.close();
    }

    private void showGame() {
        yaegerStage.show();
    }
}
