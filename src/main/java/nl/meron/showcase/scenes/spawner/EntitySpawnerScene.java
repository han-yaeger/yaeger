package nl.meron.showcase.scenes.spawner;

import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.buttons.Back;
import nl.meron.showcase.scenes.ShowCaseScene;
import nl.meron.showcase.scenes.spawner.entities.spawners.RainSpawner;
import nl.meron.showcase.scenes.spriteentities.entities.BasketBall;
import nl.meron.showcase.scenes.spriteentities.entities.GolfBall;
import nl.meron.showcase.scenes.spriteentities.entities.RugbyBall;
import nl.meron.showcase.scenes.spriteentities.entities.TennisBall;
import nl.meron.yaeger.engine.entities.EntitySpawner;
import nl.meron.yaeger.engine.entities.entity.AnchorPoint;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;
import nl.meron.yaeger.engine.scenes.WithSpawners;

import java.util.List;

public class EntitySpawnerScene extends ShowCaseScene implements WithSpawners {

    private YaegerShowCase showCase;

    public EntitySpawnerScene(final YaegerShowCase showCase) {
        this.showCase = showCase;
    }

    @Override
    protected void setupDeprecatedSpawners() {

    }

    @Override
    public void setupScene() {
        setBackgroundImage("showcase/backgrounds/night-city.jpg");
    }

    @Override
    public void setupEntities() {
        var backButton = new Back(showCase, new Location(20, getHeight() - 30));
        addEntity(backButton);
    }

    @Override
    public YaegerShowCase getShowCase() {
        return showCase;
    }

    @Override
    public void setupEntitySpawners() {
        addEntitySpawner(new RainSpawner(getWidth(), 10));
    }
}
