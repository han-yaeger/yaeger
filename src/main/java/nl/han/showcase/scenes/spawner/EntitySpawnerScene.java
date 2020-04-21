package nl.han.showcase.scenes.spawner;

import nl.han.showcase.YaegerShowCase;
import nl.han.showcase.buttons.Back;
import nl.han.showcase.scenes.ShowCaseScene;
import nl.han.showcase.scenes.spawner.entities.spawners.RainSpawner;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.scenes.WithSpawners;

public class EntitySpawnerScene extends ShowCaseScene implements WithSpawners {

    private YaegerShowCase showCase;

    public EntitySpawnerScene(final YaegerShowCase showCase) {
        this.showCase = showCase;
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
        addEntitySpawner(new RainSpawner(getWidth(), 2, 4, 1, 2));
        addEntitySpawner(new RainSpawner(getWidth(), 2, 10, 2, 4));
    }
}
