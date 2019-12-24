package nl.meron.showcase;

import nl.meron.showcase.scenes.spriteentitiesscene.SpriteEntitiesScene;
import nl.meron.showcase.scenes.textentitiesscene.TextEntitiesScene;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.YaegerEngine;
import nl.meron.yaeger.engine.scenes.SceneType;

/**
 * ShowCase of all Yaeger Features.
 */
public class YaegerShowCase extends YaegerEngine {

    private static final String GAME_TITLE = "Yaeger Show Case";
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

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
        var textEntitiesScene = new TextEntitiesScene();
        addScene(SceneType.LEVEL_ONE, textEntitiesScene);
        var spriteEntitiesScene = new SpriteEntitiesScene();
        addScene(SceneType.LEVEL_TWO, spriteEntitiesScene);
    }
}
