package nl.han.ica.testworld;

import nl.han.ica.yaeger.YaegerEngine;
import nl.han.ica.yaeger.metrics.GameDimensions;

public class Testworld extends YaegerEngine {

    private static final String GAME_TITLE = "Test";
    private static final int TESTWORLD_WIDTH = 400;
    private static final int TESTWORLD_HEIGHT = 200;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void beforeStageIsCreated() {
        setGameTitle(GAME_TITLE);
        setGameDimensions(new GameDimensions(TESTWORLD_WIDTH, TESTWORLD_HEIGHT));
    }
}
