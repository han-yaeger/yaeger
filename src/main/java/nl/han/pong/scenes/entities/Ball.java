package nl.han.pong.scenes.entities;

import nl.han.pong.scenes.ScoreKeeper;
import nl.han.yaeger.engine.entities.entity.collisions.Collider;
import nl.han.yaeger.engine.entities.entity.collisions.CollisionSide;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.Size;
import nl.han.yaeger.engine.entities.entity.SceneBorderTouchingWatcher;
import nl.han.yaeger.engine.entities.entity.collisions.AABBSideAwareCollided;
import nl.han.yaeger.engine.entities.entity.motion.Direction;
import nl.han.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;
import nl.han.yaeger.engine.entities.entity.SceneBorderCrossingWatcher;
import nl.han.yaeger.engine.scenes.SceneBorder;

public class Ball extends DynamicSpriteEntity implements SceneBorderTouchingWatcher, SceneBorderCrossingWatcher, AABBSideAwareCollided {

    private static final double INITIAL_SPEED = 4;
    private ScoreKeeper scoreKeeper;

    public Ball(final Location location, final ScoreKeeper scoreKeeper) {
        super("pong/ball.png", location, new Size(20, 20));
        this.scoreKeeper = scoreKeeper;
        setMotionTo(INITIAL_SPEED, 80);
    }

    private void start() {
        setOriginX(getSceneWidth() / 2);
        setOriginY(getSceneHeight() / 2);
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
