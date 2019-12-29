package nl.meron.pong.scenes;

import nl.meron.pong.scenes.entities.Ball;
import nl.meron.pong.scenes.entities.PlayerOneBat;
import nl.meron.pong.scenes.entities.PlayerTwoBat;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.scenes.impl.DynamicScene;

/**
 * Level one of the game
 */
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
        var ball1 = new Ball(new Point(200, 200), 75);
        var bat1 = new PlayerOneBat(new Point(20, 280), new Size(300, 75));
        var bat2 = new PlayerTwoBat(new Point(925, 280), new Size(300, 75));
        addEntity(ball1);
        addEntity(bat1);
        addEntity(bat2);
    }
}
