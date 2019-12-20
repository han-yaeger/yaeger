package nl.meron.showcase.scenes;

import nl.meron.showcase.scenes.entities.SceneBorderCrossingDynamicTextEntity;
import nl.meron.showcase.scenes.entities.SceneBorderTouchingDynamicTextEntity;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.scenes.impl.DynamicScene;

/**
 * Level one of the game
 */
public class LevelOne extends DynamicScene {

    @Override
    protected void setupSpawners() {

    }

    @Override
    public void setupScene() {
        setBackgroundImage("showcase/images/matrix.jpg");
    }

    @Override
    public void setupEntities() {
        var borderTouchingDynamicTextEntity = new SceneBorderTouchingDynamicTextEntity(new Point(400, 30));
        var borderCrossingDynamicTextEntity = new SceneBorderCrossingDynamicTextEntity(new Point(600, 60));
        addEntity(borderTouchingDynamicTextEntity);
        addEntity(borderCrossingDynamicTextEntity);
    }
}
