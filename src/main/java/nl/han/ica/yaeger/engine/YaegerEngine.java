package nl.han.ica.yaeger.engine;

import javafx.application.Application;
import javafx.stage.Stage;
import nl.han.ica.yaeger.engine.entities.entity.sprites.Size;
import nl.han.ica.yaeger.engine.scenes.Scenes;
import nl.han.ica.yaeger.engine.scenes.SceneType;
import nl.han.ica.yaeger.engine.scenes.YaegerScene;

/**
 * {@code YaegerEngine} is de basis-superklasse die ge-extend moet worden. Na het extenden zal een aantal methodes
 * worden aangeboden die het mogelijk maken de inhoud van het game te initialiseren.
 */
public abstract class YaegerEngine extends Application {

    private static final Size DEFAULT_GAME_DIMENSIONS = new Size(640, 480);

    private Size size = DEFAULT_GAME_DIMENSIONS;

    private Stage yaegerStage;
    private Scenes scenes;

    /**
     * Set the {@link Size}, being the {@code width} and {@code height} of the game.
     *
     * @param size Een {@link Size} object encapsuleert de breedte en hoogte van een game.
     */
    protected void setSize(Size size) {
        this.size = size;
    }

    /**
     * Set the title of the Game.
     *
     * @param title A {@link String} containing the title of the Game.
     */
    protected void setGameTitle(String title) {
        yaegerStage.setTitle(title);
    }

    /**
     * Zet de huidige actieve {@link YaegerScene}. Dit is de {@code scenes} die getoond wordt op het scherm en waarvan,
     * indien beschikbaar, de {@code Gameloop} en {@code Eventlisteners} hun werk doen.
     *
     * @param type De enumeratie die de type van de {@link YaegerScene} bevat.
     */
    protected void setActiveScene(SceneType type) {
        scenes.setActive(type);
    }

    /**
     * Add a {@link YaegerScene} to the Game.
     *
     * @param type  A {@link SceneType} enumeration that represents the type of the {@link YaegerScene}.
     * @param scene De {@link YaegerScene} die moet worden toegevoegd.
     */
    protected void addScene(SceneType type, YaegerScene scene) {

        scenes.addScene(type, scene);
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

        yaegerStage.setWidth(size.getWidth());
        yaegerStage.setHeight(size.getHeight());

        setupScenes();

        showGame();
    }

    /**
     * Returns the height of the Game.
     *
     * @return The height of the Game.
     */
    public double getGameHeight() {
        return this.size.getHeight();
    }

    /**
     * Returns the width of the Game.
     *
     * @return The width of the Game.
     */
    public double getGameWidth() {
        return this.size.getWidth();
    }

    /**
     * Stop the Game and close the screen.
     */
    public void quitGame() {
        yaegerStage.close();
    }

    private void showGame() {
        yaegerStage.show();
    }
}
