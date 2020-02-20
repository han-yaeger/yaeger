package nl.meron.pong.scenes.entities;

import nl.meron.pong.scenes.ScoreKeeper;
import nl.meron.yaeger.engine.entities.entity.collisions.Collided;
import nl.meron.yaeger.engine.entities.entity.collisions.Collider;
import nl.meron.yaeger.engine.entities.entity.collisions.CollisionSide;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.SceneBorderTouchingWatcher;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;
import nl.meron.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;
import nl.meron.yaeger.engine.entities.entity.SceneBorderCrossingWatcher;
import nl.meron.yaeger.engine.scenes.SceneBorder;

public class Ball extends DynamicSpriteEntity implements SceneBorderTouchingWatcher, SceneBorderCrossingWatcher, Collided {

    private static final double INITIAL_SPEED = 4;
    private ScoreKeeper scoreKeeper;

    public Ball(ScoreKeeper scoreKeeper) {
        super("pong/ball.png", new Location(200, 200), new Size(20, 20));
        this.scoreKeeper = scoreKeeper;
        setMotionTo(INITIAL_SPEED, 80);
    }

    private void start() {
        setX(getSceneWidth() / 2);
        setY(getSceneHeight() / 2);
    }

    @Override
    public void onCollision(Collider collidingObject, CollisionSide collisionSide) {
        applyNewDirection();
        applyNewRotation(collidingObject);
    }

    private void applyNewDirection() {
        var direction = getDirection();

        if (direction > 0 && direction < 180) {
            setDirectionTo(360 - direction);
        } else if (direction > 180 && direction < 360) {
            setDirectionTo(180 - (direction - 180));
        } else {
            setDirectionTo(direction + 180);
        }
    }

    private void applyNewRotation(Collider collidingObject) {
        var rotation = collidingObject.getSpeed() * 2;
        if (collidingObject.getDirection() == Direction.UP.getValue()) {
            rotation *= -1;
        }
        setRotationSpeed(rotation);
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT) || border.equals(SceneBorder.RIGHT)) {
            scoreKeeper.playerScores(border);
            start();
        }
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder border) {
        if (border.equals(SceneBorder.TOP)) {
            var direction = getDirection();

            if (direction > 90 && direction < 180) {
                setDirectionTo(180 - direction);
            } else {
                setDirectionTo(180 - (direction - 180));
            }
        } else if (border.equals(SceneBorder.BOTTOM)) {
            var direction = getDirection();

            if (direction > 0 && direction < 90) {
                setDirectionTo(180 - direction);
            } else {
                setDirectionTo(270 - (direction - 270));
            }
        }
    }
}
