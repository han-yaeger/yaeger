package nl.meron.pong.scenes;

import nl.meron.pong.scenes.entities.Ball;
import nl.meron.pong.scenes.entities.DynamicTextEntityTester;
import nl.meron.pong.scenes.entities.PlayerOneBat;
import nl.meron.pong.scenes.entities.PlayerTwoBat;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.motion.MotionVector;
import nl.meron.yaeger.engine.entities.entity.sprites.Size;
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
        var ball1 = new Ball(new Point(200, 200), MotionVector.Direction.RIGHT);
        var ball2 = new Ball(new Point(200, 400), MotionVector.Direction.LEFT);
        var bat1 = new PlayerOneBat(new Point(20, 280), new Size(300, 75));
        var bat2 = new PlayerTwoBat(new Point(925, 280), new Size(300, 75));
        addEntity(ball1);
        addEntity(ball2);
        addEntity(bat1);
        addEntity(bat2);

        var textTester = new DynamicTextEntityTester();
        addEntity(textTester);
    }
}
