package nl.meron.waterworld;

import javafx.stage.Stage;
import nl.meron.waterworld.scenes.levels.LevelOne;
import nl.meron.waterworld.scenes.GameOver;
import nl.meron.waterworld.scenes.Intro;
import nl.meron.waterworld.scenes.levels.LevelTwo;
import nl.meron.yaeger.engine.YaegerApplication;
import nl.meron.yaeger.engine.Size;

public class Waterworld extends YaegerApplication {

    public static final String FONT = "palatino";

    private static final String GAME_TITLE = "Waterworld 2";
    private static final int WATERWORLD_WIDTH = 1204;
    private static final int WATERWORLD_HEIGHT = 903;

    public static final int SCENE_INTRO = 0;
    public static final int SCENE_GAME_OVER = 3;
    public static final int SCENE_LEVEL_ONE = 1;
    public static final int SCENE_LEVEL_TWO = 2;

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
        addScene(SCENE_INTRO, new Intro(this));
        addScene(SCENE_LEVEL_ONE, new LevelOne(this));
        addScene(SCENE_LEVEL_TWO, new LevelTwo(this));
        addScene(SCENE_GAME_OVER, new GameOver(this));
    }

    public void nextScene(int scene) {
        setActiveScene(scene);
    }
}
