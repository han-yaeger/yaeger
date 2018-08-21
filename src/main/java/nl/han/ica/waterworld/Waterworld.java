package nl.han.ica.waterworld;

import nl.han.ica.waterworld.scenes.GameScene;
import nl.han.ica.waterworld.scenes.GameOverScene;
import nl.han.ica.waterworld.scenes.IntroScene;
import nl.han.ica.yaeger.YaegerEngine;
import nl.han.ica.yaeger.metrics.GameDimensions;
import nl.han.ica.yaeger.scene.SceneType;

public class Waterworld extends YaegerEngine {

    private static final String GAME_TITLE = "Waterworld 2";
    private static final int WATERWORLD_WIDTH = 1204;
    private static final int WATERWORLD_HEIGHT = 903;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initializeGame() {
        setGameDimensions(new GameDimensions(WATERWORLD_WIDTH, WATERWORLD_HEIGHT));
        setGameTitle(GAME_TITLE);
    }

    @Override
    public void setupScenes() {
        var introScene = new IntroScene(this);
        var gameScene = new GameScene(this);
        var gameOverScene = new GameOverScene(this);

        addScene(SceneType.OPENING, introScene);
        addScene(SceneType.GAME, gameScene);
        addScene(SceneType.GAMEOVER, gameOverScene);


        setActiveScene(SceneType.OPENING);
    }

    public void nextScene(SceneType scene) {
        setActiveScene(scene);
    }
}
