package nl.meron.pong.scenes.entities;

import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.sprite.Size;
import nl.meron.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;
import nl.meron.yaeger.engine.entities.events.scene.SceneBorderCrossingWatcher;
import nl.meron.yaeger.engine.scenes.SceneBorder;

public class Ball extends DynamicSpriteEntity implements SceneBorderCrossingWatcher {

    private double direction;

    public Ball(Point point, double direction) {
        super("pong/ball.png", point, new Size(20, 20), 0);
        this.direction = direction;
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT) || border.equals(SceneBorder.RIGHT)) {
            remove();
        }
    }

    @Override
    public void configure() {
        setMotion(1, direction);
    }
}