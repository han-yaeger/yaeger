package nl.meron.showcase.scenes.spriteentitiesscene;

import nl.meron.showcase.scenes.spriteentitiesscene.entities.Ball;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.scenes.impl.DynamicScene;

public class SpriteEntitiesScene extends DynamicScene {

    @Override
    protected void setupSpawners() {

    }

    @Override
    public void setupScene() {
        setBackgroundImage("showcase/images/2755488.jpg");
    }

    @Override
    public void setupEntities() {
        // SpriteEntities
        var dynamicSpriteEntity = new Ball(new Point(300, 120));

        addEntity(dynamicSpriteEntity);
    }
}
