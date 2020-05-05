package nl.meron.pong;

import javafx.stage.Stage;
import nl.meron.pong.scenes.LevelOne;
import nl.meron.yaeger.engine.YaegerApplication;
import nl.meron.yaeger.engine.Size;

/**
 * Elementary implementation of the PONG game.
 */
public class Pong extends YaegerApplication {

    private static final String GAME_TITLE = "Pong";
    public static final String FONT = "arial";
    private static final int WIDTH = 960;
    private static final int HEIGHT = 600;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initializeGame() {
        setGameTitle(GAME_TITLE);
        setSize(new Size(WIDTH, HEIGHT));
    }

    @Override
    protected void setupScenes() {
        var level1 = new LevelOne();
        addScene(1, level1);
    }
}
