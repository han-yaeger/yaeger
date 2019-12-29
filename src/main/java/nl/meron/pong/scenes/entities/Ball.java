package nl.meron.pong.scenes.entities;

import nl.meron.yaeger.engine.entities.collisions.Collided;
import nl.meron.yaeger.engine.entities.collisions.Collider;
import nl.meron.yaeger.engine.entities.collisions.CollisionSide;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.SceneBorderTouchingWatcher;
import nl.meron.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;
import nl.meron.yaeger.engine.entities.entity.SceneBorderCrossingWatcher;
import nl.meron.yaeger.engine.scenes.SceneBorder;

import java.util.Set;

public class Ball extends DynamicSpriteEntity implements  SceneBorderCrossingWatcher, Collided {

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

    @Override
    public void onCollision(Collider collidingObject, CollisionSide collisionSide) {
        changeDirection(180);
    }
}
