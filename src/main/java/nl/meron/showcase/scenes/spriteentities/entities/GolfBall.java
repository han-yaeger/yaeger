package nl.meron.showcase.scenes.spriteentities.entities;

import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.ContinuousRotatable;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.SceneBorderTouchingWatcher;
import nl.meron.yaeger.engine.entities.entity.collisions.Collided;
import nl.meron.yaeger.engine.entities.entity.collisions.Collider;
import nl.meron.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;
import nl.meron.yaeger.engine.scenes.SceneBorder;

public class GolfBall extends DynamicSpriteEntity implements SceneBorderTouchingWatcher, ContinuousRotatable, Collider, Collided {


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
