package nl.meron.pong;

import nl.meron.yaeger.engine.YaegerEngine;
import nl.meron.yaeger.engine.scenes.SceneType;

public class Pong extends YaegerEngine {

    private static final String GAME_TITLE = "Pong";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initializeGame() {
        setGameTitle(GAME_TITLE);
    }

    @Override
    protected void setupScenes() {
        var level1 = new LevelOne();
        addScene(SceneType.LEVEL_ONE, level1);
    }
}
