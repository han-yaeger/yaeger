package nl.meron.showcase.scenes.spriteentities;

import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.buttons.Back;
import nl.meron.showcase.scenes.ShowCaseScene;
import nl.meron.showcase.scenes.spriteentities.entities.BasketBall;
import nl.meron.showcase.scenes.spriteentities.entities.TennisBall;
import nl.meron.yaeger.engine.entities.entity.Point;


public class SpriteEntitiesScene extends ShowCaseScene {

    private YaegerShowCase showCase;

    public SpriteEntitiesScene(YaegerShowCase showCase) {
        this.showCase = showCase;
    }

    @Override
    protected void setupSpawners() {

    }

    @Override
    public void setupScene() {
        setBackgroundImage("showcase/backgrounds/landscape.jpg");
    }

    @Override
    public void setupEntities() {

        var backButton = new Back(showCase);
        addEntity(backButton);

        var tennisBall = new TennisBall(new Point(200, 120));
        addEntity(tennisBall);

        var basketBall = new BasketBall(new Point(200, 220));
        addEntity(basketBall);
    }

    @Override
    public YaegerShowCase getShowCase() {
        return showCase;
    }
}
