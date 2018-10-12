package nl.han.ica.waterworld;

import nl.han.ica.waterworld.scenes.levels.LevelOne;
import nl.han.ica.waterworld.scenes.GameOver;
import nl.han.ica.waterworld.scenes.Intro;
import nl.han.ica.waterworld.scenes.levels.LevelTwo;
import nl.han.ica.yaeger.engine.YaegerEngine;
import nl.han.ica.yaeger.engine.entities.entity.sprites.Size;
import nl.han.ica.yaeger.engine.scenes.SceneType;

public class Waterworld extends YaegerEngine {

    public static final String FONT = "palatino";

    private static final String GAME_TITLE = "Waterworld 2";
    private static final int WATERWORLD_WIDTH = 1204;
    private static final int WATERWORLD_HEIGHT = 903;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initializeGame() {
        setSize(new Size(WATERWORLD_WIDTH, WATERWORLD_HEIGHT));
        setGameTitle(GAME_TITLE);
    }

    @Override
    public void setupScenes() {
        var introScene = new Intro(this);
        var levelOne = new LevelOne(this);
        var levelTwo = new LevelTwo(this);
        var gameOverScene = new GameOver(this);

        addScene(SceneType.INTRO, introScene);
        addScene(SceneType.LEVEL_ONE, levelOne);
        addScene(SceneType.LEVEL_TWO, levelTwo);
        addScene(SceneType.GAMEOVER, gameOverScene);

        setActiveScene(SceneType.INTRO);
    }

    public void nextScene(SceneType scene) {
        setActiveScene(scene);
    }
}
