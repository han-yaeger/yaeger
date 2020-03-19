package nl.meron.showcase.scenes.entitymaps;

import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.buttons.Back;
import nl.meron.showcase.scenes.entitymaps.tilemaps.CenteredTileMap;
import nl.meron.showcase.scenes.entitymaps.tilemaps.FullScreenTileMap;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.scenes.DynamicScene;
import nl.meron.yaeger.engine.entities.tilemap.WithTileMaps;

public class TileMapScene extends DynamicScene implements WithTileMaps {

    private YaegerShowCase showCase;

    public TileMapScene(YaegerShowCase showCase) {
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
    public void setupTileMaps() {
        var fullScreenMap = new FullScreenTileMap();
        addEntityMap(fullScreenMap);

        var centeredMap = new CenteredTileMap(new Location(getWidth() / 2, getHeight() / 2), new Size(300, 200));
        addEntityMap(centeredMap);
    }

    @Override
    public void setupEntities() {
        var backButton = new Back(showCase);
        addEntity(backButton);
    }
}
