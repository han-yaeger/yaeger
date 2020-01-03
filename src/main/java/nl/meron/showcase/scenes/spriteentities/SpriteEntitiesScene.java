package nl.meron.showcase.scenes.spriteentities;

import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.buttons.Back;
import nl.meron.showcase.scenes.spriteentities.entities.Ball;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.scenes.impl.DynamicScene;

public class SpriteEntitiesScene extends DynamicScene {

    private YaegerShowCase showCase;

    public  SpriteEntitiesScene(YaegerShowCase showCase){
        this.showCase = showCase;
    }

    @Override
    protected void setupSpawners() {

    }

    @Override
    public void setupScene() {
        setBackgroundImage("showcase/images/landscape.jpg");
    }

    @Override
    public void setupEntities() {
        var backButton = new Back(showCase);
        addEntity(backButton);

        var dynamicSpriteEntity = new Ball(new Point(300, 120));

        addEntity(dynamicSpriteEntity);
    }
}
