package nl.meron.pong;

import nl.meron.yaeger.engine.YaegerEngine;
import nl.meron.yaeger.engine.entities.entity.sprites.Size;
import nl.meron.yaeger.engine.scenes.SceneType;

public class Pong extends YaegerEngine {

    private static final String GAME_TITLE = "Pong";
    private static final int WIDTH = 960;
    private static final int HEIGHT = 600;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initializeGame() {
        setGameTitle(GAME_TITLE);
        setSize(new Size(960, 600));
    }

    @Override
    protected void setupScenes() {
        var level1 = new LevelOne();
        addScene(SceneType.LEVEL_ONE, level1);
    }
}
