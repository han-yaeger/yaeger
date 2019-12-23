package nl.meron.showcase.scenes;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.showcase.scenes.entities.Ball;
import nl.meron.showcase.scenes.entities.SceneBorderCrossingDynamicTextEntity;
import nl.meron.showcase.scenes.entities.SceneBorderTouchingDynamicTextEntity;
import nl.meron.waterworld.Waterworld;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.sprite.SpriteEntity;
import nl.meron.yaeger.engine.entities.entity.text.TextEntity;
import nl.meron.yaeger.engine.scenes.impl.DynamicScene;

/**
 * Level one of the game
 */
public class LevelOne extends DynamicScene {

    public static final String FONT = "palatino";

    @Override
    protected void setupSpawners() {

    }

    @Override
    public void setupScene() {
        setBackgroundImage("showcase/images/2755488.jpg");
    }

    @Override
    public void setupEntities() {
        // TextEntities
        var staticSpriteEntity = new TextEntity(new Point(400, 30), "Press F1 to view Debugging info");
        staticSpriteEntity.setFill(Color.ORANGE);
        staticSpriteEntity.setFont(Font.font(FONT, FontWeight.BOLD, 30));
        var borderTouchingDynamicTextEntity = new SceneBorderTouchingDynamicTextEntity(new Point(400, 60));
        var borderCrossingDynamicTextEntity = new SceneBorderCrossingDynamicTextEntity(new Point(600, 90));

        addEntity(staticSpriteEntity);
        addEntity(borderTouchingDynamicTextEntity);
        addEntity(borderCrossingDynamicTextEntity);

        // SpriteEntities
        var dynamicSpriteEntity = new Ball(new Point(300, 120));

        addEntity(dynamicSpriteEntity);
    }
}
