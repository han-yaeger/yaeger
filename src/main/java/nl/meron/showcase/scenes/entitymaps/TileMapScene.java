package nl.meron.showcase.scenes.entitymaps;

import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.buttons.Back;
import nl.meron.showcase.scenes.entitymaps.tilemaps.BoundedTileMap;
import nl.meron.showcase.scenes.entitymaps.tilemaps.FullScreenTileMap;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.AnchorPoint;
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

        var topLeftMap = new BoundedTileMap(new Location(0, 0), new Size(100, 100));
        topLeftMap.setAnchorPoint(AnchorPoint.TOP_LEFT);
        addEntityMap(topLeftMap);

        var topCentertMap = new BoundedTileMap(new Location(getWidth() / 2, 0), new Size(100, 100));
        topCentertMap.setAnchorPoint(AnchorPoint.TOP_CENTER);
        addEntityMap(topCentertMap);

        var topRighttMap = new BoundedTileMap(new Location(getWidth(), 0), new Size(100, 100));
        topRighttMap.setAnchorPoint(AnchorPoint.TOP_RIGHT);
        addEntityMap(topRighttMap);

        var centerLeftMap = new BoundedTileMap(new Location(0, getHeight() / 2), new Size(100, 100));
        centerLeftMap.setAnchorPoint(AnchorPoint.CENTER_LEFT);
        addEntityMap(centerLeftMap);

        var centeredMap = new BoundedTileMap(new Location(getWidth() / 2, getHeight() / 2), new Size(100, 100));
        centeredMap.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntityMap(centeredMap);

        var centerRightMap = new BoundedTileMap(new Location(getWidth(), getHeight() / 2), new Size(100, 100));
        centerRightMap.setAnchorPoint(AnchorPoint.CENTER_RIGHT);
        addEntityMap(centerRightMap);

        var bottomLeftMap = new BoundedTileMap(new Location(0, getHeight()), new Size(100, 100));
        bottomLeftMap.setAnchorPoint(AnchorPoint.BOTTOM_LEFT);
        addEntityMap(bottomLeftMap);

        var bottomCenterMap = new BoundedTileMap(new Location(getWidth() / 2, getHeight()), new Size(100, 100));
        bottomCenterMap.setAnchorPoint(AnchorPoint.BOTTOM_CENTER);
        addEntityMap(bottomCenterMap);

        var bottomRightMap = new BoundedTileMap(new Location(getWidth(), getHeight()), new Size(100, 100));
        bottomRightMap.setAnchorPoint(AnchorPoint.BOTTOM_RIGHT);
        addEntityMap(bottomRightMap);
    }

    @Override
    public void setupEntities() {
        var backButton = new Back(showCase);
        addEntity(backButton);
    }
}
