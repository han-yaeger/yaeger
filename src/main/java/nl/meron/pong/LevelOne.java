package nl.meron.pong;

import nl.meron.yaeger.engine.entities.entity.Position;
import nl.meron.yaeger.engine.entities.entity.sprites.Size;
import nl.meron.yaeger.engine.scenes.impl.DynamicScene;

public class LevelOne extends DynamicScene {

    @Override
    protected void setupSpawners() {

    }

    @Override
    public void setupScene() {
        setBackgroundImage("pong/pong-bg.jpg");
    }

    @Override
    public void setupEntities() {
        var bat1 = new PlayerOneBat(new Position(20, 280), new Size(300, 75));
        var bat2 = new PlayerTwoBat(new Position(925, 280), new Size(300, 75));
        addEntity(bat1);
        addEntity(bat2);
    }
}
