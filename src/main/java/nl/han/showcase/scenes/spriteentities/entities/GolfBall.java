package nl.han.showcase.scenes.spriteentities.entities;

import nl.han.yaeger.engine.Size;
import nl.han.yaeger.engine.entities.entity.ContinuousRotatable;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.entity.SceneBorderTouchingWatcher;
import nl.han.yaeger.engine.entities.entity.collisions.AABBCollided;
import nl.han.yaeger.engine.entities.entity.collisions.Collider;
import nl.han.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;
import nl.han.yaeger.engine.scenes.SceneBorder;

public class GolfBall extends DynamicSpriteEntity implements SceneBorderTouchingWatcher, ContinuousRotatable, Collider, AABBCollided {


    public GolfBall(Location location, double speed, double direction) {
        super("showcase/entities/golfball.png", location, new Size(20, 20), 0);
        setMotionTo(speed, direction);
        setRotationSpeed(2);
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder border) {
        changeDirectionBy(180);
    }

    @Override
    public void onCollision(Collider collidingObject) {
        changeDirectionBy(180);
    }
}
