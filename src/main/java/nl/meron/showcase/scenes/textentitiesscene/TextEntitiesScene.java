package nl.meron.showcase.scenes.textentitiesscene;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.showcase.scenes.textentitiesscene.entities.SceneBorderCrossingDynamicTextEntity;
import nl.meron.showcase.scenes.textentitiesscene.entities.SceneBorderTouchingDynamicTextEntity;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.text.TextEntity;
import nl.meron.yaeger.engine.scenes.impl.DynamicScene;

public class TextEntitiesScene extends DynamicScene {

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
        var staticTextEntity = new TextEntity(new Point(400, 30), "I'm a TextEntity and remain static on the Scene");
        staticTextEntity.setFill(Color.ORANGE);
        staticTextEntity.setFont(Font.font(FONT, FontWeight.BOLD, 30));
        var borderTouchingDynamicTextEntity = new SceneBorderTouchingDynamicTextEntity(new Point(400, 60));
        var borderCrossingDynamicTextEntity = new SceneBorderCrossingDynamicTextEntity(new Point(600, 90));

        addEntity(borderTouchingDynamicTextEntity);
        addEntity(borderCrossingDynamicTextEntity);


    }
}
