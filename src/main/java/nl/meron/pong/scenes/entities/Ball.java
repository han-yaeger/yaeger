package nl.meron.pong.scenes.entities;

import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.motion.MotionVector;
import nl.meron.yaeger.engine.entities.entity.sprite.Size;
import nl.meron.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;
import nl.meron.yaeger.engine.entities.events.scene.SceneBorderCrossingWatcher;
import nl.meron.yaeger.engine.scenes.SceneBorder;

public class Ball extends DynamicSpriteEntity implements SceneBorderCrossingWatcher {

    public Ball(Point point, Double direction) {
        super("pong/ball.png", point, new Size(20, 20), 0, new MotionVector(direction, 1d));
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT) || border.equals(SceneBorder.RIGHT)) {
            remove();
        }
    }
}
