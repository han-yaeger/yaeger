package nl.meron.showcase.scenes.entitymaps;

import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.buttons.Back;
import nl.meron.showcase.scenes.entitymaps.EntityMap.TileMap;
import nl.meron.yaeger.engine.scenes.DynamicScene;
import nl.meron.yaeger.engine.entities.entitymap.WithEntityMaps;

public class EntityMapScene extends DynamicScene implements WithEntityMaps {

    private YaegerShowCase showCase;

    public EntityMapScene(YaegerShowCase showCase) {
        this.showCase = showCase;
    }

    @Override
    public void setupScene() {
        setBackgroundImage("showcase/backgrounds/milky-way.jpg");
    }

    @Override
    protected void setupSpawners() {
    }

    @Override
    public void registerEntityMaps() {
        var map = new TileMap();
        registerEntityMap(map);
    }

    @Override
    public void setupEntities() {
        var backButton = new Back(showCase);
        addEntity(backButton);
    }
}
