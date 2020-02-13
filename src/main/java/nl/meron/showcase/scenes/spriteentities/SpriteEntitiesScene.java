package nl.meron.showcase.scenes.spriteentities;

import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.buttons.Back;
import nl.meron.showcase.scenes.ShowCaseScene;
import nl.meron.showcase.scenes.spriteentities.entities.BasketBall;
import nl.meron.showcase.scenes.spriteentities.entities.RugbyBall;
import nl.meron.showcase.scenes.spriteentities.entities.TennisBall;
import nl.meron.yaeger.engine.entities.entity.Location;


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

        var tennisBall = new TennisBall(new Location(100, 120));
        addEntity(tennisBall);

        var basketBall = new BasketBall(new Location(100, 220));
        addEntity(basketBall);

        var rugbyBall = new RugbyBall(new Location(100, 320));
        addEntity(rugbyBall);
    }

    @Override
    public YaegerShowCase getShowCase() {
        return showCase;
    }
}
