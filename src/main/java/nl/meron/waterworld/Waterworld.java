package nl.meron.waterworld;

import nl.meron.waterworld.scenes.levels.LevelOne;
import nl.meron.waterworld.scenes.GameOver;
import nl.meron.waterworld.scenes.Intro;
import nl.meron.waterworld.scenes.levels.LevelTwo;
import nl.meron.yaeger.engine.YaegerEngine;
import nl.meron.yaeger.engine.entities.entity.sprite.Size;
import nl.meron.yaeger.engine.scenes.SceneType;

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
